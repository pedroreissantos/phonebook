package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class PersonTest extends AbstractTest {

	@Test
	public void createPerson() throws PhoneBookException {
		// check person was created
		assertEquals("person was not created", "Manuel", pb.getPersonByName("Manuel").getName());
	}

	@Test
	public void createContact() throws PhoneBookException {
		new Contact(p, "Xico", 123123123);

		// check contact was created
		assertTrue("contact was not created", p.hasContact("Xico"));
	}

	@Test
	public void findContact() throws PhoneBookException {
		Contact c = new Contact(p, "Xico", 123123123);

		// check contact was created
		assertEquals("contact was not found", c, p.getContactByName("Xico"));
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicateName() throws PhoneBookException {
		new Person(pb, "Manuel");
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicateContact() throws PhoneBookException {
		Contact c = new Contact(p, "Xico", 123123123);
		p.addContact(c);
	}
}
