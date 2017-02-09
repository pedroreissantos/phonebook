package pt.tecnico.phonebook.domain;

import pt.tecnico.phonebook.exception.InvalidPhoneNumberException;
import pt.tecnico.phonebook.exception.NameAlreadyExistsException;

public class Contact {
	private String name;
	private int phoneNumber;
	private Person person;

	public Contact(Person person, String name, Integer phoneNumber) throws InvalidPhoneNumberException, NameAlreadyExistsException {
		setName(name);
		setPhoneNumber(phoneNumber);
		setPerson(person);
	}

	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public Person getPerson() { return person; }
	public int getPhoneNumber() { return phoneNumber; }

	public void setPhoneNumber(Integer phoneNumber) throws InvalidPhoneNumberException {
		if (phoneNumber <= 0)
			throw new InvalidPhoneNumberException(phoneNumber);
		this.phoneNumber = phoneNumber; // super.setPhoneNumber(phoneNumber);
	}

	public void setPerson(Person person) throws NameAlreadyExistsException {
		person.addContact(this);
	}
}
