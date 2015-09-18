//package com.forms;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//
//
//public class SendData {
//
//	private String Name, Web_site, Release, Company, Country, Employees,
//	Industry, LName, Category, TypeE, Type, Specialty, Description,
//	Path;
//	private int Foundation_year;
//	private JTextArea descriptionA;
//	
//	public JTextField[] nameData = new JTextField[13];
//	public JLabel[] textData = new JLabel[13];
//	
//		public SendData() {
//			Name = nameData[0].getText().toString();
//			Release = nameData[1].getText().toString();
//			Web_site = nameData[2].getText().toString();
//			Company = nameData[3].getText().toString();
//			Foundation_year = Integer.parseInt(nameData[4].getText().toString());
//			Country = nameData[5].getText().toString();
//			Employees = nameData[6].getText().toString();
//			Industry = nameData[7].getText().toString();
//			LName = nameData[8].getText().toString();
//			Category = nameData[9].getText().toString();
//			TypeE = nameData[10].getText().toString();
//			Type = nameData[11].getText().toString();
//			Specialty = nameData[12].getText().toString();
//			Description = descriptionA.getText().toString();
//	}
//
//
//public boolean isValidFormat(String format, String value) {
//	Date date = null;
//	try {
//		date = new SimpleDateFormat(format).parse(value);
//	} catch (ParseException ex) {
//		JOptionPane.showMessageDialog(null, "Incorrect date format");
//	}
//	return date != null;
//}
//
//public void sendData(JTextArea description, JTextField nameData[], int index) {
//	
//	Path = form.path.replace("\\", "\\\\");
//
//	String[] query = {
//			"INSERT INTO developer VALUES (" + index + ",'" + Company
//					+ "', " + Foundation_year + " ,'" + Country + "','"
//					+ Employees + "','" + Industry + "')",
//			"INSERT INTO programminglanguage VALUES (" + index + ",'"
//					+ LName + "','" + Category + "','" + TypeE + "')",
//			"INSERT INTO type  VALUES (" + index + ",'" + Type + "','"
//					+ Specialty + "')",
//			"INSERT INTO program VALUES (" + index + "," + index + ","
//					+ index + "," + index + ",'" + Name + "', ? ,'"
//					+ Web_site + "','" + Description + "','" + Path + "')" };
//
//	if (isValidFormat("yyyy-MM-dd", Release)) {
//		try {
//			for (int i = 0; i < 4; i++) {
//				pst = con.prepareStatement(query[i]);
//				if (i == 3) {
//					pst.setDate(1, java.sql.Date.valueOf(Release));
//				}
//				pst.executeUpdate();
//				JOptionPane.showMessageDialog(null,
//						"The data has been sent");
//				form.addElementToList(Name, Path);
//				form.updateTable();
//				DB.dispose();
//			}
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, e);
//		}
//	}
//
//}
//
//public void changeData(JTextArea description, JTextField nameData[],
//		int index) {
//
//	Name = nameData[0].getText().toString();
//	Release = nameData[1].getText().toString();
//	Web_site = nameData[2].getText().toString();
//	Company = nameData[3].getText().toString();
//	Foundation_year = Integer.parseInt(nameData[4].getText().toString());
//	Country = nameData[5].getText().toString();
//	Employees = nameData[6].getText().toString();
//	Industry = nameData[7].getText().toString();
//	LName = nameData[8].getText().toString();
//	Category = nameData[9].getText().toString();
//	TypeE = nameData[10].getText().toString();
//	Type = nameData[11].getText().toString();
//	Specialty = nameData[12].getText().toString();
//	Description = descriptionA.getText().toString();
//
//	String[] query = {
//			"UPDATE program SET Name = '" + Name
//					+ "', `Release`=?, `Web-site`='" + Web_site
//					+ "', Description='" + Description
//					+ "'  where idProgram=" + index,
//			"UPDATE developer SET `Company name` = '" + Company
//					+ "', `Foundation year` = " + Foundation_year
//					+ ",`Country` = '" + Country
//					+ "',`Number of employees` = '" + Employees
//					+ "',`Industry` = '" + Industry
//					+ "' where idDeveloper=" + index,
//			"UPDATE programminglanguage SET name = '" + LName
//					+ "', `Category`='" + Category
//					+ "', `Type of execution`='" + TypeE
//					+ "'  where idProgramminglanguage=" + index,
//			"UPDATE type SET `Type name` = '" + Type + "', `Specialty`='"
//					+ Specialty + "'  where idType=" + index };
//
//	if (isValidFormat("yyyy-MM-dd", Release)) {
//		try {
//			for (int i = 0; i < 4; i++) {
//				pst = con.prepareStatement(query[i]);
//				if (i == 0) {
//					pst.setDate(1, java.sql.Date.valueOf(Release));
//				}
//				pst.executeUpdate();
//				form.updateAll(index);
//				DB.dispose();
//			}
//			JOptionPane
//					.showMessageDialog(null, "DataBase has been updated");
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, e);
//		}
//	}
//}
// }