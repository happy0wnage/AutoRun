package com.builder.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.DataBase.MySQL.MySqlDAO;
import com.builder.overlapView.JBackgroundPanel;
import com.builder.overlapView.LimitedDocument;
import com.builder.overlapView.Programs;

public class DataBaseForm {

	private MySqlDAO dao = new MySqlDAO();
	private static JFrame DB;
	private static JPanel dataPanel;

	private JButton submitButton;
	private JButton previousButton;
	private JButton nextButton;

	private static JTextArea descField;
	private static JScrollPane dataScroll;

	private static JTextField[] attributes = new JTextField[13];
	private static JLabel[] labels = new JLabel[13];

	private static String[] fields = { "Name", "Release", "Website",
			"Company name", "Foundation year", "Country",
			"Number of employees", "Industry", "Lang name", "Category",
			"Type of execution", "Type name", "Speciality" };

	public DataBaseForm() {
		super();
	}

	/**
	 * Сделать все элементы видимыми/невидимыми
	 * 
	 * @param n
	 */
	public void visibleAllElements(boolean n) {
		for (int i = 0; i < 13; i++) {
			labels[i].setVisible(n);
			attributes[i].setVisible(n);
		}
	}

	/**
	 * Сделать элементы видимыми с выбранного начала до выбранного конца
	 * 
	 * @param n
	 */
	public void visible(int start, int finish) {
		visibleAllElements(false);
		for (int i = start; i <= finish; i++) {
			labels[i].setVisible(true);
			attributes[i].setVisible(true);
		}
	}

	/**
	 * Проверка тексового бокса на факт присутствия данных
	 * 
	 * @return
	 */

