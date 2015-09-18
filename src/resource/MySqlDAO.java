/*package resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import com.builder.form.MainForm;
import com.builder.overlapView.Programs;

*//**
 * Набор методов, позволяющих работать с SQL запросами
 * 
 * @author Vladislav
 *//*
public class MySqlDAO implements AbstractDAO {
	private static Icon icon = null;

	public static Connection con = MySqlConnection.getConnection();
	private static ResultSet rs = null;
	private static PreparedStatement pst = null;

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

	private final String SELECT_PROGRAM = "SELECT Name,`Release`,`Web-site` FROM program WHERE idProgram = ?";
	private final String SELECT_DEVELOPER = "SELECT `Company name`, `Foundation year`, Country, `Number of employees`,Industry FROM developer WHERE idDeveloper=?";
	private final String SELECT_LANGUAGE = "SELECT Name, Category, `Type of execution` FROM programminglanguage WHERE idProgrammingLanguage=?";
	private final String SELECT_TYPE = "SELECT `Type name`, Specialty FROM type WHERE idType=?";
	private final String SELECT_DESCRIPTION = "SELECT `Description` FROM program WHERE idProgram=?";

	private final String RENAME = "UPDATE program SET name = ? where Name= ?;";

	private final String[] FIELDS = { "Name: ", "Release: ", "Website: ",
			"Company name: ", "Foundation year: ", "Country: ",
			"Number of employees: ", "Industry: ", "Name: ", "Category: ",
			"Type of execution: ", "Type name: ", "Speciality: ", "" };

	*//**
	 * Получает результат запроса
	 * 
	 * @param query
	 * @return result set
	 *//*
	public ResultSet getResult(String query) {
		try {
			pst = con.prepareStatement(query);
			rs = pst.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	*//**
	 * Возвращает кол-во строк в БД
	 * 
	 * @return count
	 *//*
	@Override
	public int getCount() {
		int count = 0;
		try {
			pst = con.prepareStatement("SELECT COUNT(*) FROM program;");
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
			pst = con.prepareStatement("SELECT MAX(idProgram) FROM program;");
			rs = pst.executeQuery();
			while (rs.next()) {
				max = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	*//**
	 * Проверка формата вводимой даты релиза приложения
	 * 
	 * @param format
	 *            of data
	 * @param data
	 * @return true/false
	 *//*
	private static boolean isValidFormat(String format, String value) {
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

			updateTable();

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

	@Override
	public void updateTable() {
		try {
			String[] query = {
					"Select Name,`Release`,`Web-site` from program",
					"Select `Company name`,`Foundation year`,`Country`,`Number of employees`,`Industry` from developer",
					"Select `Name`,`Category`,`Type of execution` from programminglanguage",
					"Select `Type name`,`Specialty` from type" };

			for (int i = 0; i < 4; i++) {
				pst = con.prepareStatement(query[i]);
				rs = pst.executeQuery();

				MainForm.table[i].setModel(DbUtils.resultSetToTableModel(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String setDescription(int index) {

		String attributes[] = { "Program:\n", "Developer:\n",
				"Programming language:\n", "Type of program:\n",
				"Description:\n" };

		String query[] = { SELECT_PROGRAM, SELECT_DEVELOPER, SELECT_LANGUAGE,
				SELECT_TYPE, SELECT_DESCRIPTION };

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
	public void reading() {
		String value = null, path = null;
		try {
			pst = con.prepareStatement("Select Name,Path from Program");
			rs = pst.executeQuery();
			while (rs.next()) {
				value = rs.getString(1);
				path = rs.getString(2);
				icon = MainForm.getIcon(path);
				MainForm.listModel.addElement(new Programs(value, icon));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String[] getListData(int dbIndex) {
		String value = null, path = null;
		try {
			pst = con
					.prepareStatement("SELECT NAME,PATH FROM Program WHERE idProgram=?");
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
	public int getDBIndexByListIndex(int index) {
		String name = MainForm.listModel.get(index).getValue();
		int id = 0;
		try {
			pst = con
					.prepareStatement("Select idProgram from program where name=?;");
			pst.setString(1, name);
			rs = pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void deleteFromDB(int index) {
		int id = getDBIndexByListIndex(index);
		deleteProgram(id);
	}

	@Override
	public boolean changePath(String detectedDisk) {

		String findPath = getListData(getDBIndexByListIndex(0))[1].substring(0,
				1);
		String path;

		if (!detectedDisk.equals(findPath)) {
			try {
				for (int i = 0; i < getCount(); i++) {
					path = getListData(getDBIndexByListIndex(i))[1];
					path = path.replaceFirst(findPath, detectedDisk);
					path = path.replace("\\", "\\\\");
					pst = con.prepareStatement("UPDATE Program SET Path='"
							+ path + "' where idProgram="
							+ getDBIndexByListIndex(i));
					pst.executeUpdate();

				}
				return true;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void showOnSearch(String searchQuery) {
		String[] query = {
				"SELECT Name,`Release`,`Web-site` FROM program WHERE Name LIKE ?;",
				"SELECT `Company name`, `Foundation year`, Country, `Number of employees`,Industry FROM developer WHERE idDeveloper IN (SELECT idProgram FROM PROGRAM WHERE NAME LIKE ?);",
				"SELECT Name, Category, `Type of execution` FROM programminglanguage WHERE idProgrammingLanguage IN (SELECT idProgram FROM PROGRAM WHERE NAME LIKE ?);",
				"SELECT `Type name`, Specialty FROM type WHERE idType IN (SELECT idProgram FROM PROGRAM WHERE NAME LIKE ?);" };

		if (MainForm.findProgram.getText().isEmpty()) {
			updateTable();
		} else {
			for (int i = 0; i < 4; i++) {
				try {
					pst = con.prepareStatement(query[i]);
					pst.setString(1, "%" + searchQuery + "%");
					rs = pst.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				MainForm.table[i].setModel(DbUtils.resultSetToTableModel(rs));
				
				
			}
		}
	}

	@Override
	public String[] fieldsView(int index) {
		String[] all = new String[14];
		String query[] = { SELECT_PROGRAM, SELECT_DEVELOPER, SELECT_LANGUAGE,
				SELECT_TYPE, SELECT_DESCRIPTION };

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
	
	private static Vector<?>[] fillTable(ResultSet rs) {
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
		    return new Vector[] {rows, columnNames};//new DefaultTableModel(rows, columnNames);
		} catch (Exception e) {
		    e.printStackTrace();

		    return null;
		}
	}
}
*/