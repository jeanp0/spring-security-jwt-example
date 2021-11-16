package com.jm.demo.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetailsImpl extends User {

    public UserDetailsImpl(String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
    }

    public UserDetailsImpl(com.jm.demo.data.model.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
    }
}
