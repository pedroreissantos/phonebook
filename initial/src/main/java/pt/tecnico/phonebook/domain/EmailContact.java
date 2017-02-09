package pt.tecnico.phonebook.domain;

import pt.tecnico.phonebook.exception.InvalidPhoneNumberException;
import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.InvalidEmailException;

public class EmailContact extends Contact {
	public String email;

	public EmailContact(Person person, String name, Integer phoneNumber, String email) throws InvalidPhoneNumberException, NameAlreadyExistsException, InvalidEmailException {
		super(person, name, phoneNumber);
		setEmail(email);
	}

	public String getEmail() { return email; }

	public void setEmail(String email) throws InvalidEmailException {
		if (email.indexOf('@') < 0) {
			throw new InvalidEmailException(email);
		}
		this.email = email;
	}
}
