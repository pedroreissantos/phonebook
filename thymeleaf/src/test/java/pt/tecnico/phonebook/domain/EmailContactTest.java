package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import pt.tecnico.phonebook.AbstractTest;
import pt.tecnico.phonebook.exception.InvalidEmailException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class EmailContactTest extends AbstractTest {
	static final String personName = "Manuel";
	static final String contactName = "Rui";
	static final int contactPhone = 123456789;
	static final String contactEmail = "rui@gmail.com";
	static final String wrongEmail = "rui.gmail.com";

	protected PhoneBook pb;
	protected Person p;

	protected void populate() throws PhoneBookException {
		pb = PhoneBook.getInstance();
		p = new Person(pb, personName);
	}

	@Test
	public void createEmailContact() throws PhoneBookException {
		new EmailContact(p, contactName, contactPhone, contactEmail);

		// check contact's email
		assertNotNull("contact with email was not created", p.getContactByName(contactName));
		assertEquals("contact with email was not found", contactEmail, ((EmailContact)p.getContactByName(contactName)).getEmail());
	}

	@Test(expected = InvalidEmailException.class)
	public void invalidEmail() throws PhoneBookException {
		new EmailContact(p, contactName, contactPhone, wrongEmail);
	}
}
