package pt.tecnico.phonebook;

import pt.tecnico.phonebook.domain.PhoneBook;
import pt.tecnico.phonebook.domain.Person;
import pt.tecnico.phonebook.domain.Contact;
import pt.tecnico.phonebook.domain.EmailContact;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class PhoneBookApplication {
	private PhoneBook pb;

	public static void main(String[] args) {
		System.out.println("*** Welcome to the PhoneBook application! ***");
		PhoneBookApplication pba = new PhoneBookApplication();
		pba.setup();
		pba.print();
	}

	public PhoneBookApplication() { // empty phonebook
		pb = new PhoneBook();
	}

	public void setup() { // phonebook with debug data
		Person person;

		if (!pb.getPersons().isEmpty()) return;

		// setup the initial state if phonebook is empty
		try {
			person = new Person(pb, "Manel");
			new Contact(person, "SOS", 112);
			new Contact(person, "IST", 214315112);
			new EmailContact(person, "Xico", 911919191, "xico@outlook.pt");
			new Contact(person, "Zé", 966669999);

			person = new Person(pb, "Maria");
			new Contact(person, "SOS", 112);
			new Contact(person, "IST", 214315112);
			new EmailContact(person, "Manel", 333333333, "manel@gmail.com");
			new EmailContact(person, "Xana", 963456789, "xana@hotmail.com");
		} catch (PhoneBookException e) { System.err.println(e); }
	}

	public void print() {
		for (Person p: pb.getPersons()) {
			System.out.println("The Contact book of " + p.getName() + " contains " + p.getContacts().size() + " contacts :");
			for (Contact c: p.getContacts())
				System.out.println("\t" + c.getName() + " -> " + c.getPhoneNumber());
		}
	}
}
