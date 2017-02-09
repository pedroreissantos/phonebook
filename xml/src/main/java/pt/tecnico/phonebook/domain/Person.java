package pt.tecnico.phonebook.domain;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;

import org.jdom2.Element;
import java.io.UnsupportedEncodingException;

import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.InvalidPhoneNumberException;
import pt.tecnico.phonebook.exception.InvalidEmailException;
import pt.tecnico.phonebook.exception.ImportDocumentException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class Person {
	private String name;
	private PhoneBook pb;
	private Set<Contact> contacts;

	public Person(PhoneBook pb, String name) throws NameAlreadyExistsException {
		setName(name);
		contacts = new HashSet<Contact>();
		setPhoneBook(pb);
	}

	public Person(PhoneBook pb, Element xml) throws PhoneBookException {
		contacts = new HashSet<Contact>();
		xmlImport(xml);
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

	public void xmlImport(Element personElement) throws PhoneBookException {
		// clear current phone book
		contacts.clear();

		try {
			setName(new String(personElement.getAttribute("name").getValue().getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new ImportDocumentException();
		}
		Element contacts = personElement.getChild("contacts");

		for (Element contactElement: contacts.getChildren("contact"))
			new Contact(this, contactElement);

		for (Element contactElement: contacts.getChildren("email-contact"))
			new EmailContact(this, contactElement);
	}

	public Element xmlExport() {
		Element element = new Element("person");
		element.setAttribute("name", getName());

		Element contactsElement = new Element("contacts");
		element.addContent(contactsElement);

		for (Contact c: contacts)
			contactsElement.addContent(c.xmlExport());

		return element;
	}

	public Contact getContactByPhoneNumber(int phoneNumber) {
		// TODO (mockup example)
		return null;
	}
}
