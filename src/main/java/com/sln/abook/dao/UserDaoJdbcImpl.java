package com.sln.abook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
//import org.springframework.util.StringUtils;

import com.sln.abook.model.Contact;
import com.sln.abook.model.User;

/**
 * 
 * All load methods load contacts for each user
 * But save methods do not save contacts for each user
 */
@Repository("userRepositoryJdbc")
public class UserDaoJdbcImpl implements UserDao {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	@Qualifier("contactRepositoryJdbc")
	ContactDao contactDao;
	
	private SqlParameterSource getSqlParameterByModel(User user) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", user.getUserId());
		paramSource.addValue("email", user.getEmail());
		paramSource.addValue("realname", user.getRealName());
		paramSource.addValue("password", user.getPassword());
		paramSource.addValue("enabled", user.isEnabled());
		return paramSource;
	}

	private static final class UserMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getLong("UserID"));
			user.setEmail(rs.getString("Email"));
			user.setRealName(rs.getString("RealName"));
			user.setPassword(rs.getString("Password"));
			user.setEnabled(rs.getBoolean("Enabled"));
			return user;
		}
	}
	
	private void loadContacts(User user) {
		List<Contact> contacts = contactDao.findByUser(user);
		user.setContacts(contacts);
	}

	@Override
	public User findById(long id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		String sql = "SELECT * FROM users WHERE UserID=:id";

		User user = null;
		try {
			user = namedParameterJdbcTemplate.queryForObject(sql, params, new UserMapper());
			loadContacts(user);
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}

		return user;
	}
	
	@Override
	public User findByEmail(String email) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
	
		String sql = "SELECT * FROM users WHERE Email=:email";
	
		User user = null;
		try {
			user = namedParameterJdbcTemplate.queryForObject(sql, params, new UserMapper());
			loadContacts(user);
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}
	
		return user;
	}

	@Override
	public List<User> findAll() {
		String sql = "SELECT * FROM users";
		List<User> users = namedParameterJdbcTemplate.query(sql, new UserMapper());
		for (User user : users) {
			loadContacts(user);
		}
		return users;
	}

	@Override
	public void save(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		String sql = "INSERT INTO users (Email, RealName, Password, Enabled) "
				+ "VALUES ( :email, :realname, :password, :enabled)";

		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(user), keyHolder);
		long generatedId = keyHolder.getKey().longValue(); 
		user.setUserId(generatedId);
		
		// also insert record into userrole table
		//sql = "INSERT INTO userrole (UserID, Email) VALUES (:id, :email)";
		//namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(user));
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE users SET Email=:email, RealName=:realname, Password=:password, Enabled=:enabled WHERE UserID=:id";
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(user));
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM users WHERE UserID= :id";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}

}