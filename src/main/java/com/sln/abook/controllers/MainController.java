package com.sln.abook.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sln.abook.model.User;
import com.sln.abook.service.MyUser;
import com.sln.abook.service.MyLocalUser;
import com.sln.abook.service.UserService;
import com.sln.abook.validator.RegisterFormValidator;

//http://www.tikalk.com/redirectattributes-new-feature-spring-mvc-31/
//https://en.wikipedia.org/wiki/Post/Redirect/Get
//http://www.oschina.net/translate/spring-mvc-flash-attribute-example
@Controller
public class MainController {

	private final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	RegisterFormValidator registerFormValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(registerFormValidator);
	}

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns com.sln.model.User object of current authenticated user
	 */
	private User currentAuthenticatedUser() {
		MyUser myUser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return myUser.getUser();
	}
	
	/**
	 * Returns true of user is authenticated
	 */
	private boolean isLoggedIn() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return false;
		}
		return !(auth instanceof AnonymousAuthenticationToken);
	}
	
	/**
	 * Authenticates user programmatically
	 */
	private void doManualLogin(User user) {
		//Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), Arrays.<GrantedAuthority>asList(new SimpleGrantedAuthority("ROLE_USER")));
		UserDetails userDetails = new MyLocalUser(user, true, true, true, AuthorityUtils.createAuthorityList("ROLE_LOCALUSER"));
		// the following will work even if you pass null instead of userDetails.getPassword()
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	/**
	 * If the login is from remember me cookie, refer
	 * org.springframework.security.authentication.AuthenticationTrustResolverImpl
	 */
	private boolean isRememberMeAuthenticated() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}

		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
	}
	
	/**
	 * save targetURL in session
	 */
	private void setRememberMeTargetUrlToSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.setAttribute("targetUrl", "/register/update");
		}
	}

	/**
	 * get targetURL from session
	 */
	private String getRememberMeTargetUrlFromSession(HttpServletRequest request){
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if(session!=null){
			targetUrl = session.getAttribute("targetUrl")==null ? "" : session.getAttribute("targetUrl").toString();
		}
		return targetUrl;
	}
	
	
	
	// ***** Mappings *****
	
	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("activeHome", true);	// this is will set [Home] button in a top menu in header.jsp to active
		model.setViewName("index");
		return model;
	}

	// I don't user /signin** URI in my project but some social sites redirect to this URI when user presses Cancel during authentication
	@RequestMapping(value = {"/login", "/signin**"}, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");

			//login form for update, if login error, get the targetUrl from session again.
			String targetUrl = getRememberMeTargetUrlFromSession(request);
			if(StringUtils.hasText(targetUrl)){
				model.addObject("targetUrl", targetUrl);
				model.addObject("loginUpdate", true);
			}
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		
		if (error == null && logout == null && isLoggedIn()) {
			// aready logged in and clicked on Login: redirect to contacts
			model.setViewName("redirect:contacts");
		} else {
			model.addObject("activeLogin", true);	// this is will set [Login] button in a top menu in header.jsp to active
			model.setViewName("login");
		}

		return model;
	}
	
	
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterForm(ModelMap model) {
		logger.debug("showRegisterForm()");
		
		if (isLoggedIn()) {
			// /register/update will prompt for login/pass if user is from "remember cookie"
			return "redirect:/register/update";
		} else {
			User user = new User();
			model.addAttribute("registerForm", user);
			model.addAttribute("activeRegister", true);	// this is will set [Register] button in a top menu in header.jsp to active
			return "register";
		}
	}
	
	// save or update user
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerOrUpdateUser(@ModelAttribute("registerForm") @Validated User user,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

		logger.debug("registerOrUpdateUser() : {}", user);

		if (result.hasErrors()) {
			if (user.isNew()) {
				model.addAttribute("activeRegister", true);	// this is will set [Register] button in a top menu in header.jsp to active
			} else {
				model.addAttribute("activeRegisterUpdate", true);	// this is will set [Update] button in a top menu in header.jsp to active
			}
			return "register";
		}
		
		// is this new user registering or logged in user updating his info?
		if (user.isNew()) {
			User existingUser = userService.findByEmail(user.getEmail());
			if (existingUser != null) {
				model.addAttribute("error", "This email is already registered!");
				model.addAttribute("activeRegister", true);	// this is will set [Register] button in a top menu in header.jsp to active
				return "register";
			}
		}

		redirectAttributes.addFlashAttribute("css", "success");
		if(user.isNew()){
			redirectAttributes.addFlashAttribute("msg", "You have registered successfully!");
		} else {
			redirectAttributes.addFlashAttribute("msg", "Your info is updated!");
		}
		
		userService.saveOrUpdate(user);
		//if (!isLoggedIn())
		doManualLogin(user);
		
		// POST/REDIRECT/GET
		return "redirect:/";

		// POST/FORWARD/GET
		// return "user/list";
	}
	
	/**
	 * This update page is for users with login with password only.
	 * If user is logged in via remember me cookie, then ask for password again.
	 * to avoid updating user info via stolen remember me cookie
	 */
	@RequestMapping(value = "/register/update**", method = RequestMethod.GET)
	public ModelAndView updatePage(HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		
		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request);
			model.addObject("loginUpdate", true);
			model.setViewName("/login");
		} else {
			User user = currentAuthenticatedUser();
			model.addObject("registerForm", user);
			model.addObject("activeRegisterUpdate", true);	// this is will set [Update] button in a top menu in header.jsp to active
			model.setViewName("register");
		}

		return model;
	}
	
	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();
		
		//check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}
		
		model.setViewName("403");
		return model;
	}
	
}
