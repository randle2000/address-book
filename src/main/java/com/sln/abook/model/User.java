package com.sln.abook.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

// just for sake of example: using getters annotations here (instead of field annotations)
@Entity
@Table(name="users")
@NamedQueries({
	@NamedQuery(name=User.GET_USER_BY_ID, query="select user from User user where userId = :id"),
	@NamedQuery(name=User.GET_USER_BY_EMAIL, query="select user from User user where user.email = :email"), // user.email for sake of example
	@NamedQuery(name=User.GET_ALL_USERS, query="from User")	// select can be omited (http://www.codejava.net/frameworks/hibernate/hibernate-query-language-hql-example)
})
public class User {
	public static final String GET_USER_BY_ID = "get_user_by_id";
	public static final String GET_USER_BY_EMAIL = "get_user_by_email";
	public static final String GET_ALL_USERS = "get_all_users";
	
	private Long userId;
    private String email;
    private String realName;
	private String password;
	private String confirmPassword;
	private boolean enabled;
	private List<Contact> contacts;

	@Transient
	public boolean isNew() {
		return (this.userId == null);
	}
	
	public Contact findContactById(Long id) {
		for (Contact contact : contacts) {
			if (id.equals(contact.getContactId()))
				return contact;
		}
		return null;
	}
	
	public Contact addNewContact(Contact contact) {
		if (!contact.isNew())
			return null;
		contact.setUser(this);
		if (contacts == null)
			contacts = new ArrayList<Contact>();
		if (contacts.add(contact)) {
			return contact;
		} else {
			return null;
		}
	}
	
	public Contact updateContact(Contact contact) { 
		Contact oldContact = findContactById(contact.getContactId());
		if (oldContact == null)
			return null;
		contact.setUser(this);
		int itemIndex = contacts.indexOf(oldContact);
		contacts.set(itemIndex, contact);
		return contact;
	}
	
	public Contact deleteContactById(Long id) {
		Contact contact = findContactById(id);
		if (contact == null)
			return null;
		contacts.remove(contact);
		return contact;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="UserID", columnDefinition = "INT")
	public Long getUserId() {
		return userId;
	}

	@Column(name="Email", unique=true, nullable=false, length=50)
	public String getEmail() {
		return email;
	}

	@Column(name="RealName", length=50)
	public String getRealName() {
		return realName;
	}

	@Column(name="Password", nullable=false, length=50)
	public String getPassword() {
		return password;
	}

	@Transient
	public String getConfirmPassword() {
		return confirmPassword;
	}

	@Column(name="Enabled", columnDefinition = "tinyint", length = 1)
	public boolean isEnabled() {
		return enabled;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
		if (contacts != null)
			for (Contact contact : contacts) {
				contact.setUser(this);
			}
	}

 	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (realName == null) {
			if (other.realName != null)
				return false;
		} else if (!realName.equals(other.realName))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((realName == null) ? 0 : realName.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", realName=" + realName + ", password=" + password
				+ "]";
	}
	
}
