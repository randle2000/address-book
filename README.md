mvn archetype:generate -DgroupId=com.sln -DartifactId=address-book -DarchetypeGroupId=co.ntier -DarchetypeArtifactId=spring-mvc-archetype -DinteractiveMode=false

CREATE TABLE users (
  UserID int(11) NOT NULL AUTO_INCREMENT,
  Email varchar(50) NOT NULL,
  Password varchar(50) NOT NULL,
  Enabled tinyint(4) NOT NULL DEFAULT '1',
  RealName varchar(50) DEFAULT NULL,
  PRIMARY KEY (UserID),
  UNIQUE KEY idx_Email (Email)
) ENGINE=InnoDB;

CREATE TABLE persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
);

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



The URI structure :

URI					Method	Action
/contacts				GET		Listing, display all contacts
/contacts				POST	Save or update contact
/contacts/{id}			GET		Display contact {id}
/contacts/add			GET		Display add contact form
/contacts/{id}/update	GET		Display update contact form for {id}
/contacts/{id}/delete	POST	Delete contact {id}


For file upload see: http://www.journaldev.com/2573/spring-mvc-file-upload-example-single-multiple-files
	In contactform.jsp:
		<form.....  enctype="multipart/form-data">
		<label for="imageUpload">Upload</label>
        <form:input type="file" path="imageUpload" id="imageUpload" accept="image/*" />
    It only works with <csrf/> disabled in spring-security.xml, otherwise you get "Method POST not supported"
    In Contact class: private MultipartFile imageUpload;
    Resizing images: http://www.codejava.net/java-se/graphics/how-to-resize-images-in-java  
	
contacts/index.jsp uses post() from my.js to post 