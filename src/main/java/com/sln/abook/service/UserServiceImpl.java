package com.sln.abook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialAuthenticationException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import com.sln.abook.dao.UserDao;
import com.sln.abook.model.User;

/**
 * UserDetailsService interface has only one method: loadUserByUsername(String username)
 * I'm implementing it in order to be able to use my UserServiceImpl as a <authentication-provider> in security.xml
 * 
 * SocialUserDetailsService  interface has only one method: loadUserByUserId(String userId)
 * I'm implementing it in order to be able to use my UserServiceImpl for SocialAuthenticationProvider bean in social.xml
 *  
 * This is needed so that Spring would store my own User object for authenticated user 
 * See examples:
 * http://stackoverflow.com/questions/25383286/spring-security-custom-userdetailsservice-and-custom-user-class
 * http://stackoverflow.com/questions/29327159/spring-security-get-user-id-from-db
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService, SocialUserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User findById(long id) {
		return userDao.findById(id);
	}
	
	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public void saveOrUpdate(User user) {
		if (findByEmail(user.getEmail())==null) {
			userDao.save(user);
		} else {
			userDao.update(user);
		}
	}

	@Override
	public void delete(long id) {
		userDao.delete(id);
	}
	
	@Override
	public boolean registerNewUser(String email, String realName, String password) {
		User user = new User();
		user.setEmail(email);
		user.setRealName(realName);
		user.setPassword(password);
		user.setConfirmPassword(password);
		user.setEnabled(true);
		
		if (findByEmail(email)==null) {
			userDao.save(user);
			return true;
		} else {
			return false;
		}
	}
	
	/** implementation of UserDetailsService interface */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	logger.debug("--------- UserServiceImpl.loadUserByUsername(): {}", username);

    	UserDetails userDetails = null;
	    User user = findByEmail(username);
		if(user == null) {
			return null;
			//throw new UsernameNotFoundException("Could not find user " + username);
		}
	    userDetails = new MyLocalUser(user, true, true, true, AuthorityUtils.createAuthorityList("ROLE_LOCALUSER") );
	    return userDetails;		
	}
	
	/** implementation of SocialUserDetailsService interface */
    @Override
    public SocialUserDetails loadUserByUserId(final String userId) throws UsernameNotFoundException, DataAccessException {
    	logger.debug("--------- UserServiceImpl.loadUserByUserId(): {}", userId);
    	
    	MyUser myUser = (MyUser) loadUserByUsername(userId);
        if (myUser == null) {
            throw new SocialAuthenticationException("No local user mapped with social user " + userId);
        }
        User user = myUser.getUser();
        SocialUserDetails socialUserDetails = new MySocialUser(user, true, true, true, AuthorityUtils.createAuthorityList("ROLE_SOCIALUSER") );
        return socialUserDetails;
    }
	
}