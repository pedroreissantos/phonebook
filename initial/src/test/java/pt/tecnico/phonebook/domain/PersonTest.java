package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class PersonTest {

	@Test
	public void createPerson() throws PhoneBookException {
		PhoneBook pb = new PhoneBook();
		new Person(pb, "Manuel");

		// check person was created
		assertEquals("person was not created", "Manuel", pb.getPersonByName("Manuel").getName());
	}

	@Test
	public void createContact() throws PhoneBookException {
		PhoneBook pb = new PhoneBook();
		Person p = new Person(pb, "Manuel");
		new Contact(p, "Xico", 123123123);

		// check contact was created
		assertTrue("contact was not created", p.hasContact("Xico"));
	}

	@Test
	public void findContact() throws PhoneBookException {
		PhoneBook pb = new PhoneBook();
		Person p = new Person(pb, "Manuel");
		Contact c = new Contact(p, "Xico", 123123123);

		// check contact was created
		assertEquals("contact was not found", c, p.getContactByName("Xico"));
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicateName() throws PhoneBookException {
		PhoneBook pb = new PhoneBook();
		new Person(pb, "Rui");
		new Person(pb, "Rui");
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicateContact() throws PhoneBookException {
		PhoneBook pb = new PhoneBook();
		Person p = new Person(pb, "Rui");
		Contact c = new Contact(p, "Xico", 123123123);
		p.addContact(c);
	}
}
