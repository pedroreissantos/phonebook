package pt.tecnico.phonebook.service;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.tecnico.phonebook.AbstractTest;
import pt.tecnico.phonebook.domain.Person;
import pt.tecnico.phonebook.domain.Contact;
import pt.tecnico.phonebook.domain.PhoneBook;
import pt.tecnico.phonebook.exception.ContactDoesNotExistException;

public class FindContactByPhoneNumberTest extends AbstractTest {

	private static final String person = "Joana";
	private static final String contact1 = "Xana", contact2 = "Miguel";
	private static final int phone1 = 123456789, phone2 = 987654321, phone3 = 123123123;

	protected void populate() {
		Person p = new Person(PhoneBook.getInstance(), person);
		new Contact(p, contact1, phone1);
		new Contact(p, contact2, phone2);
	}

	@Test
	public void success() {
		FindContactByPhoneNumberService service =
			new FindContactByPhoneNumberService(phone1);
		service.execute();
		assertEquals(service.result(), contact1);
	}

	@Test(expected = ContactDoesNotExistException.class)
	public void findNonexistingContact() throws ContactDoesNotExistException {
		new FindContactByPhoneNumberService(phone3).execute();
	}
}
