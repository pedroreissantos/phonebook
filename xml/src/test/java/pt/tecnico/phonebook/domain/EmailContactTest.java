package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import pt.tecnico.phonebook.exception.InvalidEmailException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class EmailContactTest extends AbstractTest {

	@Test
	public void createEmailContact() throws PhoneBookException {
		new EmailContact(p, "Rui", 123456789, "rui@gmail.com");

		// check contact's email
		assertEquals("contact with email was not created", "rui@gmail.com", ((EmailContact)pb.getPersonByName("Manuel").getContactByName("Rui")).getEmail());
	}

	@Test(expected = InvalidEmailException.class)
	public void invalidEmail() throws PhoneBookException {
		new EmailContact(p, "Rui", 123456789, "rui.gmail.com");
	}
}
