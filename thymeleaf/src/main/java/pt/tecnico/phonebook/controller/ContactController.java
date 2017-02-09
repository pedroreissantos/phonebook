package pt.tecnico.phonebook.controller;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pt.tecnico.phonebook.dto.ContactDto;
import pt.tecnico.phonebook.service.ListPersonPhoneBook;
import pt.tecnico.phonebook.service.CreateContactService;
import pt.tecnico.phonebook.service.RemoveContactService;

@Controller
@RequestMapping("/contacts")
public class ContactController {
	private static Logger log = LoggerFactory.getLogger(ContactController.class);
	private String name = "";

	@GetMapping()
	public String contactForm(Model model) {
		log.info("listContacts: person:{}", name);
		ListPersonPhoneBook list = new ListPersonPhoneBook(name);
		log.info("listContacts 1");
		list.execute();
		log.info("listContacts 2");
		ContactDto dto = new ContactDto();
		dto.setPersonName(name);
		model.addAttribute("contact", dto);
		model.addAttribute("contacts", list.result());
		log.info("listContacts 3");
		return "contacts"; // nome da página: template/contacts.html
	}

	@PostMapping()
	public String contactSubmit(@ModelAttribute ContactDto contact) {
		log.info("contactSubmit name:{} phone:{} email:{} person:{}", contact.getName(), contact.getPhoneNumber(), contact.getEmail(), contact.getPersonName());
		if (contact.getName().length() == 0) return "redirect:/";
		CreateContactService service = new CreateContactService(name, contact.getName(), contact.getPhoneNumber(), contact.getEmail());
		log.info("contactSubmit 1");
		service.execute();
		log.info("contactSubmit 2");
		return "redirect:/contacts";
	}

	@PostMapping("/person")
	public String contactPerson(@ModelAttribute ContactDto person) {
		log.info("ContactPerson person:{}", person.getPersonName());
		name = person.getPersonName();
		return "redirect:/contacts";
	}

	@PostMapping("/remove")
	public String contactRemove(@ModelAttribute ContactDto contact) {
		log.info("ContactRemove name:{} person:{}", contact.getName(), name);
		RemoveContactService service = new RemoveContactService(name, contact.getName());
		log.info("ContactRemove 1");
		service.execute();
		log.info("ContactRemove 2");
		return "redirect:/contacts";
	}
}
