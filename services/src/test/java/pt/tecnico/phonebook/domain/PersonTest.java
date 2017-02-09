package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.phonebook.AbstractTest;
import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class PersonTest extends AbstractTest {
	static final String personName = "Manuel";
	static final String contactName = "Xico";
	static final int contactPhone = 123123123;
	protected PhoneBook pb;
	protected Person p;

	protected void populate() throws PhoneBookException {
		pb = PhoneBook.getInstance();
		p = new Person(pb, personName);
	}

	@Test
	public void createPerson() throws PhoneBookException {
		// check person was created
		assertNotNull("PhoneBook does not exist", pb);
		assertNotNull("person does not exist", p);
		assertEquals("person was not created", p, pb.getPersonByName(personName));
		assertEquals("person's name was not found", personName, pb.getPersonByName(personName).getName());
	}

	@Test
	public void createContact() throws PhoneBookException {
		new Contact(p, contactName, contactPhone);

		// check contact was created
		assertTrue("contact was not created", p.hasContact(contactName));
	}

	@Test
	public void findContact() throws PhoneBookException {
		Contact c = new Contact(p, contactName, contactPhone);

		// check contact was created
		assertEquals("contact was not found", c, p.getContactByName(contactName));
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicateName() throws PhoneBookException {
		new Person(pb, personName);
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicateContact() throws PhoneBookException {
		Contact c = new Contact(p, contactName, contactPhone);
		p.addContact(c);
	}
}
