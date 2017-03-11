package com.sln.abook.service;

import java.util.List;

import com.sln.abook.model.User;

public interface UserService {

	User findById(long id);
	
	User findByEmail(String email);
	
	List<User> findAll();

	void saveOrUpdate(User user);
	
	void delete(long id);
	
	boolean registerNewUser(String email, String realName, String password);
	
}