package pt.tecnico.phonebook.domain;

import java.io.File;
import java.util.*;

import org.jdom2.Element;
import org.jdom2.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.ContactDoesNotExistException;

public class PhoneBook extends PhoneBook_Base {
	static final Logger log = LoggerFactory.getLogger(PhoneBook.class);

	public static PhoneBook getInstance() {
		PhoneBook pb = FenixFramework.getDomainRoot().getPhonebook();
		if (pb != null)
			return pb;

		log.trace("new PhoneBook");
		return new PhoneBook();
	}

	private PhoneBook() {
		setRoot(FenixFramework.getDomainRoot());
	}

	public Person getPersonByName(String name) {
		for (Person person : getPersonSet()) {
			if (person.getName().equals(name)) {
				return person;
			}
		}
		return null;
	}

	public boolean hasPerson(String personName) {
		return getPersonByName(personName) != null;
	}

	@Override
	public void addPerson(Person personToBeAdded) throws NameAlreadyExistsException {
		if (hasPerson(personToBeAdded.getName()))
			throw new NameAlreadyExistsException(personToBeAdded.getName());

		super.addPerson(personToBeAdded);
	}

	public void cleanup() {
		for (Person p: getPersonSet())
			p.remove();
	}

	public void xmlImport(Element element) {
		for (Element node: element.getChildren("person")) {
			String name = node.getAttribute("name").getValue();
			Person person = getPersonByName(name);

			if (person == null) // Does not exist
				person = new Person(this, name);

			person.xmlImport(node);
		}
	}

	public Document xmlExport() {
		Element element = new Element("phonebook");
		Document doc = new Document(element);

		for (Person p: getPersonSet())
			element.addContent(p.xmlExport());

		return doc;
	}

	public File resourceFile(String filename) {
		log.trace("Resource: "+filename);
		ClassLoader classLoader = getClass().getClassLoader();
		if (classLoader.getResource(filename) == null) return null;
		return new java.io.File(classLoader.getResource(filename).getFile());
	}

	public Contact findPhoneInAllContacts(int phoneNumber) throws ContactDoesNotExistException {
		Contact c;
		for (Person p: getPersonSet())
			if ((c = p.getContactByPhoneNumber(phoneNumber)) != null)
				return c;
		throw new ContactDoesNotExistException(""+phoneNumber);
	}

	public int getPhoneNumberByContact(String name) throws ContactDoesNotExistException {
		Contact c;
		for (Person p: getPersonSet())
			if ((c = p.getContactByName(name)) != null)
				return c.getPhoneNumber();
		throw new ContactDoesNotExistException(name);
	}

	public List<Person> getPeopleWithContact(String name) throws ContactDoesNotExistException {
		List<Person> people = new ArrayList<Person>();
		for (Person p: getPersonSet())
			if (p.getContactByName(name) != null)
				people.add(p);
		if (people.size() == 0) throw new ContactDoesNotExistException(name);
		return people;
	}
}
