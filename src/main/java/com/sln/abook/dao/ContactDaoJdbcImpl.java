package com.sln.abook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.sln.abook.model.Contact;
import com.sln.abook.model.User;

@Repository("contactRepositoryJdbc")
public class ContactDaoJdbcImpl implements ContactDao {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
	
	private SqlParameterSource getSqlParameterByModel(Contact contact) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", contact.getContactId());
		paramSource.addValue("userid", contact.getUser().getUserId());
		paramSource.addValue("name", contact.getName());
		paramSource.addValue("email", contact.getEmail());
		paramSource.addValue("address", contact.getAddress());
		paramSource.addValue("favorite", contact.isFavorite());
		// join String
		paramSource.addValue("groups", convertListToDelimitedString(contact.getGroups()));
		paramSource.addValue("gender", contact.getGender());
		paramSource.addValue("priority", contact.getPriority());
		paramSource.addValue("country", contact.getCountry());
		// join String
		paramSource.addValue("personality", convertListToDelimitedString(contact.getPersonality()));
		return paramSource;
	}

	private static final class ContactMapper implements RowMapper<Contact> {
		public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
			Contact contact = new Contact();
			contact.setContactId(rs.getLong("ContactID"));
			//contact.setUserId(rs.getLong("UserID"));
			contact.setName(rs.getString("Name"));
			contact.setEmail(rs.getString("Email"));
			contact.setAddress(rs.getString("Address"));
			contact.setFavorite(rs.getBoolean("Favorite"));
			contact.setGroups(convertDelimitedStringToList(rs.getString("Groups")));
			contact.setGender(rs.getString("Gender"));
			contact.setPriority(rs.getInt("Priority"));
			contact.setCountry(rs.getString("Country"));
			contact.setPersonality(convertDelimitedStringToList(rs.getString("Personality")));
			return contact;
		}
	}
	
	//@Override
	public Contact findById(long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		String sql = "SELECT * FROM contacts WHERE ContactID=:id";

		Contact result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new ContactMapper());
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}

		return result;
	}

	@Override
	public List<Contact> findByUser(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", user.getUserId());
		
		String sql = "SELECT * FROM contacts WHERE UserID=:userid";
		List<Contact> contacts = namedParameterJdbcTemplate.query(sql, params, new ContactMapper());
		return contacts;
	}

	@Override
	public void save(Contact contact) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		String sql = "INSERT INTO contacts (UserID, Name, Email, Address, Favorite, Groups, Gender, Priority, Country, Personality) "
				+ "VALUES ( :userid, :name, :email, :address, :favorite, :groups, :gender, :priority, :country, :personality)";

		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(contact), keyHolder);
		long generatedId = keyHolder.getKey().longValue(); 
		contact.setContactId(generatedId);
	}

	@Override
	public void update(Contact contact) {
		// not updating UserID
		String sql = "UPDATE contacts SET Name=:name, Email=:email, Address=:address, Favorite=:favorite, Groups=:groups, Gender=:gender, Priority=:priority, Country=:country, Personality=:personality " +
				"WHERE ContactID=:id";
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(contact));
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM contacts WHERE ContactID= :id";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}

}
