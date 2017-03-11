CREATE TABLE users (
  UserID int(11) NOT NULL AUTO_INCREMENT,
  Email varchar(50) NOT NULL,
  Password varchar(50) NOT NULL,
  Enabled tinyint(4) NOT NULL DEFAULT '1',
  RealName varchar(50) DEFAULT NULL,
  PRIMARY KEY (UserID),
  UNIQUE KEY idx_Email (Email)
) ENGINE=InnoDB;

CREATE TABLE contacts (
  ContactID int NOT NULL AUTO_INCREMENT,
  UserID int NOT NULL,
  Name VARCHAR(30),
  Email varchar(50) NOT NULL,
  Address VARCHAR(255),
  Favorite BOOLEAN,
  Groups VARCHAR(500),
  Gender VARCHAR(1),
  Priority INTEGER,
  Country VARCHAR(10),
  Personality VARCHAR(500),
  PRIMARY KEY (ContactID),
  UNIQUE KEY idx_Email (Email),
  KEY UserID (UserID),
  CONSTRAINT contacts_ibfk_1 FOREIGN KEY (UserID) REFERENCES users (UserID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- this table is used by <remember-me /> in WEB-INF/config/security.xml  
CREATE TABLE persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
);

-- this table is used by JdbcUsersConnectionRepository in WEB-INF/config/social.xml to associate your local users with social users
-- taken from: https://github.com/spring-projects/spring-social/blob/master/spring-social-core/src/main/resources/org/springframework/social/connect/jdbc/JdbcUsersConnectionRepository.sql
create table UserConnection (userId varchar(255) not null,
	providerId varchar(255) not null,
	providerUserId varchar(255),
	rank int not null,
	displayName varchar(255),
	profileUrl varchar(512),
	imageUrl varchar(512),
	accessToken varchar(512) not null,
	secret varchar(512),
	refreshToken varchar(512),
	expireTime bigint,
	primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);
