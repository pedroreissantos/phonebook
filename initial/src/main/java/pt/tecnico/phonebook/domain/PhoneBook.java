package pt.tecnico.phonebook.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import pt.tecnico.phonebook.exception.NameAlreadyExistsException;

public class PhoneBook {
	private List<Person> persons;

	public PhoneBook() {
		persons = new ArrayList<Person>();
	}

	public Collection<Person> getPersons() {
		return Collections.unmodifiableCollection(persons);
	}

	public Person getPersonByName(String name) {
		for (Person person : persons)
			if (person.getName().equals(name))
				return person;
		return null;
	}

	public boolean hasPerson(String personName) {
		return getPersonByName(personName) != null;
	}

	public void addPerson(Person personToBeAdded) throws NameAlreadyExistsException {
		if (hasPerson(personToBeAdded.getName()))
			throw new NameAlreadyExistsException(personToBeAdded.getName());

		persons.add(personToBeAdded);
	}

	public void cleanup() {
		persons.clear();
	}
}
