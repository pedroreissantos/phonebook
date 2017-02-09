package pt.tecnico.phonebook.domain;

import java.io.File;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;

import org.jdom2.Element;
import org.jdom2.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.tecnico.phonebook.exception.NameAlreadyExistsException;
import pt.tecnico.phonebook.exception.InvalidPhoneNumberException;
import pt.tecnico.phonebook.exception.InvalidEmailException;
import pt.tecnico.phonebook.exception.ImportDocumentException;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class PhoneBook {
	static final Logger log = LoggerFactory.getLogger(PhoneBook.class);
	static private PhoneBook pb; // Singleton
	private Set<Person> persons;

	public static PhoneBook getInstance() {
		if (pb != null)
			return pb;

		log.trace("new PhoneBook");
		return new PhoneBook();
	}

	private PhoneBook() {
		persons = new HashSet<Person>();
		pb = this;
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

	public void xmlImport(Element element) throws PhoneBookException {
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

		for (Person p: persons)
			element.addContent(p.xmlExport());

		return doc;
	}

	public File resourceFile(String filename) {
		log.trace("Resource: "+filename);
		ClassLoader classLoader = getClass().getClassLoader();
		if (classLoader.getResource(filename) == null) return null;
		return new java.io.File(classLoader.getResource(filename).getFile());
	}

	public Contact findPhoneInAllContacts(int phoneNumber) {
		Contact c;
		for (Person p: getPersons()) {
			log.trace("Called getContactByPhoneNumber for "+p.getName());
			if ((c = p.getContactByPhoneNumber(phoneNumber)) != null)
				return c;
		}
		return null;
	}
}
