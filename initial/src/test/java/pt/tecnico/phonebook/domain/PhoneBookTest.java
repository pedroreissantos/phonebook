package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class PhoneBookTest {

	@Test
	public void createPerson() throws PhoneBookException {
		PhoneBook pb = new PhoneBook();
		new Person(pb, "Manuel");

		// check person was created
		assertTrue("person was not created", pb.hasPerson("Manuel"));
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicatePerson() throws PhoneBookException {
		PhoneBook pb = new PhoneBook();
		Person p = new Person(pb, "Rui");
		pb.addPerson(p);
	}

	@Test
	public void findPerson() throws PhoneBookException {
		PhoneBook pb = new PhoneBook();
		Person p = new Person(pb, "Manuel");
		new Person(pb, "Rui");
		new Person(pb, "Joana");

		// check person was created
		assertEquals("contact was not found", p, pb.getPersonByName("Manuel"));
	}

	@Test
	public void clean() throws PhoneBookException {
		PhoneBook pb = new PhoneBook();
		new Person(pb, "Rui");
		new Person(pb, "Manuel");
		pb.cleanup();
		assertNull("user Manuel not removed", pb.getPersonByName("Manuel"));
		assertNull("user Rui not removed", pb.getPersonByName("Rui"));
	}
}
