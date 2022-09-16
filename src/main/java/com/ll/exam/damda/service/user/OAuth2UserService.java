package com.ll.exam.damda.service.user;


import com.ll.exam.damda.config.user.OAuthTypeMatchNotFoundException;
import com.ll.exam.damda.config.user.UserNotFoundException;
import com.ll.exam.damda.dto.user.SiteUserContext;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String oauthId = oAuth2User.getName();
        SiteUser siteUser = null;
        String oauthType = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        if (!"KAKAO".equals(oauthType)) {
            throw new OAuthTypeMatchNotFoundException();
        }
        if (isNew(oauthType, oauthId)) {
            switch (oauthType) {
                case "KAKAO" -> {
                    Map attributesProperties = (Map) attributes.get("properties");
                    Map attributesKakaoAcount = (Map) attributes.get("kakao_account");
                    String email = "%s@kakao.com".formatted(oauthId);
                    String username = "KAKAO_%s".formatted(oauthId);
                    if ((boolean) attributesKakaoAcount.get("has_email")) {
                        email = (String) attributesKakaoAcount.get("email");
                    }
                    siteUser = SiteUser.builder()
                            .email(email)
                            .username(username)
                            .password("")
                            .method("k")
                            .nickname(username)
                            .createDate(LocalDateTime.now())
                            .build();

                    userRepository.save(siteUser);
                }
            }
        } else {
            siteUser = (SiteUser) userRepository.findByUsername("%s_%s".formatted(oauthType, oauthId))
                    .orElseThrow(UserNotFoundException::new);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("siteUser"));
        return new SiteUserContext(siteUser, authorities, attributes, userNameAttributeName);
    }

    private boolean isNew(String oAuthType, String oAuthId) {
        return userRepository.findByUsername("%s_%s".formatted(oAuthType, oAuthId)).isEmpty();
    }
}