package pt.tecnico.phonebook;

import pt.tecnico.phonebook.PhoneBookApplication;
import pt.tecnico.phonebook.domain.PhoneBook;
import pt.tecnico.phonebook.domain.Person;
import pt.tecnico.phonebook.exception.PhoneBookException;
import pt.tecnico.phonebook.controller.AtomicInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.core.WriteOnReadError;


public abstract class AbstractTest {
	protected static final Logger log = LoggerFactory.getLogger(AbstractTest.class);

	@BeforeClass // run once before each test class
	public static void setUpBeforeAll() throws Exception {
		// run tests with a clean database!!!
		AtomicInterface.delete(); // requires a transaction
	}

	@Before // run before each test
	public void setUp() throws Exception {
		try {
			FenixFramework.getTransactionManager().begin(false);
			populate();
		} catch (WriteOnReadError | NotSupportedException | SystemException e) {
			e.printStackTrace();
		}
	}

	@After // rollback after each test
	public void tearDown() {
		try {
			FenixFramework.getTransactionManager().rollback();
		} catch (IllegalStateException | SecurityException | SystemException e) {
			e.printStackTrace();
		}
	}

	// may be redefined on each test class
	protected abstract void populate() throws PhoneBookException;
}
