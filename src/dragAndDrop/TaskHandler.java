package dragAndDrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class TaskHandler extends TransferHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9016045905555917417L;

	private static final Logger log = Logger.getLogger(TaskHandler.class.getName());

	// Array of integer for indices
	private int[] indices = null;
	// Location where items were added
	private int addIndex = -1; 
	// Number of items added.
	private int addCount = 0;

	/**
	 * This method is used to validate the data flavor of transfer data
	 *
	 * @param transferData
	 * @return
	 */
	public boolean canImport(TransferHandler.TransferSupport transferData) {
		if (!transferData.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			return false;
		}
		return true;
	}

	/**
	 * This method is used to create transferable for the transfer data
	 *
	 * @param jComponent
	 * @return
	 */
	protected Transferable createTransferable(JComponent jComponent) {
		JList<String> jList = (JList<String>) jComponent;
		this.indices = jList.getSelectedIndices();
		List<String> list = jList.getSelectedValuesList();

		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			stringBuffer.append(object == null ? "" : object.toString());
			if (i != list.size() - 1) {
				stringBuffer.append("\n");
			}
		}

		return new StringSelection(stringBuffer.toString());
	}

	/**
	 * This method is used to get the action from the source
	 *
	 * @param jComponent
	 * @return
	 */
	public int getSourceActions(JComponent jComponent) {
		return TransferHandler.COPY_OR_MOVE;
	}

	/**
	 * This method is used to import the transfer data
	 *
	 * @param transferData
	 * @return
	 */
	public boolean importData(TransferHandler.TransferSupport transferData) {
		if (!transferData.isDrop()) {
			return false;
		}

		JList<String> list = (JList<String>) transferData.getComponent();
		DefaultListModel<String> defaultListModel = (DefaultListModel<String>) list.getModel();
		JList.DropLocation dropLocation = (JList.DropLocation) transferData.getDropLocation();
		int index = dropLocation.getIndex();
		boolean isInsert = dropLocation.isInsert();
		// Get the drag and drop data
		Transferable transferable = transferData.getTransferable();
		String data = null;
		try {
			data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}

		String[] values = data.split("\n");

		this.addIndex = index;
		this.addCount = values.length;

		for (int i = 0; i < values.length; i++) {
			if (isInsert) {
				defaultListModel.add(index++, values[i]);
			} else {
				if (index < defaultListModel.getSize()) {
					defaultListModel.set(index++, values[i]);
				} else {
					defaultListModel.add(index++, values[i]);
				}
			}
		}
		return true;
	}

	/**
	 * This method is used to export the transfer data
	 *
	 * @param jComponent
	 * @param transferableData
	 * @param action
	 */
	protected void exportDone(JComponent jComponent, Transferable transferableData, int action) {
		JList<String> jList = (JList<String>) jComponent;
		DefaultListModel<String> defaultListModel = (DefaultListModel<String>) jList.getModel();

		if (action == TransferHandler.MOVE) {
			for (int i = indices.length - 1; i >= 0; i--) {
				defaultListModel.remove(indices[i]);
			}
		}

		this.indices = null;
		this.addCount = 0;
		this.addIndex = -1;
	}
}