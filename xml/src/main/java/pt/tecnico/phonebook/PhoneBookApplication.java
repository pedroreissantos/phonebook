package pt.tecnico.phonebook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.tecnico.phonebook.domain.PhoneBook;
import pt.tecnico.phonebook.domain.Person;
import pt.tecnico.phonebook.domain.Contact;
import pt.tecnico.phonebook.domain.EmailContact;
import pt.tecnico.phonebook.exception.PhoneBookException;

public class PhoneBookApplication {
	static final Logger log = LoggerFactory.getLogger(PhoneBookApplication.class);
	private PhoneBook pb;

	public static void main(String[] args) throws Exception {
		System.out.println("*** Welcome to the PhoneBook application! ***");
		PhoneBookApplication pba = new PhoneBookApplication();
		pba.setup();
		for (String s: args)
			pba.xmlScan(new File(s));
		pba.print();
		pba.xmlPrint();
	}

	public PhoneBookApplication() { // empty phonebook
		log.trace("Init");
		pb = PhoneBook.getInstance();
	}

	public void setup() throws PhoneBookException { // phonebook with debug data
		log.trace("Setup");
		Person person;

		if (!pb.getPersons().isEmpty()) return;

		// setup the initial state if phonebook is empty
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
	}

	public void print() {
		log.trace("Print");

		for (Person p: pb.getPersons()) {
			System.out.println("The Contact book of " + p.getName() + " contains " + p.getContacts().size() + " contacts :");
			for (Contact c: p.getContacts())
				System.out.println("\t" + c.getName() + " -> " + c.getPhoneNumber());
		}
	}

	public void xmlPrint() {
		log.trace("xmlPrint");
		Document doc = pb.xmlExport();
		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		try { xmlOutput.output(doc, new PrintStream(System.out));
		} catch (IOException e) { System.out.println(e); }
	}

	public void xmlScan(File file) throws PhoneBookException {
		log.trace("xmlScan");
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = (Document)builder.build(file);
			pb.xmlImport(document.getRootElement());
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}
}
