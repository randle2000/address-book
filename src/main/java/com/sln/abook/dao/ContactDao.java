package com.sln.abook.dao;

import java.util.List;

import com.sln.abook.model.Contact;
import com.sln.abook.model.User;

public interface ContactDao {
	
	//Contact findById(long id, User user);
	
	List<Contact> findByUser(User user);

	void save(Contact contact);

	void update(Contact contact);
	
	void delete(Long id);
	
}