package SMS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import Utility.SQLAdapter;

public class SMS {
	 public static void main (String args []){
	  try
	    {
		    SQLAdapter sql= new SQLAdapter();
		    sql.openSQLConnection();
	        BufferedReader  readKeyBoard; 
	        String          sid;
	        readKeyBoard = new BufferedReader(new InputStreamReader(System.in)); 
	        System.out.print("Please Enter SID:");
	        sid = readKeyBoard.readLine();	        
	        System.out.println("Sid is " + sid);
	        sql.closeSQLConnection();
	    }	  
	  catch(Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}
}
