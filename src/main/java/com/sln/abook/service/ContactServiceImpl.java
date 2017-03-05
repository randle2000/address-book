package com.sln.abook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sln.abook.dao.ContactDao;
import com.sln.abook.model.Contact;
import com.sln.abook.model.User;

@Service("contactService")
public class ContactServiceImpl implements ContactService {

	private ContactDao contactDao;

	@Autowired
	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}

	@Override
	public Contact findById(long id) {
		return contactDao.findById(id);
	}

	@Override
	public List<Contact> findByUser(User user) {
		return contactDao.findByUser(user);
	}

	@Override
	public void saveOrUpdate(Contact contact) throws Exception {
		if (contact.isNew()) {
			contactDao.save(contact);
		} else {
			if (findById(contact.getContactId())==null)
				throw new Exception("Trying to update contact but it doesn't exist in a database");
			contactDao.update(contact);
		}
	}

	@Override
	public void delete(long id) {
		contactDao.delete(id);
	}

}
