package com.ll.exam.damda.dto.user;

import com.ll.exam.damda.entity.user.SiteUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class SiteUserContext extends User {
    private final Long id;
    private final String nickname;

    public SiteUserContext(SiteUser user, List<GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.id = user.getId();
        this.nickname = user.getNickname();
    }
}