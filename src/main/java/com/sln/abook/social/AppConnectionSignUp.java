package com.sln.abook.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import com.sln.abook.service.UserService;

/**
 * If no local user associated with the given connection then
 * connection signup will create a new local user from the given connection.
 */
public class AppConnectionSignUp implements ConnectionSignUp {

	private static final Logger logger = LoggerFactory.getLogger(AppConnectionSignUp.class);
	
    @Autowired
    @Qualifier(value = "userService")
    private UserService userService;

    // Actually it's better to use your own custom Exception here
    // see example in http://sunilkumarpblog.blogspot.in/2016/04/social-login-with-spring-security.html
    //		https://github.com/sunilpulugula/SpringSecuritySocialLoginExample/blob/master/src/main/java/com/spring/security/social/login/example/service/RegistrationUserDetailService.java
    //		https://github.com/sunilpulugula/SpringSecuritySocialLoginExample/blob/master/src/main/java/com/spring/security/social/login/example/exception/UserAlreadyExistAuthenticationException.java
    @Override
    public String execute(final Connection<?> connection) {
    	UserProfile userProfile = connection.fetchUserProfile(); 
        String userId = connection.getKey().getProviderUserId();
        String providerId = connection.getKey().getProviderId();
        String email = userProfile.getEmail();
        String realName = userProfile.getName();
        
        logger.debug("--------- AppConnectionSignUp.execute(): {}", userId);
        
        String password = providerId + ": " + email;
        if (!userService.registerNewUser(userId, realName, password)) {
        	throw new AuthenticationServiceException("User already exist!");
        }
    	
    	return userId;
    }


}
