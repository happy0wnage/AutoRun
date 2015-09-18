package com.builder.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import com.DataBase.MySQL.MySqlDAO;
import com.builder.overlapView.JBackgroundPanel;
import com.builder.overlapView.ListEntryCellRenderer;
import com.builder.overlapView.Programs;
import com.builder.overlapView.SdoJFileChooser;

public class MainForm {

	private static MySqlDAO dao = new MySqlDAO();
	public static JTable table[] = new JTable[4];

	public static JScrollPane tableScroll[] = new JScrollPane[4];

	public static JPanel TablePanel;
	public static JList<Programs> list;
	public static JBackgroundPanel main;
	public static JScrollPane scrollPane;
	public static JScrollPane textScroll;
	public static JFrame frame;
	public static DefaultListModel<Programs> listModel;
	public static BufferedReader bfRead;
	public static BufferedWriter bfwrite;
	public static JButton RenameOK;
	public static JTextArea textArea;
	public static JTextField RenameText;
	public static JTextField findProgram;
	public static String value, path;
	public static ImageIcon imageIcon;
	public static String programName;
	public static JTabbedPane tabbedPane;

	/*public static String flashDetect() {
		String flash = null;
		long a = 15716286464l;
		for (File f : File.listRoots())
			if (f.canWrite() && f.getTotalSpace() == a)
				flash = f.getPath().substring(0, 1).toUpperCase();
		return flash;
	}*/

	/**
	 * Получение иконки файла по поти к файлу
	 * 
	 * @param path
	 * @return icon image
	 */
	public static Icon getIcon(String path) {
		return FileSystemView.getFileSystemView().getSystemIcon(new File(path));
	}

	public static int getDBIndexByListIndex(int index) {
		try {
			String name = listModel.get(index).getValue();
			return dao.getDBIndexByListIndex(name);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

	public static void choice() throws IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		SdoJFileChooser fileopen = new SdoJFileChooser();
		int ret = fileopen.showDialog(null, "Choice file");
		if (ret == JFileChooser.APPROVE_OPTION) {

			path = fileopen.getSelectedFile().getAbsolutePath();
			programName = fileopen.getSelectedFile().getName();
			int id = dao.getLastId() + 1;
			int dotPos = path.lastIndexOf(".") + 1;
			if (path.substring(dotPos).equals("exe")) {
				new DataBaseForm(true, id);
				frame.setEnabled(false);
				id++;

			} else
				JOptionPane.showMessageDialog(null, "Incorrect file extension");
		}
	}

	public static void install(int index) throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		try {
			Desktop.getDesktop().open(
					new File(dao.getListData(getDBIndexByListIndex(index))[1]));
		} catch (Exception e) {
			dao.deleteProgram(getDBIndexByListIndex(index));
			listModel.remove(index);
			textArea.setText(null);
			choice();
		}
	}

