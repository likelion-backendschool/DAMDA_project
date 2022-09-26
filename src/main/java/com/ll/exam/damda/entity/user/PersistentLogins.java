package com.ll.exam.damda.entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity(name = "persistent_logins")
public class PersistentLogins {

    @Id
    private String series;
    private String username;
    private String token;
    private Date lastUsed;
}