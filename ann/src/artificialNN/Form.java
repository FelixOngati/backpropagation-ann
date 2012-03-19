package artificialNN;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.lang.reflect.*;

public class Form extends JFrame {
	private static final long serialVersionUID = 1L;
	private UtilClass util;
	private JComboBox cbxDataset;
	private JTextField txtNumofHidUnits;
	private JTextField txtLearnRate;
	private JTextField txtMomentum;
	private JTextField txtNumofIter;
	private JTextField txtRandomSeed;
	private JTextField txtValidationSize;
	private JCheckBox chbPrintLearningProcess;

	/**
	 * constructor
	 * 
	 * @param util
	 */
	public Form(UtilClass util) {
		this.util = util;
		createMenu();
		createWidgets();

		this.pack();
		this.setSize(new Dimension(200, 410));
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * a general listener which uses reflections to call the proper method from
	 * UtilClass
	 * 
	 * @author khademzadeh
	 * 
	 */
	private class myListener implements ActionListener {
		String action;
		String methodName;

		public myListener(String action) {
			this.action = action;
			this.methodName = toMethodName();
		}

		public String toMethodName() {
			String str = action.replace(" ", "");
			str = (char) (str.charAt(0) + 32) + str.substring(1);
			return str;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Method method = util.getClass().getMethod(methodName, null);
				method.invoke(util, null);
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e2) {
				e2.printStackTrace();
			} catch (IllegalAccessException e3) {
				e3.printStackTrace();
			} catch (InvocationTargetException e4) {
				e4.printStackTrace();
			}
		}
	}

	/**
	 * set the proper parameters for current selected dataset
	 */
	private class CbxDatasetListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox source = (JComboBox) e.getSource();
			String ds = source.getSelectedItem().toString();
			System.out.println("Setting parameters for " + ds + " dataset.");
			if (ds == "tennis" || ds == "iris") {
				txtNumofHidUnits.setText("2");
				txtNumofIter.setText("450");
			} else if (ds == "identity") {
				txtNumofHidUnits.setText("3");
				txtNumofIter.setText("1700");
			}
		}
	}

	/**
	 * set the parameters for testing noisy iris
	 */
	public void setParams4TestNoisyIris() {
		cbxDataset.setSelectedIndex(0);
		txtNumofHidUnits.setText("2");
		txtNumofIter.setText("3000");
	}

	/**
	 * creates a menu item and its listener
	 * 
	 * @param text
	 * @return
	 */
	private JMenuItem createMenuItem(String text) {
		JMenuItem item = new JMenuItem(text);
		item.addActionListener(new myListener(text));
		return item;
	}

	/**
	 * creates the menu
	 */
	private void createMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createMenuItem("Read Data"));
		menu.add(createMenuItem("Print Data"));
		menu.add(createMenuItem("Learn ANN"));
		menu.add(createMenuItem("Test ANN"));
		menu.add(createMenuItem("Test ANN on Noisy Iris"));
		menu.add(createMenuItem("Quit"));

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	/**
	 * creates a pair of label and textfield for input values
	 * 
	 * @param panel
	 * @param lblText
	 * @param txtText
	 * @return
	 */
	private JTextField addLabelAndText(JPanel panel, String lblText,
			String txtText) {
		JLabel lbl = new JLabel(lblText);
		JTextField txt = new JTextField(txtText, 10);
		panel.add(lbl);
		panel.add(txt);
		return txt;
	}

	/**
	 * creates all the widgets in the form
	 */
	private void createWidgets() {
		JPanel panel = new JPanel();

		JLabel lblDataset = new JLabel("Dataset:");
		String[] items = { "iris", "tennis", "identity", "xor" };
		cbxDataset = new JComboBox(items);
		cbxDataset.addActionListener(new CbxDatasetListener());
		panel.add(lblDataset);
		panel.add(cbxDataset);

		txtNumofHidUnits = addLabelAndText(panel, "Num of Hidden Units:", "2");
		txtLearnRate = addLabelAndText(panel, "Learning Rate:", "0.25");
		txtMomentum = addLabelAndText(panel, "Momentum:", "0.1");
		txtNumofIter = addLabelAndText(panel, "Num of Iteration:", "450");
		txtRandomSeed = addLabelAndText(panel, "Random Seed:", "654321");
		txtValidationSize = addLabelAndText(panel, "Validation Percent:", "20");

		chbPrintLearningProcess = new JCheckBox("Print Learning Process");
		panel.add(chbPrintLearningProcess);
		this.add(panel);
	}

	/**
	 * creates a param object from the form values
	 * 
	 * @return
	 */
	public Params getParams() {
		return new Params((String) cbxDataset.getSelectedItem(),
				txtNumofHidUnits.getText(), txtLearnRate.getText(),
				txtMomentum.getText(), txtNumofIter.getText(),
				txtRandomSeed.getText(), chbPrintLearningProcess.isSelected(),
				txtValidationSize.getText());
	}
}
