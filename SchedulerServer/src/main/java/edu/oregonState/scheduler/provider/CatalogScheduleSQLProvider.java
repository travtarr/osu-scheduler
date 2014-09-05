package edu.oregonState.scheduler.provider;

import java.sql.*;

public class CatalogScheduleSQLProvider {

	private String username = null;
	private String password = null;
	private String address = "jdbc:mysql://";
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private final String DBTABLE = "osu_catalog";
	private String dbName = null;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final int NUM_FIELDS = 10;

	public CatalogScheduleSQLProvider(String username, String password, String address,
			String dbName) {
		this.username = username;
		this.password = password;
		this.dbName = dbName;
		this.address = this.address.concat(address);
		this.address = this.address.concat("/" + dbName);
		// System.out.println("" + this.address);
	}

	/**
	 * Adds the catalog schedule info into the database table. Use when only one
	 * transaction is required.
	 * 
	 * @param courseID
	 * @param courseName
	 * @param instructor
	 * @param term
	 * @param dateStart
	 * @param dateEnd
	 * @param classDays
	 * @param classTimeStart
	 * @param classTimeEnd
	 * @return
	 */
	public boolean addClassSchedule(String courseID, String courseName,
			String instructor, String term, String dateStart, String dateEnd,
			String classDays, String classTimeStart, String classTimeEnd) {

		// verify input data
		if (courseID.isEmpty() || courseName.isEmpty() || instructor.isEmpty()
				|| term.isEmpty() || dateStart.isEmpty() || dateEnd.isEmpty()
				|| classDays.isEmpty() || classTimeStart.isEmpty()
				|| classTimeEnd.isEmpty()) {
			return false;
		}

		// connect
		connect();

		// setup query
		String qryStr = "INSERT INTO " + DBTABLE
				+ " (courseID, courseName, instructor, term, "
				+ "dateStart, dateEnd, classDays,"
				+ "classTimeStart, classTimeEnd) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		if (conn == null)
			return false;

		try {
			// create prepared statement
			stmt = conn.prepareStatement(qryStr);
			conn.setAutoCommit(false);

			// insert variables into statement
			stmt.setString(1, courseID);
			stmt.setString(2, courseName);
			stmt.setString(3, instructor);
			stmt.setString(4, term);
			stmt.setDate(5, Date.valueOf(dateStart));
			stmt.setDate(6, Date.valueOf(dateEnd));
			stmt.setString(7, classDays);
			stmt.setString(8, classTimeStart);
			stmt.setString(9, classTimeEnd);

			// execute statement
			stmt.executeUpdate();

			// commit changes
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();

			// roll-back changes
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		} finally {
			// close statement resource
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					printError("addClassSchedule",
							"Unable to close preparedStatement");
				}

			// close connection
			close();
		}
		return true;
	}

	/**
	 * Adds the schedule information to the database. Best to use when adding
	 * multiple events at once.
	 * 
	 * @param values
	 * @return
	 */
	public boolean addClassSchedule(String[][] values) {

		// verify input data
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				if (values[i][j].isEmpty())
					return false;
			}
		}

		// open connection
		connect();

		// setup query
		String qryStr = "INSERT INTO " + DBTABLE
				+ " (courseID, courseName, instructor, term, "
				+ "dateStart, dateEnd, classDays,"
				+ "classTimeStart, classTimeEnd) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			// create prepared statement
			stmt = conn.prepareStatement(qryStr);

			// insert variables into statement
			for (int i = 0; i < values.length; i++) {
				for (int j = 0; j < values[i].length; j++) {
					if (j == 4 || j == 5)
						stmt.setDate(j + 1, Date.valueOf(values[i][j]));
					stmt.setString(j + 1, values[i][j]);
				}
				// execute statement
				stmt.executeUpdate();

				// commit changes
				conn.commit();
			}

		} catch (SQLException e) {
			e.printStackTrace();

			// roll-back changes
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		} finally {
			// close statement resource
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					printError("addClassSchedule",
							"Unable to close preparedStatement");
				}

			// close connection
			close();
		}
		return true;
	}

	/**
	 * Returns the schedule of the given instructor between two dates.
	 * 
	 * @param instructor
	 * @param dateStart
	 * @param dateEnd
	 * @return - an array of schedules, each array of strings is a separate event
	 */
	public String[][] getSchedule(String instructor, String dateStart,
			String dateEnd) {
		String[][] values = null;

		// verify input data
		if (instructor.isEmpty() || dateStart.isEmpty() || dateEnd.isEmpty()) {
			return null;
		}
		// open connection
		connect();

		// setup query
		String qryStr = "SELECT * FROM " + DBTABLE + " WHERE "
				+ "instructor = ? AND dateStart <= ? AND dateEnd >= ?";

		if (conn == null)
			return null;

		try {
			// create prepared statement
			stmt = conn.prepareStatement(qryStr);

			// insert variables into statement
			stmt.setString(1, instructor);
			stmt.setDate(2, Date.valueOf(dateStart));
			stmt.setDate(3, Date.valueOf(dateEnd));

			// execute statement
			ResultSet rs = stmt.executeQuery();

			// set-up array
			values = new String[getNumRows(rs)][NUM_FIELDS];

			// return null if no results found
			if (getNumRows(rs) == 0)
				return null;

			// get results into a string array
			int i = 0;
			while (rs.next()) {
				for (int j = 0; j < NUM_FIELDS; j++) {
					values[i][j] = rs.getString(j + 1);
					// System.out.println("result: " + values[i][j]);
				}
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close statement resource
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					printError("addClassSchedule",
							"Unable to close preparedStatement");
				}

			// close connection
			close();
		}
		return values;
	}
	
	
	/**
	 * Returns all the schedules of the given instructor.
	 * 
	 * @param instructor
	 * @return - an array of schedules, each array of strings is a separate event
	 */
	public String[][] getSchedule(String instructor) {
		String[][] values = null;

		// verify input data
		if (instructor.isEmpty()) {
			return null;
		}
		// open connection
		connect();

		// setup query
		String qryStr = "SELECT * FROM " + DBTABLE + " WHERE "
				+ "instructor = ?";

		if (conn == null)
			return null;

		try {
			// create prepared statement
			stmt = conn.prepareStatement(qryStr);

			// insert variables into statement
			stmt.setString(1, instructor);

			// execute statement
			ResultSet rs = stmt.executeQuery();

			// set-up array
			values = new String[getNumRows(rs)][NUM_FIELDS];

			// return null if no results found
			if (getNumRows(rs) == 0)
				return null;

			// get results into a string array
			int i = 0;
			while (rs.next()) {
				for (int j = 0; j < NUM_FIELDS; j++) {
					values[i][j] = rs.getString(j + 1);
					// System.out.println("result: " + values[i][j]);
				}
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close statement resource
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					printError("addClassSchedule",
							"Unable to close preparedStatement");
				}

			// close connection
			close();
		}
		return values;
	}

	/**
	 * Connects to the SQL Server
	 */
	private void connect() {
		/**
		 * TODO: Connect to SQL Server
		 */
		try {
			// Register driver
			Class.forName(JDBC_DRIVER);

			// Open connection
			conn = DriverManager.getConnection(this.address, this.username,
					this.password);
			
			conn.setAutoCommit(false);

		} catch (Exception e) {
			printError("connect", "Unable to connect to DB");
			e.printStackTrace();

		}
	}

	/**
	 * Closes the connection to the DB server.
	 */
	private void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			printError("close", "Unable to close connection to DB");
			e.printStackTrace();
		}
	}

	/**
	 * Simple error printing specific to this class.
	 * 
	 * @param method
	 * @param msg
	 */
	private void printError(String method, String msg) {
		System.out.println("[SQLUtility:" + method + "]" + " Error: " + msg);
	}

	private int getNumRows(ResultSet rs) {
		int firstRow = 0, lastRow = 0;
		try {
			if (rs.first()) {
				firstRow = rs.getRow();
			}

			if (rs.last()) {
				lastRow = rs.getRow();
			}
			rs.first();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lastRow - firstRow;
	}

	/**
	 * Checks to see if table exists and creates one if it does not exist.
	 */
	public void createTable() {
		DatabaseMetaData dbm = null;
		String qry = "CREATE TABLE  `"
				+ this.dbName
				+ "`.`osu_catalog` ("
				+ "`id` INT NOT NULL AUTO_INCREMENT ,"
				+ "`courseID` VARCHAR( 8 ) COLLATE utf8_unicode_ci NOT NULL ,"
				+ "`courseName` VARCHAR( 100 ) COLLATE utf8_unicode_ci NOT NULL ,"
				+ "`instructor` VARCHAR( 40 ) COLLATE utf8_unicode_ci NOT NULL ,"
				+ "`term` VARCHAR( 4 ) COLLATE utf8_unicode_ci NOT NULL ,"
				+ "`dateStart` DATE NOT NULL ,"
				+ "`dateEnd` DATE NOT NULL ,"
				+ "`classDays` VARCHAR( 7 ) COLLATE utf8_unicode_ci NOT NULL ,"
				+ "`classTimeStart` VARCHAR( 4 ) COLLATE utf8_unicode_ci NOT NULL ,"
				+ "`classTimeEnd` VARCHAR( 4 ) COLLATE utf8_unicode_ci NOT NULL ,"
				+ "KEY  `id` (  `id` )"
				+ ") ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci;";

		connect();

		try {
			dbm = conn.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, "osu_catalog", null);
			if (tables.next()) {
				// Table exists; remove table
				String removeQry = "DROP TABLE `osu_catalog`";
				
				// create prepared statement
				stmt = conn.prepareStatement(removeQry);

				// execute statement
				stmt.executeUpdate();

				// commit changes
				conn.commit();
			}
			// now create table

			// create prepared statement
			stmt = conn.prepareStatement(qry);

			// execute statement
			stmt.executeUpdate();

			// commit changes
			conn.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// roll-back changes
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		} finally {
			// close statement resource
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					printError("addClassSchedule",
							"Unable to close preparedStatement");
				}

			// close connection
			close();
		}

	}
}
