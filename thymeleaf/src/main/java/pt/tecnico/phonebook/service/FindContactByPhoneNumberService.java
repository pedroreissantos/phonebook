package pt.tecnico.phonebook.service;

import pt.tecnico.phonebook.domain.PhoneBook;
import pt.tecnico.phonebook.exception.ContactDoesNotExistException;

public class FindContactByPhoneNumberService extends PhoneBookService {

	private int phone;
	private String contact;

	public FindContactByPhoneNumberService(int phone) {
			this.phone = phone;
	}

	public final void dispatch() throws ContactDoesNotExistException {
		contact = getPhoneBook().findPhoneInAllContacts(phone).getName();
	}

	public final String result() {
		return contact;
	}
}
