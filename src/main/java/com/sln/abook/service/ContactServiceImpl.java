package com.sln.abook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sln.abook.dao.ContactDao;
import com.sln.abook.model.Contact;
import com.sln.abook.model.User;

@Service("contactService")
public class ContactServiceImpl implements ContactService {

	private ContactDao contactDao;

	@Autowired
	//@Qualifier("contactRepositoryJdbc")
	@Qualifier("contactRepositoryHibernate")
	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}
	
	// this method should always receive new contact object
	@Override
	public void saveOrUpdate(Contact contact, User user) throws Exception {
		if (contact.isNew()) {
			user.addNewContact(contact);
			contactDao.save(contact);
		} else {
			//if (findById(contact.getContactId())==null)
			//	throw new Exception("Trying to update contact but it doesn't exist in a database");
			user.updateContact(contact);
			contactDao.update(contact);
		}
	}

	@Override
	public void delete(Long id, User user) {
		user.deleteContactById(id);
		contactDao.delete(id);
	}

}
