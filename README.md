# Address Book

+ Rename profiles/dev/*.properties-dummy to *.properties
+ Edit database.properties and socialProviders.properties
+ Crate DB (address_book) and create tables using src/main/resources/tables.sql
	+ tables `users` and `contacts` are used by this app
	+ table `persistent_logins` is used by `<remember-me />` in WEB-INF/config/security.xml  
	+ table `UserConnection` is used by JdbcUsersConnectionRepository in WEB-INF/config/social.xml to associate your local users with social users

+ To compile:
	`mvn clean package`
+ To run locally:
	On Windows:	`java -cp "target/dependency/*" webapp.runner.launch.Main target/*.war --enable-naming`
	On Linux: 	`java -cp 'target/dependency/*' webapp.runner.launch.Main target/*.war --enable-naming`
+ To deploy:
	`mvn clean heroku:deploy`


### Notes 
Also see Spring Social notes in My Java notes

contacts/index.jsp uses post() from my.js to post

This app was initially created using

	mvn archetype:generate -DgroupId=com.sln -DartifactId=address-book -DarchetypeGroupId=co.ntier -DarchetypeArtifactId=spring-mvc-archetype -DinteractiveMode=false


### The URI structure :

|URI					|Method	|Action
|-----------------------|-------|--------------
|/contacts				|GET	|Listing, display all contacts
|/contacts				|POST	|Save or update contact
|/contacts/{id}			|GET	|Display contact {id}
|/contacts/add			|GET	|Display add contact form
|/contacts/{id}/update	|GET	|Display update contact form for {id}
|/contacts/{id}/delete	|POST	|Delete contact {id}

### Switching from JDBC to Hibernate via JPA
Here Hibernate implementation is used over JPA api
To do this I did the following:
+ added artifacts to pom.xml: spring-orm, hibernate-entitymanager
+ added WEB-INF/config/data-jpa.xml (and its import in root-context.xml)
+ added src/main/resources/META-INF/persistence.xml (punit defined there (can be any name) is referenced by data-jpa.xml)
+ added SpringOpenEntityManagerInViewFilter to web.xml
+ added `com.sln.abook.dao.UserDaoHibernateImpl` and `com.sln.abook.dao.ContactDaoHibernateImpl`
+ added annotations to `com.sln.abook.model.User` and `com.sln.abook.model.Contact`
+ added Hibernate properties to /profiles/dev/database.properties and /src/main/resources/application.properties

To switch between JDBC and Hibernate:
In `com.sln.abook.service.UserService` and `com.sln.abook.service.ContactService` inject the corresponding implementation. Like:

	@Autowired
	//@Qualifier("userRepositoryJdbc")
	@Qualifier("userRepositoryHibernate")
	private UserDao userDao;

Annotations in `com.sln.abook.model.User` and `com.sln.abook.model.Contact` will be ignored if using "userRepositoryJdbc"

### OAuth2 with social networks:
	http://sunilkumarpblog.blogspot.in/2016/04/social-login-with-spring-security.html
	http://callistaenterprise.se/blogg/teknik/2014/09/02/adding-social-login-to-a-website-using-spring-social/
	https://www.petrikainulainen.net/spring-social-tutorial/
	https://spring.io/guides/tutorials/spring-boot-oauth2/

### Images and file uploads (not used here)
For file upload see: http://www.journaldev.com/2573/spring-mvc-file-upload-example-single-multiple-files
	In contactform.jsp:
		<form.....  enctype="multipart/form-data">
		<label for="imageUpload">Upload</label>
        <form:input type="file" path="imageUpload" id="imageUpload" accept="image/*" />
    It only works with <csrf/> disabled in spring-security.xml, otherwise you get "Method POST not supported"
    In Contact class: private MultipartFile imageUpload;
    Resizing images: http://www.codejava.net/java-se/graphics/how-to-resize-images-in-java  
	

