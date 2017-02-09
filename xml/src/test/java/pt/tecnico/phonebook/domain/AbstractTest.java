package pt.tecnico.phonebook.domain;

import pt.tecnico.phonebook.exception.PhoneBookException;

import org.junit.Before;

public abstract class AbstractTest {
	protected PhoneBook pb;
	protected Person p;

	@Before // run before each test
	public void setUp() throws Exception {
		populate();
	}

	// may be redefined on each test class
	protected void populate() throws PhoneBookException {
		pb = PhoneBook.getInstance();
		pb.cleanup();
		p = new Person(pb, "Manuel");
	}
}
