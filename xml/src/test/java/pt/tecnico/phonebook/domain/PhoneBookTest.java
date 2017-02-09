package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class PhoneBookTest extends AbstractTest {

	@Test
	public void createPerson() throws PhoneBookException {
		// check person was created
		assertTrue("person was not created", pb.hasPerson("Manuel"));
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void duplicatePerson() throws PhoneBookException {
		pb.addPerson(p);
	}

	@Test
	public void findPerson() throws PhoneBookException {
		new Person(pb, "Rui");
		new Person(pb, "Joana");

		// check person was created
		assertEquals("contact was not found", p, pb.getPersonByName("Manuel"));
	}

	@Test
	public void clean() throws PhoneBookException {
		new Person(pb, "Rui");
		pb.cleanup();
		assertNull("user Manuel not removed", pb.getPersonByName("Manuel"));
		assertNull("user Rui not removed", pb.getPersonByName("Rui"));
	}
}
