package pt.tecnico.phonebook.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.phonebook.exception.PhoneBookException;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.io.StringReader;

public class ImportTest extends AbstractTest {
	private static final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		+ "<phonebook>"
		+ "  <person name=\"Maria\">"
		+ "	<contacts>"
		+ "	  <contact name=\"Xana\" phoneNumber=\"963456789\" />"
		+ "	  <contact name=\"Manel\" phoneNumber=\"333333333\" />"
		+ "	  <contact name=\"IST\" phoneNumber=\"214315112\" />"
		+ "	  <contact name=\"SOS\" phoneNumber=\"112\" />"
		+ "	</contacts>"
		+ "  </person>"
		+ "  <person name=\"Manel\">"
		+ "	<contacts>"
		+ "	  <contact name=\"Xico\" phoneNumber=\"911919191\" />"
		+ "	  <contact name=\"IST\" phoneNumber=\"214315112\" />"
		+ "	  <contact name=\"SOS\" phoneNumber=\"112\" />"
		+ "	</contacts>"
		+ "  </person>"
		+ "</phonebook>";

	protected void populate() throws PhoneBookException {
		pb = PhoneBook.getInstance();
		pb.cleanup();
	}

	@Test
	public void success() throws Exception {
		Document doc = new SAXBuilder().build(new StringReader(xml));
		pb.xmlImport(doc.getRootElement());

		// check imported data
		assertEquals("created 2 Persons", 2, pb.getPersonSet().size());
		assertTrue("created Maria", pb.hasPerson("Maria"));
		assertTrue("created Manel", pb.hasPerson("Manel"));
		assertEquals("Maria has 4 Contacts", 4, pb.getPersonByName("Maria").getContactSet().size());
		assertEquals("Manel has 3 Contacts", 3, pb.getPersonByName("Manel").getContactSet().size());
		// it must be right, but more checks can be made ...
	}
}
