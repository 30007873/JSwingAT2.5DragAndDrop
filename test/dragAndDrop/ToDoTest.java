package dragAndDrop;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import javax.swing.DropMode;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ToDoTest extends JPanel {
	
	private static final Logger log = Logger.getLogger(ToDoTest.class.getName());

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
		log.info("Running JUnit test cases from class: " + ToDoTest.class);

	}

	@AfterClass
	public static void after() {
		log.info("Testing class " + ToDoTest.class + " has completed.");

	}

	public void reset() {
		tearDown();
		setup();
	}

	@Test
	public void createListTest() {
		ToDo toDo = new ToDo();
		JPanel jPanel = toDo.createList();
		JComboBox<String> jComboBox = toDo.jComboBox;
		JList<String> jList = toDo.jList;
		// Asserts
		assertTrue(jPanel.getLayout() instanceof BorderLayout);
		// for scroll pane
		assertTrue(jPanel.getComponent(0) instanceof JScrollPane);
		assertEquals(jPanel.getComponent(0).getPreferredSize().width, 800);
		assertEquals(jPanel.getComponent(0).getPreferredSize().height, 200);
		// for drop panel
		assertTrue(jPanel.getComponent(1) instanceof JPanel);
		// drop combo
		assertTrue(jComboBox.getItemAt(0).equals("DRAG AND DROP"));
		assertTrue(jComboBox.getItemAt(1).equals("INITIALIZE"));
		assertTrue(jComboBox.getActionListeners()[0] instanceof ToDo);
		// for list
		assertTrue(jList instanceof JList);
		assertEquals(jList.getSelectionMode(), ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		assertTrue(jList.getDragEnabled());
		assertTrue(jList.getTransferHandler() instanceof TaskHandler);
		
		reset();
	}

	@Test
	public void createAreaTest() {
		ToDo toDo = new ToDo();
		JPanel jPanel = toDo.createArea();
		
		// Asserts
		assertTrue(jPanel.getBorder() instanceof TitledBorder);
		// for scroll pane
		assertTrue(jPanel.getComponent(0) instanceof JScrollPane);
		assertEquals(jPanel.getComponent(0).getPreferredSize().width, 800);
		assertEquals(jPanel.getComponent(0).getPreferredSize().height, 200);
		
		reset();
	}

	@Test
	public void actionPerformedTest() {
		ToDo toDo = new ToDo();
		
		JList<String> jList = toDo.jList;
		JComboBox<String> jComboBox = toDo.jComboBox;
		
		Object object = jComboBox.getSelectedItem();
		
		if (object == "DRAG AND DROP") {
		assertEquals(jList.getDropMode(), DropMode.USE_SELECTION);
		} else if (object == "INITIALIZE") {
			assertEquals(jList.getDropMode(), DropMode.ON_OR_INSERT);
		}		
		
		ActionEvent actionEvent = new ActionEvent(jComboBox, 1001, "comboBoxChanged", 16);
		toDo.actionPerformed(actionEvent);
		// Assert statement
		assertTrue(toDo.jComboBox.getSelectedItem() != null);
		reset();
	}

	@Test
	public void displayTest() {
		ToDo toDo = new ToDo();
		toDo.display();
		JFrame jFrame = toDo.jFrame;
		
		// Asserts
		assertTrue(jFrame.getTitle().equals("Drag & Drop - To Do"));
		assertEquals(jFrame.getDefaultCloseOperation(), JFrame.EXIT_ON_CLOSE);
		assertTrue(jFrame.isOpaque());
		assertTrue(jFrame.getContentPane() instanceof ToDo);
		assertTrue(jFrame.isVisible());
		
		reset();
	}
}