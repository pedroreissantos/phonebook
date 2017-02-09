package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import pt.tecnico.phonebook.exception.InvalidPhoneNumberException;
import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class ContactTest extends AbstractTest {

	@Test
	public void createContact() throws PhoneBookException {
		Contact c = new Contact(p, "Rui", 123456789);

		// check contact was created
		assertEquals("contact was not found", c, pb.getPersonByName("Manuel").getContactByName("Rui"));
	}

	@Test(expected = InvalidPhoneNumberException.class)
	public void invalidPhoneNumber() throws PhoneBookException {
		new Contact(p, "Rui", 0);
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicateContact() throws PhoneBookException {
		new Contact(p, "Rui", 123456789);
		new Contact(p, "Rui", 987654321);
	}
}
