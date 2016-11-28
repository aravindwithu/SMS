package Utility;

import java.sql.*;
import java.util.*;
import data.*;
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

public class SQLAdapter {
	
	private static OracleDataSource ds = null; 
	private static Connection conn = null;
	
	public SQLAdapter() {
		 try {
			ds = new oracle.jdbc.pool.OracleDataSource();
			conn = null;
		} catch (SQLException e) {
			System.out.println ("\n*** SQLException caught ***\n" + e.getMessage());
		}
	}
	
	//Connection to Oracle server
	public void openSQLConnection() throws SQLException{
	  try
	    {	        
	        ds.setURL(Constants.CONN_STR);
	        conn = ds.getConnection(Constants.CONN_USER_NAME, Constants.CONN_PASSWORD);
	        System.out.println("Connection to SQL is opened."); 
	    }
	  catch(SQLException ex) { System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());}
	  catch(Exception e) {System.out.println ("\n*** other Exception caught ***\n");}	
	}
	
	public void closeSQLConnection() throws SQLException{
		  try
		    {	        
			  	conn.close();
			  	ds = null;		        
		        System.out.println("Connection to SQL is closed."); 
		    }
		  catch(SQLException ex) { System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());}
		  catch(Exception e) {System.out.println ("\n*** other Exception caught ***\n");}	
		}
	
	public ArrayList<Students> getAllStudents() throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {		
		ArrayList<Students> resultList = new ArrayList<Students>();
		ResultSet rset = null;
		CallableStatement cs = null;
		try {		
			String query = "begin showStudents(?); end;";
				if (conn == null) {
					openSQLConnection();
				}	
				else{
					closeSQLConnection();
					openSQLConnection();
				}
				cs = conn.prepareCall(query);	
				// register the out parameter (the first parameter)
				cs.registerOutParameter(1, OracleTypes.CURSOR);		
				// execute and retrieve the result set
				cs.executeUpdate();	
			    //rset = ((OracleCallableStatement)cs).getCursor(1);
			    rset =(ResultSet) cs.getObject(1);
			    if(rset != null){
					while (rset.next()) {
						Students std = new Students();
						std.sid = rset.getString(1);
						std.firstname = rset.getString(2);
						std.lastname = rset.getString(3);
						std.status = rset.getString(4);
						if(rset.getString(5)!=null){ 
							 	std.gpa = Double.parseDouble(rset.getString(5));
							 } else{
								 std.gpa = 0;};
						std.email = rset.getString(6);
						resultList.add(std);
					}	
			    }
				closeSQLConnection();
		}
		catch (SQLException e) {
			System.out.println("SQL Exception occured!!\n\nDetails: "
					+ e.getMessage());
			}
		return resultList;
	}
	
	public String insertStudent(Students stdIn) throws ClassNotFoundException {
		String result = "";
		CallableStatement cs = null;
		try {
			String query = "{call insertStudents(?, ?, ?, ?, ?, ?)}";

			if (conn == null) {
				openSQLConnection();
			}

			cs = conn.prepareCall(query);
			cs.registerOutParameter(1, java.sql.Types.CHAR);
			cs.setString(2, stdIn.firstname);
			cs.setString(3, stdIn.lastname);
			cs.setString(4, stdIn.status);
			cs.setDouble(5, stdIn.gpa);
			cs.setString(6, stdIn.email);		

			// execute and retrieve the result set
			cs.executeUpdate();
			result = cs.getString(1);
			
			closeSQLConnection();

		} catch (SQLException e) {
			System.out.println("SQL Exception occured!!\n\nDetails: "
					+ e.getMessage());
			result = "SQL Exception occured!!\n\nDetails: " + e.getMessage();
		}

		return result;
	}
	
	public StudentAndClasses getStudentAndClasses(String sidIn) throws ClassNotFoundException {
		StudentAndClasses stclResult= new StudentAndClasses();
		CallableStatement cs = null;
		Students std= new Students();
		ArrayList<Classes> classList = new ArrayList<Classes>();
		ResultSet classSet = null;
		try {
			String query = "{call getStudentAndClasses(?, ?, ?, ?, ?)}";

			if (conn == null) {
				openSQLConnection();
			}

			cs = conn.prepareCall(query);
			cs.setString(1, sidIn);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.registerOutParameter(5, java.sql.Types.NUMERIC);

			// execute and retrieve the result set
			cs.executeUpdate();
			std.sid = sidIn;
			std.lastname = cs.getString(2);
			std.status = cs.getString(3);
			classSet = (ResultSet) cs.getObject(4);
			stclResult.q_status = Integer.parseInt(cs.getString(5));
			
			if(stclResult.q_status == 1 && classSet!= null)
			{
				while (classSet.next()) {
					Classes cl = new Classes();
					cl.classid = classSet.getString(1);
					cl.classCode = classSet.getString(2);
					cl.title = classSet.getString(3);
					if(classSet.getString(4)!=null){
					cl.year = Integer.parseInt(classSet.getString(4));
					}else{
						cl.year = 0;
					}				
					cl.semester = classSet.getString(5);
					classList.add(cl);
				}	
				stclResult.classes = classList;
			}
			stclResult.student = std;
			
			closeSQLConnection();

		} catch (SQLException e) {
			System.out.println("SQL Exception occured!!\n\nDetails: "
					+ e.getMessage());			
		}

		return stclResult;
	}	
	
	public ClassAndStudents getClassAndStudents(String classidIn) throws ClassNotFoundException {
		ClassAndStudents clstResult= new ClassAndStudents();
		CallableStatement cs = null;
		Classes cl= new Classes();
		ArrayList<Students> studentList = new ArrayList<Students>();
		ResultSet studentSet = null;
		try {
			String query = "{call getClassAndStudents(?, ?, ?, ?, ?, ?)}";

			if (conn == null) {
				openSQLConnection();
			}

			cs = conn.prepareCall(query);
			cs.setString(1, classidIn);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.registerOutParameter(4, java.sql.Types.NUMERIC);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			cs.registerOutParameter(6, java.sql.Types.NUMERIC);

			// execute and retrieve the result set
			cs.executeUpdate();
			cl.classid = classidIn;
			cl.title = cs.getString(2);
			cl.semester = cs.getString(3);
			if(cs.getString(4)!=null){
			cl.year = Integer.parseInt(cs.getString(4));
			}else{
				cl.year = 0;
			}
			studentSet = (ResultSet) cs.getObject(5);
			clstResult.q_status = Integer.parseInt(cs.getString(6));
			
			if(clstResult.q_status == 1 && studentSet!=null){
			while (studentSet.next()) {
				Students std = new Students();
				std.sid = studentSet.getString(1);
				std.lastname = studentSet.getString(2);
				studentList.add(std);
		   }		
			clstResult.students = studentList;
			}
			clstResult.classInfo = cl;
			
			closeSQLConnection();

		} catch (SQLException e) {
			System.out.println("SQL Exception occured!!\n\nDetails: "
					+ e.getMessage());			
		}
		return clstResult;
	}
	
	public ArrayList<Logs> getLogs() throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {
	
	ArrayList<Logs> resultList = new ArrayList<Logs>();
	ResultSet rset = null;
	
	try {
	
		if (conn == null) {
			openSQLConnection();
		}
	
		Statement stmt = conn.createStatement();
		// execute and retrieve the result set
		rset = (ResultSet) stmt.executeQuery("Select * from logs");
		while (rset.next()) {
			Logs log = new Logs();
			if(rset.getString(1)!=null){
				log.logid = Integer.parseInt(rset.getString(1));
				}else{
					log.logid = 0;
			}			
			log.who = rset.getString(2);
			log.time = rset.getString(3);
			log.table_name = rset.getString(4);
			log.operation = rset.getString(5);
			log.key_value = rset.getString(6);
			resultList.add(log);
			}
		closeSQLConnection();
	
		} catch (SQLException e) {
			System.out.println("SQL Exception occured!!\n\nDetails: "
					+ e.getMessage());
		}
		return resultList;
	}
	
	
	public ArrayList<Prerequisites> getPrerequisites(String dept_code, int course_no) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {	
	ArrayList<Prerequisites> resultList = new ArrayList<Prerequisites>();
	ResultSet rset = null;
	CallableStatement cs = null;	
	try {	
		String query = "begin getPrerequisiteCourses(?, ?, ?); end;";
		if (conn == null) {
			openSQLConnection();
		}
		else{
			closeSQLConnection();
			openSQLConnection();		
		}
	
		cs = conn.prepareCall(query);
		cs.setString(1, dept_code);
		cs.setInt(2, course_no);		
		cs.registerOutParameter(3, OracleTypes.CURSOR);

		// execute and retrieve the result set
		try{
		
		boolean a = cs.execute();
		
		while(a){
			rset = (ResultSet)cs.getResultSet();
			
			while (rset.next()) {
				Prerequisites preReq = new Prerequisites();
				preReq.course_count = Integer.parseInt(rset.getString(1));	
				preReq.course_code = rset.getString(2);	
				resultList.add(preReq);			
				}
			rset.close();
			a = cs.getMoreResults();
			
		}
		
		cs.close();
		
		
		System.out.println(a);
		conn.commit();}
		catch(Exception e){
			e.getMessage();
		}
		rset = (ResultSet)cs.getObject(3);
		
		
		
		closeSQLConnection();
	
		} catch (SQLException e) {
			System.out.println("SQL Exception occured!!\n\nDetails: "
					+ e.getMessage());
		}
		return resultList;	
	}
	
	public String enrollStudent(String sid, String classid) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {	
	String result = "";
	CallableStatement cs = null;	
	try {	
		String query = "{call enroll_Student(?, ?, ?)}";
		if (conn == null) {
			openSQLConnection();
		}
	
		cs = conn.prepareCall(query);
		cs.setString(1, sid);
		cs.setString(2, classid);		
		cs.registerOutParameter(3, java.sql.Types.VARCHAR);

		// execute and retrieve the result set
		cs.executeUpdate();
		result = cs.getString(3);		
		
		closeSQLConnection();
	
		} catch (SQLException e) {
			System.out.println("SQL Exception occured!!\n\nDetails: "
					+ e.getMessage());
		}
		return result;	
	}
	
	public String dropEnrollment(String sid, String classid) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {	
	String result = "";
	CallableStatement cs = null;	
	try {	
		String query = "{call drop_Enrollment(?, ?, ?)}";
		if (conn == null) {
			openSQLConnection();
		}
	
		cs = conn.prepareCall(query);
		cs.setString(1, sid);
		cs.setString(2, classid);		
		cs.registerOutParameter(3, java.sql.Types.VARCHAR);

		// execute and retrieve the result set
		cs.executeUpdate();
		result = cs.getString(3);		
		
		closeSQLConnection();
	
		} catch (SQLException e) {
			System.out.println("SQL Exception occured!!\n\nDetails: "
					+ e.getMessage());
		}
		return result;	
	}
	
	
	
}
