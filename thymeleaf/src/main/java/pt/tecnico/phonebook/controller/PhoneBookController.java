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
import pt.tecnico.phonebook.service.FindPersonsWithContactService;
import pt.tecnico.phonebook.service.FindContactByPhoneNumberService;
import pt.tecnico.phonebook.service.FindPhoneNumberService;

@Controller
@RequestMapping("/phonebook")
public class PhoneBookController {
	private static Logger log = LoggerFactory.getLogger(PhoneBookController.class);
	private ContactDto dto = new ContactDto();
	private List<ContactDto> list = new ArrayList<ContactDto>();

	@GetMapping()
	public String phonebookForm(Model model) {
		log.info("phonebookForm");
		model.addAttribute("contact", dto);
		model.addAttribute("persons", list);
		return "phonebook";
	}

	@PostMapping("/find/people")
	public String findPeople(@ModelAttribute ContactDto person) {
		log.info("findPeople name:{}", person.getName());
		FindPersonsWithContactService service = new FindPersonsWithContactService(person.getName());
		log.info("findPeople 1");
		service.execute();
		log.info("findPeople 2");
		list.clear();
		for (String s: service.result())
			list.add(new ContactDto(s, 0));
		log.info("findPeople 2");
		return "redirect:/phonebook";
	}

	@PostMapping("/find/phone")
	public String findPhone(@ModelAttribute ContactDto person) {
		log.info("findPhone number:{}", person.getPhoneNumber());
		FindContactByPhoneNumberService service = new FindContactByPhoneNumberService(person.getPhoneNumber());
		log.info("findPhone 1");
		service.execute();
		log.info("findPhone 2");
		dto.setName(service.result());
		log.info("findPhone 3");
		return "redirect:/phonebook";
	}

	@PostMapping("/find/contact")
	public String findContact(@ModelAttribute ContactDto person) {
		log.info("findContact name:{}", person.getName());
		FindPhoneNumberService service = new FindPhoneNumberService(person.getName());
		log.info("findContact 1");
		service.execute();
		log.info("findContact 2");
		dto.setPhoneNumber(service.result());
		log.info("findContact 3");
		return "redirect:/phonebook";
	}

	@RequestMapping("/delete")
	public String deletePhoneBook() {
		log.info("deletePhoneBook");
		return "redirect:/";
	}
}
