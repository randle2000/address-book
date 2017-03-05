package com.sln.abook.service;

import java.util.List;

import com.sln.abook.model.Contact;
import com.sln.abook.model.User;

public interface ContactService {
	
	Contact findById(long id);
	
	List<Contact> findByUser(User user);

	void saveOrUpdate(Contact contact) throws Exception;
	
	void delete(long id);
	
}