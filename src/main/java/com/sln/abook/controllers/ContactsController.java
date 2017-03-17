package com.sln.abook.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sln.abook.model.Contact;
import com.sln.abook.model.User;
import com.sln.abook.service.ContactService;
import com.sln.abook.service.MyUser;
import com.sln.abook.validator.ContactFormValidator;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

	private final Logger logger = LoggerFactory.getLogger(ContactsController.class);
	
	private ContactService contactService;

	@Autowired
	ContactFormValidator contactFormValidator;
	
	@Autowired
	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(contactFormValidator);
	}
	
	/**
	 * Returns com.sln.model.User object of current authenticated user
	 */
	private User currentAuthenticatedUser() {
		MyUser myUser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return myUser.getUser();
	}
	
	private void populateDefaultModel(Model model) {

		List<String> groupsList = new ArrayList<String>();
		groupsList.add("Family");
		groupsList.add("Friends");
		groupsList.add("Colleagues");
		groupsList.add("Enemies");
		groupsList.add("Clients");
		groupsList.add("Employers");
		model.addAttribute("groupsList", groupsList);

		Map<String, String> personality = new LinkedHashMap<String, String>();
		personality.put("Honest", "Honest");
		personality.put("Reliable", "Reliable");
		personality.put("Intelligent", "Intelligent");
		personality.put("Dumb", "Dumb");
		personality.put("Asshole", "Asshole");
		personality.put("Self-centered", "Self-centered");
		model.addAttribute("personalityList", personality);

		List<Integer> priorities = new ArrayList<Integer>();
		priorities.add(1);
		priorities.add(2);
		priorities.add(3);
		priorities.add(4);
		priorities.add(5);
		model.addAttribute("priorityList", priorities);

		Map<String, String> country = new LinkedHashMap<String, String>();
		country.put("UA", "Ukraine");
		country.put("US", "United Stated");
		country.put("NZ", "New Zealand");
		country.put("CA", "Canada");
		model.addAttribute("countryList", country);
	}
	
	// this is will set [Contacts] button in a top menu in header.jsp to active
	// will be called on each request
	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("activeContacts", true);
	}
	
	
	
	// ***** Mappings *****

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String showAllContacts(Model model) {
		logger.debug("showAllContacts()");

		User user = currentAuthenticatedUser();
		model.addAttribute("contacts", user.getContacts());
		return "contacts/index";
	}
	
	// save or update contact
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
	public String saveOrUpdateContact(@ModelAttribute("contactForm") @Validated Contact contact,
			BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) throws Exception {

		logger.debug("saveOrUpdateContact() : {}", contact);
		
		if (result.hasErrors()) {
			populateDefaultModel(model);
			return "contacts/contactform";
		}

		// Add message to flash scope
		redirectAttributes.addFlashAttribute("css", "success");
		if(contact.isNew()) {
		  redirectAttributes.addFlashAttribute("msg", "Contact added successfully!");
		} else {
		  redirectAttributes.addFlashAttribute("msg", "Contact updated successfully!");
		}

		User user = currentAuthenticatedUser();
		contactService.saveOrUpdate(contact, user);

		// POST/REDIRECT/GET
		return "redirect:/contacts/" + contact.getContactId();

		// POST/FORWARD/GET
		// return "/contacts/";
	}	
	
	// show add contact form
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showAddContactForm(Model model) {
		logger.debug("showAddContactForm()");

		Contact contact = new Contact();
		model.addAttribute("contactForm", contact);
		populateDefaultModel(model);
		return "contacts/contactform";
	}	

	// show update form
	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateContactForm(@PathVariable("id") int id, Model model) {
		logger.debug("showUpdateContactForm() : {}", id);

		User user = currentAuthenticatedUser();
		Contact contact = user.findContactById(Long.valueOf(id));
		model.addAttribute("contactForm", contact);
		populateDefaultModel(model);
		return "contacts/contactform";

	}

	// delete contact
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String deleteContact(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
		logger.debug("deleteContact() : {}", id);

		User user = currentAuthenticatedUser();
		contactService.delete(Long.valueOf(id), user);
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Contact is deleted!");
		return "redirect:/contacts";
	}

	// show contact
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showContact(@PathVariable("id") int id, Model model) {
		logger.debug("showContact() id: {}", id);

		User user = currentAuthenticatedUser();
		Contact contact = user.findContactById(Long.valueOf(id));
		if (contact == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Contact not found");
		}
		model.addAttribute("contact", contact);
		return "contacts/show";
	}
	

}
