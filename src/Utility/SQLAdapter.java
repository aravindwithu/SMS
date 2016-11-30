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
			  	conn = null;
			  	ds = new oracle.jdbc.pool.OracleDataSource();		        
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
			String query = "begin SMSPack.showStudents(?); end;";
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
	
	public ArrayList<Courses> getAllCourses() throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {		
		ArrayList<Courses> resultList = new ArrayList<Courses>();
		ResultSet rset = null;
		CallableStatement cs = null;
		try {		
			String query = "begin SMSPack.showCourses(?); end;";
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
						Courses cr = new Courses();
						cr.dept_code = rset.getString(1);
						if(rset.getString(2)!=null){ 
							cr.course_no = Integer.parseInt(rset.getString(2));
						 } else{
							 cr.course_no  = 0;};
						cr.title = rset.getString(3);
						resultList.add(cr);
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
	
	public ArrayList<Prerequisites> getAllPrerequisites() throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {		
		ArrayList<Prerequisites> resultList = new ArrayList<Prerequisites>();
		ResultSet rset = null;
		CallableStatement cs = null;
		try {		
			String query = "{call SMSPack.showPrerequisites(?)}";
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
						Prerequisites pr = new Prerequisites();
						pr.dept_code = rset.getString(1);
						if(rset.getString(2)!=null){ 
							pr.course_no = Integer.parseInt(rset.getString(2));
						 } else{
							 pr.course_no  = 0;};
						 pr.pre_dept_code = rset.getString(3);
						if(rset.getString(4)!=null){ 
							pr.pre_course_no = Integer.parseInt(rset.getString(4));
						 } else{
							 pr.pre_course_no  = 0;};
						resultList.add(pr);
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
	
	public ArrayList<Classes> getAllClasses() throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {		
		ArrayList<Classes> resultList = new ArrayList<Classes>();
		ResultSet rset = null;
		CallableStatement cs = null;
		try {		
			String query = "{call SMSPack.showClasses(?)}";
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
						Classes cl = new Classes();
						cl.classid = rset.getString(1);
						cl.dept_code = rset.getString(2);
						if(rset.getString(3)!=null){ 
							cl.course_no = Integer.parseInt(rset.getString(3));
						 } else{
							 cl.course_no  = 0;};
						if(rset.getString(4)!=null){ 
							cl.sect_no= Integer.parseInt(rset.getString(4));
						 } else{
							 cl.sect_no  = 0;};
						 if(rset.getString(5)!=null){ 
								cl.year = Integer.parseInt(rset.getString(5));
							 } else{
								 cl.year  = 0;};							 
						cl.semester = rset.getString(6);	
						if(rset.getString(7)!=null){ 
							cl.limit = Integer.parseInt(rset.getString(7));
						 } else{
							 cl.limit  = 0;};
						if(rset.getString(8)!=null){ 
							cl.class_size = Integer.parseInt(rset.getString(8));
						 } else{
							 cl.class_size = 0;};
						resultList.add(cl);
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
	
	public ArrayList<Enrollments> getAllEnrollments() throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {		
		ArrayList<Enrollments> resultList = new ArrayList<Enrollments>();
		ResultSet rset = null;
		CallableStatement cs = null;
		try {		
			String query = "{call SMSPack.showEnrollments(?)}";
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
						Enrollments en = new Enrollments();
						en.sid = rset.getString(1);
						en.classid = rset.getString(2);
						en.lgrade = rset.getString(3);
						resultList.add(en);
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
	
	public ArrayList<Logs> getAllLogs() throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {	
	ArrayList<Logs> resultList = new ArrayList<Logs>();
	ResultSet rset = null;
	CallableStatement cs = null;
	try {		
		String query = "{call SMSPack.showLogs(?)}";
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

	
	public String insertStudent(Students stdIn) throws ClassNotFoundException {
		String result = "";
		CallableStatement cs = null;
		try {
			String query = "{call SMSPack.insertStudents(?, ?, ?, ?, ?, ?)}";

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
		}

		return result;
	}
	
	public String deleteStudent(String stdIn) throws ClassNotFoundException {
		String result = "";
		CallableStatement cs = null;
		try {
			String query = "{call SMSPack.deleteStudents(?, ?)}";

			if (conn == null) {
				openSQLConnection();
			}

			cs = conn.prepareCall(query);
			cs.setString(1, stdIn);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);

			// execute and retrieve the result set
			cs.executeUpdate();
			result = cs.getString(2);		
			
			closeSQLConnection();
		
		} catch (SQLException e) {
			System.out.println("SQL Exception occured!!\n\nDetails: "
					+ e.getMessage());
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
			String query = "{call SMSPack.getStudentAndClasses(?, ?, ?, ?, ?)}";

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
			String query = "{call SMSPack.getClassAndStudents(?, ?, ?, ?, ?, ?)}";

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
	
	
	
	/*private void initPreReqCourses(String dept_code, int course_no) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {	
	CallableStatement cs = null;	
	try {	
		String query = "{call initPreReqCourses(?, ?)}";
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

		// execute and retrieve the result set
	    cs.executeUpdate();
	    
	    closeSQLConnection();
	    
		} catch (SQLException e) {
			System.out.println("SQL Exception occured!!\n\nDetails: "
					+ e.getMessage());
		}
	}*/
	
	
	public ArrayList<Prerequisites> getPrerequisites(String dept_code, int course_no) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {	
	ArrayList<Prerequisites> resultList = new ArrayList<Prerequisites>();
	ResultSet rset = null;		
	try {			
		//this.initPreReqCourses(dept_code, course_no);
		CallableStatement cs = null;
		String query = "{call SMSPack.getPrerequisiteCourses(?,?,?)}";
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
		 cs.executeUpdate();		
	
		rset = (ResultSet)cs.getObject(3);
		
		while (rset.next()) {
			Prerequisites preReq = new Prerequisites();
			preReq.course_count = Integer.parseInt(rset.getString(1));	
			preReq.course_code = rset.getString(2);	
			resultList.add(preReq);			
			}
			
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
		String query = "{call SMSPack.enrollStudent(?, ?, ?)}";
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
		String query = "{call SMSPack.dropEnrollment(?, ?, ?)}";
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
