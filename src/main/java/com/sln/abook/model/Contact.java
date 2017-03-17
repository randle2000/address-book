package com.sln.abook.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

// types of groups and personality were changed from List<String> to String in order to persist fields to DB easier
// conversion is done in getters and setters for these fields
// (not a very good design, note for the future: re-make)
@Entity
@Table(name="contacts")
public class Contact {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ContactID", columnDefinition = "INT")
	private Long contactId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserID", columnDefinition = "INT", nullable = false)
	private User user;
	
	@Column(name="Name", length=30)
	private String name;

	@Column(name="Email", unique=true, nullable=false, length=50)
	private String email;

	@Column(name="Address", length=255)
	private String address;
	
	@Column(name="Favorite", columnDefinition = "bit", length = 1)
	private boolean favorite;

	//private List<String> groups;
	@Column(name="Groups", length=500)
	private String groups;
	
	@Column(name="Gender", length=1)
	private String gender;
	
	@Column(name="Priority")
	private Integer priority;
	
	@Column(name="Country", length=10)
	private String country;
	
	//private List<String> personality;
	@Column(name="Personality", length=500)
	private String personality;

	
	// ************** Sln's methods start
	public boolean isNew() {
		return (this.contactId == null);
	}
	
	private static List<String> convertDelimitedStringToList(String delimitedString) {
		List<String> result = new ArrayList<String>();

		if (!StringUtils.isEmpty(delimitedString)) {
			result = Arrays.asList(StringUtils.delimitedListToStringArray(delimitedString, ","));
		}
		return result;
	}

	private static String convertListToDelimitedString(List<String> list) {
		String result = "";
		if (list != null) {
			result = StringUtils.arrayToCommaDelimitedString(list.toArray());
		}
		return result;
	}
	// ************** Sln's methods end

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (favorite ? 1231 : 1237);
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((personality == null) ? 0 : personality.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (contactId == null) {
			if (other.contactId != null)
				return false;
		} else if (!contactId.equals(other.contactId))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (favorite != other.favorite)
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (personality == null) {
			if (other.personality != null)
				return false;
		} else if (!personality.equals(other.personality))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contact [contactId=" + contactId + ", user=" + user + ", name=" + name + ", email=" + email
				+ ", address=" + address + ", favorite=" + favorite + ", groups=" + groups + ", gender=" + gender
				+ ", priority=" + priority + ", country=" + country + ", personality=" + personality + "]";
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public List<String> getGroups() {
		return convertDelimitedStringToList(groups);
	}

	public void setGroups(List<String> groups) {
		this.groups = convertListToDelimitedString(groups);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getPersonality() {
		return convertDelimitedStringToList(personality);
	}

	public void setPersonality(List<String> personality) {
		this.personality = convertListToDelimitedString(personality);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
