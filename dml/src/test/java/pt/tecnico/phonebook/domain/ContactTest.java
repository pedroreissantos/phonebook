package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import pt.tecnico.phonebook.exception.InvalidPhoneNumberException;
import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class ContactTest extends AbstractTest {
	static final String personName = "Manuel";
	static final String contactName = "Rui";
	static final int contactPhone = 123456789;
	protected Person p;

	protected void populate() throws PhoneBookException {
		super.populate();
		p = new Person(pb, personName);
	}

	@Test
	public void createContact() throws PhoneBookException {
		Contact c = new Contact(p, contactName, contactPhone);

		// check contact was created
		assertEquals("user was not found", c, pb.getPersonByName(personName).getContactByName(contactName));
	}

	@Test(expected = InvalidPhoneNumberException.class)
	public void invalidPhoneNumber() throws PhoneBookException {
		new Contact(p, contactName, 0);
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicateContact() throws PhoneBookException {
		new Contact(p, contactName, contactPhone);
		new Contact(p, contactName, 987654321);
	}
}
