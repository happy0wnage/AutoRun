package com.DataBase.MySQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.builder.overlapView.Programs;

/**
 * Набор методов, позволяющих работать с SQL запросами
 * 
 * @author Vladislav
 */
public class MySqlDAO implements AbstractDAO {

	public static Connection con = MySqlConnection.getConnection();
	private static ResultSet rs = null;
	private static PreparedStatement pst = null;
	private static CallableStatement cstm = null;
	private Vector<?>[][] vec;
	
	private final String INSERT_DEVELOPER = "INSERT INTO developer VALUES (?,?,?,?,?,?);";
	private final String INSERT_LANGUAGE = "INSERT INTO programminglanguage VALUES (?,?,?,?);";
	private final String INSERT_TYPE = "INSERT INTO type VALUES (?,?,?);";
	private final String INSERT_PROGRAM = "INSERT INTO program VALUES (?,?,?,?,?,?,?,?,?);";

	private final String CHANGE_PROGRAM = "UPDATE program SET Name=?, `Release`= ?, `Web-site`= ?, Description= ?  where idProgram=?;";
	private final String CHANGE_DEVELOPER = "UPDATE developer SET `Company name` = ?, `Foundation year` = ?, Country = ?, `Number of employees` = ?, Industry = ? where idDeveloper = ?;";
	private final String CHANGE_LANGUAGE = "UPDATE programminglanguage SET name = ?, `Category`=?, `Type of execution`= ? where idProgramminglanguage= ?;";
	private final String CHANGE_TYPE = "UPDATE type SET `Type name` = ?, `Specialty`=?  where idType=?;";

	private final String DELETE_PROGRAM = "DELETE FROM program WHERE idProgram=?";
	private final String DELETE_DEVELOPER = "DELETE FROM developer WHERE idDeveloper=?";
	private final String DELETE_LANGUAGE = "DELETE FROM programminglanguage WHERE idProgrammingLanguage=?";
	private final String DELETE_TYPE = "DELETE FROM type WHERE idType=?";

	private final String SELECT_PROGRAM_IF = "SELECT Name,`Release`,`Web-site` FROM program WHERE idProgram = ?";
	private final String SELECT_DEVELOPER_IF = "SELECT `Company name`, `Foundation year`, Country, `Number of employees`,Industry FROM developer WHERE idDeveloper=?";
	private final String SELECT_LANGUAGE_IF = "SELECT Name, Category, `Type of execution` FROM programminglanguage WHERE idProgrammingLanguage=?";
	private final String SELECT_TYPE_IF = "SELECT `Type name`, Specialty FROM type WHERE idType=?";
	private final String SELECT_DESCRIPTION_IF = "SELECT `Description` FROM program WHERE idProgram=?";

	private final String SELECT_PROGRAM = "Select Name,`Release`,`Web-site` from program";
	private final String SELECT_DEVELOPER = "Select `Company name`,`Foundation year`,`Country`,`Number of employees`,`Industry` from developer";
	private final String SELECT_LANGUAGE = "Select `Name`,`Category`,`Type of execution` from programminglanguage";
	private final String SELECT_TYPE = "Select `Type name`,`Specialty` from type";
	
	private final String SELECT_ID = "SELECT idProgram FROM program WHERE name=?;";
	private final String SELECT_NAME_PATH = "SELECT NAME,PATH FROM Program";
	private final String SELECT_NAME_PATH_IF = "SELECT NAME,PATH FROM Program WHERE idProgram=?";
	private final String RENAME = "UPDATE program SET name = ? where Name= ?;";
	private final String SELECT_COUNT = "SELECT COUNT(*) FROM program;";
	private final String SELECT_MAX = "SELECT MAX(idProgram) FROM program;";
	private final String UPDATE_PATH = "UPDATE Program SET Path=? where idProgram=?";
	private final String SELECT_FOR_RENAME = "SELECT path from program where idProgram like (SELECT min(idProgram) from program);";
	private final String SELECT_ID_PATH = "SELECT idProgram, path from program;";
	
	private final String[] FIELDS = { "Name: ", "Release: ", "Website: ",
			"Company name: ", "Foundation year: ", "Country: ",
			"Number of employees: ", "Industry: ", "Name: ", "Category: ",
			"Type of execution: ", "Type name: ", "Speciality: ", "" };
	
