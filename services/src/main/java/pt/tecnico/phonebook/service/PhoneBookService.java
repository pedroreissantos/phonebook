package pt.tecnico.phonebook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.phonebook.domain.Person;
import pt.tecnico.phonebook.domain.PhoneBook;
import pt.tecnico.phonebook.exception.PersonDoesNotExistException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public abstract class PhoneBookService {
	static final Logger log = LoggerFactory.getLogger(PhoneBookService.class);

	@Atomic
	public final void execute() throws PhoneBookException {
		dispatch();
	}

	static PhoneBook getPhoneBook() {
		return PhoneBook.getInstance();
	}

	static Person getPerson(String personName) throws PersonDoesNotExistException {
		Person p = getPhoneBook().getPersonByName(personName);

		if (p == null)
			throw new PersonDoesNotExistException(personName);

		return p;
	}

	protected abstract void dispatch() throws PhoneBookException;
}
