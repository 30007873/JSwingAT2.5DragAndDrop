package dragAndDrop;

import java.util.logging.Logger;

import javax.swing.TransferHandler;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TaskHandlerTest extends TransferHandler {
	
	private static final Logger log = Logger.getLogger(TaskHandlerTest.class.getName());

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setup() {
		log.info("Setting up...");

	}

	@After
	public void tearDown() {
		log.info("Tearing down...");

	}

	@BeforeClass
	public static void before() {
		log.info("Running JUnit test cases from class: " + TaskHandlerTest.class);

	}

	@AfterClass
	public static void after() {
		log.info("Testing class " + TaskHandlerTest.class + " has completed.");

	}

	public void reset() {
		tearDown();
		setup();
	}
	
	@Test
	public void canImportTest() {
		// requires mockito/powermock
		reset();
	}

	@Test
	public void createTransferableTest() {
		// requires mockito/powermock
		reset();
	}

	@Test
	public void getSourceActionsTest() {
		// requires mockito/powermock
		reset();
	}

	@Test
	public void importDataTest() {
		// requires mockito/powermock
		reset();
	}

	@Test
	public void exportDoneTest() {
		// requires mockito/powermock
		reset();
	}
}