	/**
	 * Возвращает кол-во строк в БД
	 * 
	 * @return count
	 */
	@Override
	public int getCount() {
		int count = 0;
		try {
			pst = con.prepareStatement(SELECT_COUNT);
			rs = pst.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getLastId() {
		int max = 0;
		try {
			pst = con.prepareStatement(SELECT_MAX);
			rs = pst.executeQuery();
			while (rs.next()) {
				max = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	/**
	 * Проверка формата вводимой даты релиза приложения
	 * 
	 * @param format
	 *            of data
	 * @param data
	 * @return true/false
	 */
	private boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(value);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

	@Override
	public boolean insertProgram(String[] field, int index) {
		String Release = field[1];

		if (isValidFormat("yyyy-MM-dd", Release)) {
			try {
				con.setAutoCommit(false);
				pst = con.prepareStatement(INSERT_DEVELOPER);
				pst.setInt(1, index);
				pst.setString(2, field[3]);
				pst.setInt(3, Integer.parseInt(field[4]));
				pst.setString(4, field[5]);
				pst.setInt(5, Integer.parseInt(field[6]));
				pst.setString(6, field[7]);
				pst.executeUpdate();

				pst = con.prepareStatement(INSERT_LANGUAGE);
				pst.setInt(1, index);
				pst.setString(2, field[8]);
				pst.setString(3, field[9]);
				pst.setString(4, field[10]);
				pst.executeUpdate();

				pst = con.prepareStatement(INSERT_TYPE);
				pst.setInt(1, index);
				pst.setString(2, field[11]);
				pst.setString(3, field[12]);
				pst.executeUpdate();

				pst = con.prepareStatement(INSERT_PROGRAM);
				pst.setInt(1, index);
				pst.setInt(2, index);
				pst.setInt(3, index);
				pst.setInt(4, index);
				pst.setString(5, field[0]);
				pst.setDate(6, java.sql.Date.valueOf(Release));
				pst.setString(7, field[2]);
				pst.setString(8, field[13]);
				pst.setString(9, field[14]);
				pst.executeUpdate();

				con.commit();
				con.setAutoCommit(true);
				return true;
			} catch (Exception e) {
				try {
					con.rollback();
					con.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean changeProgram(String[] field, int index) {

		String Release = field[1];
		if (isValidFormat("yyyy-MM-dd", Release)) {
			try {
				pst = con.prepareStatement(CHANGE_PROGRAM);
				pst.setString(1, field[0]);
				pst.setDate(2, java.sql.Date.valueOf(Release));
				pst.setString(3, field[2]);
				pst.setString(4, field[13]);
				pst.setInt(5, index);
				pst.executeUpdate();

				pst = con.prepareStatement(CHANGE_DEVELOPER);
				pst.setString(1, field[3]);
				pst.setInt(2, Integer.parseInt(field[4]));
				pst.setString(3, field[5]);
				pst.setInt(4, Integer.parseInt(field[6]));
				pst.setString(5, field[7]);
				pst.setInt(6, index);
				pst.executeUpdate();

				pst = con.prepareStatement(CHANGE_LANGUAGE);
				pst.setString(1, field[8]);
				pst.setString(2, field[9]);
				pst.setString(3, field[10]);
				pst.setInt(4, index);
				pst.executeUpdate();

				pst = con.prepareStatement(CHANGE_TYPE);
				pst.setString(1, field[11]);
				pst.setString(2, field[12]);
				pst.setInt(3, index);
				pst.executeUpdate();

				return true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	public void deleteProgram(int index) {
		try {
			pst = con.prepareStatement(DELETE_PROGRAM);
			pst.setInt(1, index);
			pst.executeUpdate();

			pst = con.prepareStatement(DELETE_DEVELOPER);
			pst.setInt(1, index);
			pst.executeUpdate();

			pst = con.prepareStatement(DELETE_LANGUAGE);
			pst.setInt(1, index);
			pst.executeUpdate();

			pst = con.prepareStatement(DELETE_TYPE);
			pst.setInt(1, index);
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void renameProgram(String fName, String sName) {

		try {
			pst = con.prepareStatement(RENAME);
			pst.setString(1, sName);
			pst.setString(2, fName);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Vector<?>[] fillTable(ResultSet rs) {
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int numberOfColumns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();

			for (int column = 0; column < numberOfColumns; column++) {
				columnNames.addElement(metaData.getColumnLabel(column + 1));
			}

			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

			while (rs.next()) {
				Vector<Object> newRow = new Vector<Object>();

				for (int i = 1; i <= numberOfColumns; i++) {
					newRow.addElement(rs.getObject(i));
				}

				rows.addElement(newRow);
			}
			return new Vector[] { rows, columnNames };// new
														// DefaultTableModel(rows,
														// columnNames);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	@Override
	public Vector<?>[][] updateTable() {
		Vector<?>[][] vec = new Vector[4][2];
		try {
			String[] query = { SELECT_PROGRAM, SELECT_DEVELOPER, SELECT_LANGUAGE, SELECT_TYPE };

			for (int i = 0; i < 4; i++) {
				pst = con.prepareStatement(query[i]);
				rs = pst.executeQuery();

				vec[i][0] = fillTable(rs)[0];
				vec[i][1] = fillTable(rs)[1];
			}
			return vec;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String setDescription(int index) {

		String attributes[] = { "Program:\n", "Developer:\n",
				"Programming language:\n", "Type of program:\n",
				"Description:\n" };

		String query[] = { SELECT_PROGRAM_IF, SELECT_DEVELOPER_IF, SELECT_LANGUAGE_IF,
				SELECT_TYPE_IF, SELECT_DESCRIPTION_IF };

		StringBuilder view = new StringBuilder();
		int k = 0;
		try {
			for (int i = 0; i < 5; i++) {
				pst = con.prepareStatement(query[i]);
				pst.setInt(1, index);
				rs = pst.executeQuery();

				view.append(attributes[i]);
				while (rs.next()) {
					for (int j = 1; j < rs.getMetaData().getColumnCount() + 1; j++, k++) {
						view.append("        ").append(FIELDS[k]).append("  ")
								.append(rs.getString(j)).append("\n");
					}
					view.append("\n");
				}
			}
			return view.toString();
		}

		catch (SQLException e) {
			System.out.println(e);
		}
		return null;

	}

	@Override
	public List<Programs> reading() {
		String value = null, path = null;
		List<Programs> list = new LinkedList<Programs>();
		try {
			pst = con.prepareStatement(SELECT_NAME_PATH);
			rs = pst.executeQuery();
			while (rs.next()) {
				value = rs.getString(1);
				path = rs.getString(2);

				list.add(new Programs(value, path));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String[] getListData(int dbIndex) {
		String value = null, path = null;
		try {
			pst = con
					.prepareStatement(SELECT_NAME_PATH_IF);
			pst.setInt(1, dbIndex);
			rs = pst.executeQuery();

			while (rs.next()) {
				value = rs.getString(1);
				path = rs.getString(2);
			}
			return new String[] { value, path };
		} catch (SQLException e) {
			System.out.println(e);
		}

		return null;
	}

	@Override
	public int getDBIndexByListIndex(String name) {
		int id = 0;
		try {
			pst = con
					.prepareStatement(SELECT_ID);
			pst.setString(1, name);
			rs = pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean changePath(String detectedDisk) {

		String findPath = "";
		try {
			pst = con.prepareStatement(SELECT_FOR_RENAME);
			rs = pst.executeQuery();

			while (rs.next()) {
				findPath = rs.getString(1);
			}
			findPath = findPath.charAt(0) + "";
			String path;

			pst = con.prepareStatement(SELECT_ID_PATH);
			rs = pst.executeQuery();

			if (!detectedDisk.equals(findPath)) {
				while (rs.next()) {
					path = rs.getString(2);
					path = path.replaceFirst(findPath, detectedDisk);
					path = path.replace("\\", "\\\\");
					pst = con
							.prepareStatement(UPDATE_PATH);
					pst.setString(2, path);
					pst.setInt(1, rs.getInt(2));
					pst.executeUpdate();
				}
				return true;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public Vector<?>[][] showOnSearch(String readingLine) {
		vec = new Vector[4][2];

		if (readingLine.isEmpty()) {
			return updateTable();
		} else {
			getTable("{call sel_find(?)}", 0, readingLine);
			getTable("{call sel_find_dev(?)}", 1, readingLine);
			getTable("{call sel_find_lan(?)}", 2, readingLine);
			getTable("{call sel_find_type(?)}", 3, readingLine);
		}
		return vec;
	}

	private void getTable(String callableQuery, int index, String readingLine) {
		try {
			cstm = con.prepareCall(callableQuery);
			cstm.setString(1, "%" + readingLine + "%");
			rs = cstm.executeQuery();
			vec[index][0] = fillTable(rs)[0];
			vec[index][1] = fillTable(rs)[1];

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String[] fieldsView(int index) {
		String[] all = new String[14];
		String query[] = { SELECT_PROGRAM_IF, SELECT_DEVELOPER_IF, SELECT_LANGUAGE_IF,
				SELECT_TYPE_IF, SELECT_DESCRIPTION_IF };

		int k = 0;
		try {
			for (int i = 0; i < 5; i++) {
				pst = con.prepareStatement(query[i]);
				pst.setInt(1, index);
				rs = pst.executeQuery();

				while (rs.next()) {
					for (int j = 1; j < rs.getMetaData().getColumnCount() + 1; j++) {
						all[k] = rs.getString(j);
						k++;
					}
				}
			}
			return all;

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;

	}

}
