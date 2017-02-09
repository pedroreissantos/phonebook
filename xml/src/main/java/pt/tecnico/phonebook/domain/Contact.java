package pt.tecnico.phonebook.domain;

import org.jdom2.Element;
import org.jdom2.DataConversionException;
import java.io.UnsupportedEncodingException;

import pt.tecnico.phonebook.exception.ImportDocumentException;
import pt.tecnico.phonebook.exception.InvalidPhoneNumberException;
import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class Contact {
	private String name;
	private int phoneNumber;
	private Person person;

	protected Contact() { /* for XML import */ }

	public Contact(Person person, String name, Integer phoneNumber) throws InvalidPhoneNumberException, NameAlreadyExistsException {
		setName(name);
		setPhoneNumber(phoneNumber);
		setPerson(person);
	}

	public Contact(Person person, Element xml) throws PhoneBookException {
		xmlImport(xml);
		setPerson(person);
	}

	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public Person getPerson() { return person; }
	public int getPhoneNumber() { return phoneNumber; }

	public void setPhoneNumber(Integer phoneNumber) throws InvalidPhoneNumberException {
		if (phoneNumber <= 0)
			throw new InvalidPhoneNumberException(phoneNumber);
		this.phoneNumber = phoneNumber;
	}

	public void setPerson(Person person) throws NameAlreadyExistsException {
		person.addContact(this);
	}

	public void xmlImport(Element contactElement) throws PhoneBookException {
		try {
			setName(new String(contactElement.getAttribute("name").getValue().getBytes("UTF-8")));
			setPhoneNumber(contactElement.getAttribute("phoneNumber").getIntValue());
		} catch (UnsupportedEncodingException | DataConversionException e) {
			throw new ImportDocumentException();
		}
	}

	public Element xmlExport() {
		Element element = new Element("contact");
		element.setAttribute("name", getName());
		element.setAttribute("phoneNumber", Integer.toString(getPhoneNumber()));

		return element;
	}
}
