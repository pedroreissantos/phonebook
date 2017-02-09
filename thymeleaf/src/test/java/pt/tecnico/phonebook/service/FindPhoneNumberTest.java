package pt.tecnico.phonebook.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.tecnico.phonebook.AbstractTest;
import pt.tecnico.phonebook.domain.PhoneBook;
import pt.tecnico.phonebook.domain.Person;
import pt.tecnico.phonebook.domain.Contact;
import pt.tecnico.phonebook.exception.ContactDoesNotExistException;

public class FindPhoneNumberTest extends AbstractTest {

	private static final String person = "Xana";
	private static final String contact = "Joana", other = "Miguel";
	private static final int phone = 999999999;

	protected void populate() {
		new Contact(new Person(PhoneBook.getInstance(), person), contact, phone);
	}

	@Test
	public void success() {
		FindPhoneNumberService service = new FindPhoneNumberService(contact);
		service.execute();
		assertEquals(service.result(), phone);
	}

	@Test(expected = ContactDoesNotExistException.class)
	public void findNonexistingContact() throws ContactDoesNotExistException {
		FindPhoneNumberService service = new FindPhoneNumberService(other);
		service.execute();
	}
}
