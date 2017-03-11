package com.sln.abook.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.sln.abook.model.User;

/** 
 * MyLocalUser is basically  org.springframework.security.core.userdetails.User with my User embedded into it
 * MySocialUser is basically org.springframework.social.security.SocialUser     with my User embedded into it
 * Both classes implement MyUser to provide access to my User
 * This class is used by UserServiceImpl
 */
public class MyLocalUser extends org.springframework.security.core.userdetails.User implements MyUser {
	private static final long serialVersionUID = 5639683223516504866L;
    private User user;

    public MyLocalUser(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.user = user;
    }

    public MyLocalUser(User user, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }
}