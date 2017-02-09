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
import pt.tecnico.phonebook.service.ListPeopleService;
import pt.tecnico.phonebook.service.CreatePersonService;
import pt.tecnico.phonebook.service.RemovePersonService;

@Controller
@RequestMapping("/people")
public class PeopleController {
	private static Logger log = LoggerFactory.getLogger(PhoneBookController.class);

	@GetMapping()
	public String personForm(Model model) {
		log.info("phonebookForm");
		ListPeopleService people = new ListPeopleService();
		log.info("phonebookForm 1");
		people.execute();
		log.info("phonebookForm 2");
		List<ContactDto> contacts = new ArrayList<>();
		for (String name: people.result()) {
			ContactDto dto = new ContactDto();
			dto.setPersonName(name);
			contacts.add(dto);
			log.info("phonebookForm 3: "+name);
		}
		log.info("phonebookForm 3: #"+contacts.size());
		model.addAttribute("person", new ContactDto());
		model.addAttribute("persons", contacts);
		return "person";
	}

	@PostMapping()
	public String personSubmit(@ModelAttribute ContactDto person) {
		log.info("PhoneBookSubmit name:{}", person.getPersonName());
		if (person.getPersonName().length() == 0) return "redirect:/";
		CreatePersonService service = new CreatePersonService(person.getPersonName());
		log.info("PhoneBookSubmit 1");
		service.execute();
		log.info("PhoneBookSubmit 2");
		return "redirect:/people";
	}

	@PostMapping("/remove")
	public String personRemove(@ModelAttribute ContactDto person) {
		log.info("PhoneBookRemove name:{}", person.getPersonName());
		RemovePersonService service = new RemovePersonService(person.getPersonName());
		log.info("PhoneBookRemove 1");
		service.execute();
		log.info("PhoneBookRemove 2");
		return "redirect:/people";
	}

	@RequestMapping("/delete")
	public String deletePhoneBook() {
		log.info("Delete PhoneBook");
		return "redirect:/";
	}
}
