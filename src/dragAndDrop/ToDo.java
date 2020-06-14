package dragAndDrop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

public class ToDo extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8812415041397143670L;
	
	public JComboBox<String> jComboBox;
	public JList<String> jList;
	public JFrame jFrame;

	public ToDo() {
		super(new GridLayout(2, 1));
		add(createArea());
		add(createList());
	}

	public JPanel createList() {
		DefaultListModel<String> defaultListModel = new DefaultListModel<String>();

		defaultListModel.addElement("TEST");

		jList = new JList<String>(defaultListModel);
		jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane jScrollPane = new JScrollPane(jList);
		jScrollPane.setPreferredSize(new Dimension(800, 200));

		jList.setDragEnabled(true);
		jList.setTransferHandler(new TaskHandler());
 
		jComboBox = new JComboBox<String>(new String[] { "DRAG AND DROP", "INITIALIZE" });
		jComboBox.addActionListener(this);
		
		JPanel jPanel1 = new JPanel();
		jPanel1.add(new JLabel("List Drop Mode: "));
		jPanel1.add(jComboBox);

		JPanel jPanel2 = new JPanel(new BorderLayout());
		jPanel2.add(jScrollPane, BorderLayout.CENTER);
		jPanel2.add(jPanel1, BorderLayout.SOUTH);
		jPanel2.setBorder(BorderFactory.createTitledBorder("Pending Tasks"));

		return jPanel2;
	}

	public JPanel createArea() {
		JTextArea jTextArea = new JTextArea();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("INSTRUCTIONS: ");
		stringBuilder.append("\nCTRL + Drag and drop = COPY");
		stringBuilder.append("\nDrag and drop = MOVE");
		stringBuilder.append("\n");
		stringBuilder.append("\nMODE: INITIALIZE to drag to pending tasks");
		stringBuilder.append("\nMODE: DRAG AND DROP to modify task.");
		jTextArea.setText(stringBuilder.toString());
		jTextArea.setDragEnabled(true);
		
		JScrollPane jScrollPane = new JScrollPane(jTextArea);
		jScrollPane.setPreferredSize(new Dimension(800, 200));
		
		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(jScrollPane, BorderLayout.CENTER);
		jPanel.setBorder(BorderFactory.createTitledBorder("Add Task"));
		
		return jPanel;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		Object object = jComboBox.getSelectedItem();
		if (object == "DRAG AND DROP") {
			jList.setDropMode(DropMode.USE_SELECTION);
		} else if (object == "INITIALIZE") {
			jList.setDropMode(DropMode.ON_OR_INSERT);
		}
	}

	public void display() {
		jFrame = new JFrame("Drag & Drop - To Do");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JComponent jComponent = new ToDo();
		jComponent.setOpaque(true);
		jFrame.setContentPane(jComponent);
		
		jFrame.pack();
		jFrame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				ToDo toDo = new ToDo();
				toDo.display();
			}
		});
	}
}