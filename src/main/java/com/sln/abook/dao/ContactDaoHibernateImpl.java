package com.sln.abook.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sln.abook.model.Contact;
import com.sln.abook.model.User;

@Repository("contactRepositoryHibernate")
public class ContactDaoHibernateImpl implements ContactDao {
	
	private final Logger logger = LoggerFactory.getLogger(ContactDaoHibernateImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Contact> findByUser(User user) {
		// this method is not needed as contacts can now be retrieved from user.getContacts() 
		return null;
	}

	@Override
	@Transactional
	public void save(Contact contact) {
		logger.debug("========hiber save() : {}", contact);
		em.persist(contact);
		em.flush();
	}

	@Override
	@Transactional
	public void update(Contact contact) {
		logger.debug("========hiber update() : {}", contact);
		em.merge(contact);
		em.flush();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		logger.debug("========hiber delete() : {}", id);
		//em.createQuery("DELETE FROM Contact contact WHERE contactId=" + id).executeUpdate();
		// alternative way
		Contact contact = em.find(Contact.class, id);
		em.remove(contact);
		logger.debug("========hiber after delete() Does em still contain contact?: {}", em.contains(contact));
	}

}
