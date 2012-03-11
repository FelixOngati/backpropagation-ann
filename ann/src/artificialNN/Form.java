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

	public Form(UtilClass util) {
		this.util = util;
		createMenu();
		createWidgets();

		this.pack();
		this.setSize(new Dimension(200, 430));
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

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

	private JMenuItem createMenuItem(String text) {
		JMenuItem item = new JMenuItem(text);
		item.addActionListener(new myListener(text));
		return item;
	}

	private void createMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createMenuItem("Read Data"));
		menu.add(createMenuItem("Print Data"));
		menu.add(createMenuItem("Learn ANN"));
		menu.add(createMenuItem("Test ANN"));
		menu.add(createMenuItem("Quit"));

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	private JTextField addLabelAndText(JPanel panel, String lblText,
			String txtText) {
		JLabel lbl = new JLabel(lblText);
		JTextField txt = new JTextField(txtText, 10);
		panel.add(lbl);
		panel.add(txt);
		return txt;
	}

	private void createWidgets() {
		JPanel panel = new JPanel();

		JLabel lblDataset = new JLabel("Dataset:");
		String[] items = { "test2", "test", "tennis", "identity", "iris" };
		cbxDataset = new JComboBox(items);
		panel.add(lblDataset);
		panel.add(cbxDataset);

		txtNumofHidUnits = addLabelAndText(panel, "Num of Hidden Units:", "2");
		txtLearnRate = addLabelAndText(panel, "Learning Rate:", "0.3");
		txtMomentum = addLabelAndText(panel, "Momentum:", "0.3");
		txtNumofIter = addLabelAndText(panel, "Num of Iteration:", "10");
		txtRandomSeed = addLabelAndText(panel, "Random Seed:", "1234");
		this.add(panel);
	}

	public Params getParams() {
		return new Params((String) cbxDataset.getSelectedItem(),
				txtNumofHidUnits.getText(), txtLearnRate.getText(),
				txtMomentum.getText(), txtNumofIter.getText(),
				txtRandomSeed.getText());
	}
}
