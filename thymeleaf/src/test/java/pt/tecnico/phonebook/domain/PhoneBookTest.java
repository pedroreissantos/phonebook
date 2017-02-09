package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.phonebook.AbstractTest;
import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class PhoneBookTest extends AbstractTest {
	static final String personName = "Manuel";
	static final String otherName = "Xico";
	static final String anotherName = "Joana";
	protected PhoneBook pb;

	protected void populate() throws PhoneBookException {
		pb = PhoneBook.getInstance();
	}

	@Test
	public void createPerson() throws PhoneBookException {
		new Person(pb, personName);
		// check person was created
		assertTrue("person was not created", pb.hasPerson(personName));
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicatePerson() throws PhoneBookException {
		Person p = new Person(pb, personName);
		pb.addPerson(p);
	}

	@Test
	public void findPerson() throws PhoneBookException {
		Person p = new Person(pb, personName);
		new Person(pb, otherName);
		new Person(pb, anotherName);

		// check person was created
		assertEquals("user was not found", p, pb.getPersonByName(personName));
	}

	@Test
	public void clean() throws PhoneBookException {
		new Person(pb, personName);
		new Person(pb, otherName);
		pb.cleanup();
		assertNull("user "+personName+" not removed", pb.getPersonByName(personName));
		assertNull("user "+otherName+" not removed", pb.getPersonByName(otherName));
	}
}
