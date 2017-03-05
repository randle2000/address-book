package com.sln.abook.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sln.abook.model.Contact;
import com.sln.abook.service.ContactService;

@Component
public class ContactFormValidator implements Validator {

	@Autowired
	@Qualifier("emailValidator")
	EmailValidator emailValidator;
	
	@Autowired
	ContactService contactService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Contact.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Contact contact = (Contact) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.contactForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.contactForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.contactForm.address");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.contactForm.gender");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty.contactForm.country");

		if(!emailValidator.valid(contact.getEmail())){
			errors.rejectValue("email", "Pattern.contactForm.email");
		}
		
		if(contact.getPriority()==null || contact.getPriority()<=0){
			errors.rejectValue("priority", "NotEmpty.contactForm.priority");
		}

		if(contact.getCountry().equalsIgnoreCase("none")){
			errors.rejectValue("country", "NotEmpty.contactForm.country");
		}

		if (contact.getGroups() == null || contact.getGroups().size() < 2) {
			errors.rejectValue("groups", "Valid.contactForm.groups");
		}

		if (contact.getPersonality() == null || contact.getPersonality().size() < 3) {
			errors.rejectValue("personality", "Valid.contactForm.personality");
		}
	}

}