	public boolean isFilled() {
		for (int i = 0; i < 13; i++) {
			if (attributes[i].getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Field "
						+ labels[i].getText().toString() + " is not filled");
				return false;
			}
		}
		return true;
	}

	public DataBaseForm(final boolean selectForm, final int dbIndex)
			throws IOException {

		DB = new JFrame("Database");
		DB.setIconImage((new ImageIcon(getClass()
				.getResource("/image/icon.png"))).getImage());
		DB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		DB.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				MainForm.frame.setEnabled(true);
				MainForm.frame.setAlwaysOnTop(true);
				MainForm.frame.setAlwaysOnTop(false);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

		DB.setResizable(false);
		JBackgroundPanel dbMain = new JBackgroundPanel(ImageIO.read(getClass()
				.getResource("/image/DB.jpg")));
		dbMain.setPreferredSize(new Dimension(318, 600));
		DB.getContentPane().add(dbMain, BorderLayout.NORTH);
		dbMain.setLayout(null);

		dataPanel = new JPanel();
		dataPanel.setBounds(new Rectangle(28, 58, 262, 303));
		dataPanel.setOpaque(false);
		dbMain.add(dataPanel);
		dataPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel description = new JPanel();
		description.setBounds(28, 376, 262, 166);
		dbMain.add(description);
		description.setLayout(null);
		description.setOpaque(false);

		JLabel Description = new JLabel("Description");
		Description.setAlignmentX(Component.CENTER_ALIGNMENT);
		Description.setBounds(72, 0, 117, 29);
		Description.setForeground(new Color(108, 132, 159));
		Description.setFont(new Font("a_Concepto", Font.PLAIN, 18));

		/*
		 * Текстовое поле для заполнения краткого описания к программе
		 */
		descField = new JTextArea();
		descField.setMargin(new Insets(10, 10, 10, 10));
		descField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		descField.setFont(new Font("Arial", Font.PLAIN, 14));
		descField.setLineWrap(true);
		descField.setWrapStyleWord(true);
		descField.setBackground(new Color(236, 242, 249));

		dataScroll = new JScrollPane(descField,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dataScroll.setBounds(15, 30, 232, 121);

		description.add(Description);
		description.add(dataScroll);

		/*
		 * Кнопка "Отправить данные"
		 */
		submitButton = new JButton(new ImageIcon(getClass().getResource(
				"/image/submit.png")));
		submitButton.setBorder(null);
		submitButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/image/submit_2.png")));
		submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		submitButton.setBounds(15, 555, 288, 33);
		dbMain.add(submitButton);

		/*
		 * Кнопка для просмотра предыдущей сущности
		 */
		previousButton = new JButton(new ImageIcon(getClass().getResource(
				"/image/prev.png")));
		previousButton.setBorder(null);
		previousButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/image/prev_2.png")));
		previousButton
				.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		previousButton.setBounds(15, 12, 144, 33);
		dbMain.add(previousButton);

		/*
		 * Кнопка для просмотра следующей сущности
		 */
		nextButton = new JButton();
		nextButton = new JButton(new ImageIcon(getClass().getResource(
				"/image/next.png")));
		nextButton.setBorder(null);
		nextButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/image/next_2.png")));
		nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nextButton.setBounds(159, 12, 144, 33);
		dbMain.add(nextButton);

		/*
		 * Создание полей заполенения
		 */

		attributes = new JTextField[13];
		labels = new JLabel[13];
		final int size = labels.length;

		for (int i = 0; i < size; i++) {
			labels[i] = new JLabel(fields[i]);
			labels[i].setForeground(new Color(108, 132, 159));
			labels[i].setFont(new Font("a_Concepto", Font.PLAIN, 18));

			attributes[i] = new JTextField(22);
			attributes[i].setDocument(new LimitedDocument(45));
			attributes[i].setBackground(new Color(236, 242, 249));
			attributes[i].setFont(new Font("Arial", Font.PLAIN, 14));

			dataPanel.add(labels[i]);
			dataPanel.add(attributes[i]);
		}
		visibleAllElements(false);

		for (int i = 0; i < 3; i++) {
			labels[i].setVisible(true);
			attributes[i].setVisible(true);
		}

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (attributes[0].isVisible()) {
					visible(3, 7);
				} else if (attributes[3].isVisible()) {
					visible(8, 10);
				} else if (attributes[8].isVisible()) {
					visible(11, 12);
				} else if (attributes[11].isVisible()) {
					visible(0, 2);
				}

			}
		});

		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (attributes[0].isVisible()) {
					visible(11, 12);
				} else if (attributes[3].isVisible()) {
					visible(0, 2);
				} else if (attributes[8].isVisible()) {
					visible(3, 7);
				} else if (attributes[11].isVisible()) {
					visible(8, 10);
				}

			}
		});

		if (!selectForm) {
			for (int i = 0; i < 13; i++) {
				attributes[i].setText(dao.fieldsView(dbIndex)[i]);
			}
			descField.setText(dao.fieldsView(dbIndex)[13]);
		} else {
			attributes[0].setText(MainForm.programName.replaceAll(".exe", ""));
		}

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isFilled()) {

					String allFields[] = new String[15];
					for (int i = 0; i < 13; i++) {
						allFields[i] = attributes[i].getText().toString();
					}
					allFields[13] = descField.getText().toString();

					if (selectForm) {
						allFields[14] = MainForm.path.replace("\\", "\\\\");
						if (dao.insertProgram(allFields, dbIndex)) {
							MainForm.listModel.addElement(new Programs(
									allFields[0], allFields[14]));
							MainForm.updateTable();
							JOptionPane.showMessageDialog(null,
									"Data has been sent");
							DB.dispose();
							MainForm.frame.setEnabled(true);
							MainForm.frame.setAlwaysOnTop(true);
							MainForm.frame.setAlwaysOnTop(false);
						} else {
							JOptionPane.showMessageDialog(null,
									"Couldn't load fata", "Error",
									JOptionPane.ERROR_MESSAGE);
						}

					} else {
						if (dao.changeProgram(allFields, dbIndex)) {
							MainForm.updateList_Area(dbIndex);
							MainForm.updateTable();
							JOptionPane.showMessageDialog(null,
									"Data has been updated");
							DB.dispose();
							MainForm.frame.setEnabled(true);
							MainForm.frame.setAlwaysOnTop(true);
							MainForm.frame.setAlwaysOnTop(false);
						} else {
							JOptionPane.showMessageDialog(null,
									"Couldn't load fata", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

		});

		DB.pack();
		DB.setLocationRelativeTo(null);
		DB.setPreferredSize(new Dimension(318, 600));
		DB.setVisible(true);
	}

}
