package com.sln.abook.dao;

import java.util.List;

import com.sln.abook.model.User;

public interface UserDao {

	User findById(long id);
	
	User findByEmail(String email);

	List<User> findAll();

	void save(User user);

	void update(User user);

	void delete(long id);
	
}
