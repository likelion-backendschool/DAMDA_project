package com.ll.exam.damda.dto.user;

import com.ll.exam.damda.entity.user.SiteUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class SiteUserContext extends User implements OAuth2User {
    private final Long id;
    private final String nickname;
    private final String email;

    private final String method;

    private Map<String, Object> attributes;
    private String userNameAttributeName;

    public SiteUserContext(SiteUser user, List<GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.method = user.getMethod();
    }

    public SiteUserContext(SiteUser siteUser, List<GrantedAuthority> authorities, Map<String, Object> attributes, String userNameAttributeName) {
        this(siteUser, authorities);
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return super.getAuthorities().stream().collect(Collectors.toSet());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.getAttribute(this.userNameAttributeName).toString();
    }
}