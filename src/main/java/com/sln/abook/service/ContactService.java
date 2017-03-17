package com.sln.abook.service;

import com.sln.abook.model.Contact;
import com.sln.abook.model.User;

public interface ContactService {
	
	void saveOrUpdate(Contact contact, User user) throws Exception;
	
	void delete(Long id, User user);
	
}