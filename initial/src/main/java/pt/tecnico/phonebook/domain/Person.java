package pt.tecnico.phonebook.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import pt.tecnico.phonebook.exception.NameAlreadyExistsException;

public class Person {
	private String name;
	private PhoneBook pb;
	private List<Contact> contacts;

	public Person(PhoneBook pb, String name) throws NameAlreadyExistsException {
		setName(name);
		contacts = new ArrayList<Contact>();
		setPhoneBook(pb);
	}

	public void setName(String name) { this.name = name; }

	public String getName() { return name; }

	public Collection<Contact> getContacts() {
		return Collections.unmodifiableCollection(contacts);
	}

	public void setPhoneBook(PhoneBook pb) throws NameAlreadyExistsException {
		pb.addPerson(this);
	}

	public void addContact(Contact contactToBeAdded) throws NameAlreadyExistsException {
		if (hasContact(contactToBeAdded.getName()))
			throw new NameAlreadyExistsException(contactToBeAdded.getName());

		contacts.add(contactToBeAdded);
	}

	public Contact getContactByName(String name) {
		for (Contact c: contacts)
			if (c.getName().equals(name))
				return c;
		return null;
	}

	public boolean hasContact(String contactName) {
		return getContactByName(contactName) != null;
	}
}
