package pt.tecnico.phonebook.domain;

import org.jdom2.Element;
import java.io.UnsupportedEncodingException;

import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.InvalidPhoneNumberException;
import pt.tecnico.phonebook.exception.InvalidEmailException;
import pt.tecnico.phonebook.exception.ImportDocumentException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class Person extends Person_Base {

	public Person(PhoneBook pb, String name) {
		super();
		setName(name);
		setPhoneBook(pb);
	}

	public Person(PhoneBook pb, Element xml) throws NameAlreadyExistsException {
		super();
		xmlImport(xml);
		setPhoneBook(pb);
	}

	@Override
	public void setPhoneBook(PhoneBook pb) throws NameAlreadyExistsException {
		if (pb == null)
			super.setPhoneBook(null);
		else
			pb.addPerson(this);
	}

	@Override
	public void addContact(Contact contactToBeAdded) throws NameAlreadyExistsException {
		if (hasContact(contactToBeAdded.getName()))
			throw new NameAlreadyExistsException(contactToBeAdded.getName());

		super.addContact(contactToBeAdded);
	}

	public Contact getContactByName(String name) {
		for (Contact contact: getContactSet())
			if (contact.getName().equals(name))
				return contact;
		return null;
	}

	public boolean hasContact(String contactName) {
		return getContactByName(contactName) != null;
	}

	public void remove() {
		for (Contact c: getContactSet())
			c.remove();
		setPhoneBook(null);
		deleteDomainObject();
	}

	public void xmlImport(Element personElement) throws PhoneBookException {
		// clear current phone book
		for (Contact c: getContactSet())
			c.remove();

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

		for (Contact c: getContactSet())
			contactsElement.addContent(c.xmlExport());

		return element;
	}

	public Contact getContactByPhoneNumber(int phoneNumber) {
		// TODO (mockup example)
		return null;
	}
}
