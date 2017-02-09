package pt.tecnico.phonebook.controller;

import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.phonebook.domain.*;
import pt.tecnico.phonebook.exception.*;
import pt.tecnico.phonebook.dto.*;

// unused example
public class AtomicInterface {

	private static PhoneBook getPhoneBook() {
                return PhoneBook.getInstance();
        }

	private static Person getPerson(String personName) throws PersonDoesNotExistException {
                Person p = getPhoneBook().getPersonByName(personName);
                if (p == null)
                        throw new PersonDoesNotExistException(personName);
                return p;
        }

	@Atomic
	public static String createPerson(String name) {
		new Person(getPhoneBook(), name);
		return name;
	}

	@Atomic
	public static ContactDto createContact(String personName, String contactName, int phoneNumber, String contactEmail) {
		Contact c = null;
		if (contactEmail.length() == 0)
			c = new Contact(getPerson(personName), contactName, phoneNumber);
		else
			c = new EmailContact(getPerson(personName), contactName, phoneNumber, contactEmail);
		ContactDto dto = c.dto();
		dto.setPersonName(personName);
		return dto;
	}

	@Atomic
	public static List<String> listPeople() {
		List<String> persons = new ArrayList<>();
		for (Person p : getPhoneBook().getPersonSet())
			persons.add(p.getName());
		return persons;
	}

	@Atomic
	public static List<ContactDto> listContacts() {
		List<ContactDto> contacts = new ArrayList<>();
		for (Person p : getPhoneBook().getPersonSet())
			for (Contact c : p.getContactSet()) {
				ContactDto dto = c.dto();
				dto.setPersonName(p.getName());
				contacts.add(dto);
			}
		return contacts;
	}

	@Atomic
	public static void delete() {
		getPhoneBook().cleanup();
	}

}
