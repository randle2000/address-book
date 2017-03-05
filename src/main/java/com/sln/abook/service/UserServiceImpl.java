package com.sln.abook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sln.abook.dao.UserDao;
import com.sln.abook.model.User;

/**
 * UserDetailsService interface has only one method: loadUserByUsername(String username)
 * I'm implementing it in order to be able to use my UserServiceImpl as a <authentication-provider> in spring-security.xml
 * This is needed so that Spring would store my own User object for authenticated user 
 * See examples:
 * http://stackoverflow.com/questions/25383286/spring-security-custom-userdetailsservice-and-custom-user-class
 * http://stackoverflow.com/questions/29327159/spring-security-get-user-id-from-db
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

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
	
	/** implementation of UserDetailsService interface */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = null;
	    User user = findByEmail(username);
		if(user == null) 
			throw new UsernameNotFoundException("Could not find user " + username);
	    userDetails = new MyUserDetails(user, true, true, true, AuthorityUtils.createAuthorityList("ROLE_USER") );
	    return userDetails;		
	}
	
}