	public MainForm() {

		listModel = new DefaultListModel<Programs>();
		list = new JList<Programs>(listModel);
		list.setCellRenderer(new ListEntryCellRenderer());

			for (int i = 0; i < dao.reading().size(); i++) {
				listModel.addElement(dao.reading().get(i));
			}
			for (int i = 0; i < listModel.getSize(); i++) {
				int dbIndex = getDBIndexByListIndex(i);
				String value = dao.getListData(dbIndex)[0];
				String path = dao.getListData(dbIndex)[1];
				listModel.set(i, new Programs(value, path));
			}
		frame = new JFrame("APP Installer");
		URL iconURL = getClass().getResource("/image/icon.png");
		frame.setIconImage((new ImageIcon(iconURL)).getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			main = new JBackgroundPanel(ImageIO.read(getClass().getResource(
					"/image/main.jpg")));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		main.setPreferredSize(new Dimension(900, 600));
		main.setVisible(true);
		frame.getContentPane().add(main, BorderLayout.CENTER);
		frame.setResizable(false);

		list.setFixedCellHeight(25);
		list.setBorder(new EmptyBorder(5, 5, 5, 5));
		list.setOpaque(false);
		list.setFont(new Font("Nexa", Font.PLAIN, 14));
		list.setSelectionBackground(new Color(135, 206, 235, 150));
		list.setVisible(true);
		list.setBackground(new Color(246, 246, 246, 0));
		list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		scrollPane = new JScrollPane(list,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(15, 43, 229, 544);
		scrollPane.setBorder(null);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		main.setLayout(null);
		main.add(scrollPane);

		imageIcon = new ImageIcon(
				(getClass().getResource("/image/install.png")));
		final JButton installButton = new JButton(imageIcon);
		installButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/image/install_2.png")));

		installButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		installButton.setBorder(null);
		installButton.setBounds(788, 12, 99, 32); // //19, 39, 39
		installButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int index = list.getSelectedIndex();
					install(index);
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException | IOException
						| ArrayIndexOutOfBoundsException e1) {

					if (listModel.getSize() != 0) {
						JOptionPane.showMessageDialog(null,
								"Please select an item");
					} else {
						JOptionPane.showMessageDialog(null,
								"Please add program to the list");
					}

				}
			}
		});
		imageIcon = new ImageIcon((getClass().getResource("/image/View.png")));
		final JToggleButton viewButton = new JToggleButton(imageIcon);
		viewButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/image/View_2.png")));
		viewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		viewButton.setBorder(null);
		viewButton.setBounds(709, 12, 65, 32);

		viewButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				updateTable();
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					int index = list.getSelectedIndex();
					TablePanel.setVisible(true);
					for (int i = 0; i < 4; i++)
						table[i].getSelectionModel().setSelectionInterval(
								index, index);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED)
					TablePanel.setVisible(false);
			}
		});

		main.add(viewButton);
		main.add(installButton);

		final JPopupMenu popUp = new JPopupMenu();
		final JMenuItem Rename = new JMenuItem("Rename");
		final JMenuItem Delete = new JMenuItem("Delete");
		final JMenuItem PopupDescription = new JMenuItem("Change");
		popUp.add(Rename);
		popUp.add(Delete);
		popUp.add(PopupDescription);

		Rename.setBackground(new Color(67, 168, 198));
		Rename.setForeground(Color.WHITE);
		Delete.setBackground(new Color(135, 206, 235));
		Delete.setForeground(Color.WHITE);
		PopupDescription.setBackground(new Color(67, 168, 198));
		PopupDescription.setForeground(Color.WHITE);

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (SwingUtilities.isRightMouseButton(me)
						&& !list.isSelectionEmpty()
						&& list.locationToIndex(me.getPoint()) == list
								.getSelectedIndex())
					popUp.show(list, me.getX(), me.getY());
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!list.isSelectionEmpty()) {
					int index = list.getSelectedIndex();
					textArea.setText(dao
							.setDescription(getDBIndexByListIndex(index)));
					scrollPane.transferFocusUpCycle();
					for (int i = 0; i < 4; i++)
						table[i].getSelectionModel().setSelectionInterval(
								index, index);
				}
			}
		});

		textArea = new JTextArea();
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textArea.setFont(new Font("Arial", Font.PLAIN, 14));
		textArea.setOpaque(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);

		textScroll = new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textScroll.setBounds(259, 44, 628, 544); // 512
		textScroll.setBorder(null);
		textScroll.setOpaque(false);
		textScroll.getViewport().setOpaque(false);

		TablePanel = new JPanel();
		TablePanel.setVisible(false);
		TablePanel.setLayout(null);
		TablePanel.setBackground(new Color(67, 168, 198));
		TablePanel.setBounds(259, 44, 628, 544);
		main.add(TablePanel);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 628, 544);

		TablePanel.add(tabbedPane);

		for (int i = 0; i < 4; i++) {
			table[i] = new JTable();
			table[i].setEnabled(false);

			tableScroll[i] = new JScrollPane(table[i],
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tableScroll[i].setBorder(null);
			tableScroll[i].getViewport().setBackground(Color.WHITE);
		}

		updateTable();
		tabbedPane.add("Program", tableScroll[0]);
		tabbedPane.add("Developer", tableScroll[1]);
		tabbedPane.add("ProgrammingLanguage", tableScroll[2]);
		tabbedPane.add("Type", tableScroll[3]);

		main.add(textScroll);

		imageIcon = new ImageIcon(getClass().getResource("/image/add.png"));
		final JButton addButton = new JButton(imageIcon);
		addButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/image/add_2.png")));

		addButton.setOpaque(false);
		addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addButton.setBorder(null);
		addButton.setBounds(165, 12, 81, 33);
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					choice();
				} catch (IOException | InstantiationException
						| IllegalAccessException | ClassNotFoundException
						| SQLException e1) {

					e1.printStackTrace();
				}
			}
		});

		main.add(addButton);

		PopupDescription.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int index = getDBIndexByListIndex(list.getSelectedIndex());
					new DataBaseForm(false, index);
					frame.setEnabled(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		RenameText = new JTextField();
		RenameText.setMargin(new Insets(0, 10, 0, 10));
		RenameText.setOpaque(false);
		RenameText.setForeground(Color.WHITE);
		RenameText.setBounds(267, 17, 235, 23);
		main.add(RenameText);
		RenameText.setColumns(10);
		RenameText.setVisible(false);

		RenameOK = new JButton(new ImageIcon(getClass().getResource(
				"/image/Confirm.png")));
		RenameOK.setVisible(false);
		RenameOK.setBorder(null);
		RenameOK.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/image/Confirm_2.png")));
		RenameOK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		RenameOK.setOpaque(false);
		RenameOK.setBounds(516, 12, 33, 33);
		main.add(RenameOK);

		Rename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RenameText.setText(listModel.getElementAt(
						list.getSelectedIndex()).getValue());
				findProgram.setVisible(false);
				RenameText.setVisible(true);
				RenameOK.setVisible(true);
			}
		});

		RenameOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = list.getSelectedIndex();
				int dbIndex = getDBIndexByListIndex(i);
				String text = RenameText.getText();

				String fName = listModel.get(i).getValue();
				String sName = listModel.get(i).rename(text);

				RenameText.setText("");
				findProgram.setVisible(true);
				RenameText.setVisible(false);
				RenameOK.setVisible(false);

				dao.renameProgram(fName, sName);
				updateTable();
				updateList_Area(dbIndex);
			}
		});

		RenameText.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					int i = list.getSelectedIndex();
					int dbIndex = getDBIndexByListIndex(i);
					String text = RenameText.getText();

					String fName = listModel.get(i).getValue();
					String sName = listModel.get(i).rename(text);

					RenameText.setText("");
					findProgram.setVisible(true);
					RenameText.setVisible(false);
					RenameOK.setVisible(false);

					dao.renameProgram(fName, sName);
					updateTable();
					updateList_Area(dbIndex);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		findProgram = new JTextField();
		findProgram.setMargin(new Insets(0, 10, 0, 10));
		findProgram.setOpaque(false);
		findProgram.setForeground(Color.WHITE);
		findProgram.setBounds(267, 17, 235, 23);
		findProgram.setColumns(10);
		findProgram.setVisible(true);
		findProgram.setText("Please, enter search query...");
		main.add(findProgram);

		findProgram.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				findProgram.setText("Please, enter search query...");
				updateTable();
			}

			@Override
			public void focusGained(FocusEvent e) {
				findProgram.setText("");
				TablePanel.setVisible(true);
			}
		});

		findProgram.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				showOnSearch(findProgram.getText().toLowerCase());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				showOnSearch(findProgram.getText().toLowerCase());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				showOnSearch(findProgram.getText().toLowerCase());
			}

			private void showOnSearch(String readingLine) {
				for (int i = 0; i < 4; i++) {
					table[i].setModel(new DefaultTableModel(dao
							.showOnSearch(readingLine)[i][0], dao
							.showOnSearch(readingLine)[i][1]));
				}
			}
		});
		Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = list.getSelectedIndex();
				list.clearSelection();
				dao.deleteProgram(getDBIndexByListIndex(index));
				listModel.remove(index);
				textArea.setText(null);
				updateTable();
			}
		});

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setPreferredSize(new Dimension(900, 600));
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		new MainForm();
	}

	public static void updateTable() {
		for (int i = 0; i < 4; i++) {
			table[i].setModel(new DefaultTableModel(dao.updateTable()[i][0],
					dao.updateTable()[i][1]));
		}
	}

	public static void updateList_Area(int dbIndex) {
		try {
			String value = dao.getListData(dbIndex)[0];
			String path = dao.getListData(dbIndex)[1];

			int listIndex = list.getSelectedIndex();
			listModel.set(listIndex, new Programs(value, path));
			textArea.setText(dao.setDescription(dbIndex));

		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
}
