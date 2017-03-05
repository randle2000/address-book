package com.sln.abook.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.sln.abook.model.User;

/** 
 * This class is used by UserServiceImpl
 */
public class MyUserDetails extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 5639683223516504866L;
    private User user;

    public MyUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.user = user;
    }

    public MyUserDetails(User user, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}