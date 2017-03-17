package com.sln.abook.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sln.abook.model.User;

/**
 * 
 * Load methods will also load contacts for each user
 * Save methods will also save contacts for each user
 */
@Repository("userRepositoryHibernate")
public class UserDaoHibernateImpl implements UserDao {
	
	private final Logger logger = LoggerFactory.getLogger(UserDaoHibernateImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public User findById(long id) {
		logger.debug("========hiber findById() : {}", id);
//		User user = em.createNamedQuery(User.GET_USER_BY_ID, User.class)
//				.setParameter( "id", id )
//				.getSingleResult();
		// alternative way
		User user = em.find(User.class, id);
		return user;
	}

	@Override
	public User findByEmail(String email) {
		logger.debug("========hiber findByEmail() : {}", email);
		// done differently than in findById() for sake of example (http://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/Hibernate_User_Guide.html#jpql-api)
		TypedQuery<User> query = em.createNamedQuery(User.GET_USER_BY_EMAIL, User.class);
		query.setParameter( "email", email );
		// if no user exists in DB with given email getSingleResult() will throw NoResultException, we should return null
		User user;
		try {
			user = query.getSingleResult();
		} catch(NoResultException e) {
			user = null;
		}
		return user;
	}

	@Override
	public List<User> findAll() {
		List<User> users = em.createNamedQuery(User.GET_ALL_USERS, User.class)
				.getResultList();
		return users;
	}

	@Override
	@Transactional
	public void save(User user) {
		logger.debug("========hiber save() : {}", user);
		em.persist(user);
		em.flush();
		logger.debug("========hiber save() after flush: {}", user);
	}

	@Override
	@Transactional
	public void update(User user) {
		logger.debug("========hiber update() : {}", user);
		em.merge(user);
		em.flush();
		logger.debug("========hiber update() after flush: {}", user);
	}

	@Override
	@Transactional
	public void delete(long id) {
		// see: http://www.codejava.net/frameworks/hibernate/hibernate-basics-3-ways-to-delete-an-entity-from-the-datastore
		// the following line does not work because of org.hibernate.exception.ConstraintViolationException
		//em.createQuery("DELETE FROM User WHERE userId=" + id).executeUpdate();
		User user = em.find(User.class, id);
		em.remove(user);
	}

}
