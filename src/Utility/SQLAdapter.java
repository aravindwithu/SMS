package Utility;

import java.sql.Connection;
import java.sql.SQLException;
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
}
