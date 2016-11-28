package SMS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import Utility.*;
import data.*;

public class SMS {
	 public static void main (String args []){
	  try
	    {
		    SQLAdapter sql= new SQLAdapter();
	        BufferedReader  readKeyBoard; 
	        	        
	        //-----------------------------------------
	        //--2.
	        /*ArrayList<Students> resultList = new ArrayList<Students>();
	        resultList = sql.getAllStudents();
	       
	        for(Students result : resultList){
	        	System.out.println(result.sid);}*/
	        
	        
	        //------------------------------------------
	        //--3.
	        /*Students std = new Students();
	        std.sid = null;
	        std.firstname = "ham";
	        std.lastname = "bal";
	        std.status = "freshman";
	        std.gpa = 3.5;
	        std.email = "bal@bu.edu";
	        
	        String result = sql.insertStudent(std);
	        System.out.print("The new B number is: " + result);*/
	        
	      //------------------------------------------
	        //--4.
	        
	        /* readKeyBoard = new BufferedReader(new InputStreamReader(System.in)); 
	         String sid;
	         System.out.print("Please Enter SID:");
	         sid = readKeyBoard.readLine();	        
	         System.out.println("Sid is " + sid);
	         
	         StudentAndClasses stclResult = new StudentAndClasses();
	        
	        stclResult = sql.getStudentAndClasses(sid);
	        
	        if(stclResult.q_status == 1){
		        System.out.println(stclResult.student.sid + ", "+ stclResult.student.lastname + ", "+ stclResult.student.status);
		        for(Classes cl : stclResult.classes){
		        	System.out.println(cl.classid + ", "+ cl.classCode + ", "+ cl.title + ", "+ cl.year + ", "+ cl.semester);
		        }
	        }
	        else{System.out.println("Student not found");}*/
	        
	        //-----------------------------------------------------------
	        //5.	        
	           
	        /*ArrayList<Prerequisites> resultList = new ArrayList<Prerequisites>();
	        
	        resultList = sql.getPrerequisites("CS", 432);
	        
	        if(resultList != null)
	        {
	        	for(Prerequisites preReq : resultList){
		        	System.out.println(preReq.course_count + ", "+preReq.course_code);
		        }
	        }*/
	        
	        //-----------------------------------------------------------
	        //6.
	        /*
	        readKeyBoard = new BufferedReader(new InputStreamReader(System.in)); 
	         String class_Id;
	        System.out.print("Please Enter Class Id:");
	        Class_Id = readKeyBoard.readLine();	        
	        System.out.println("Class Id is " + class_Id);
	        
	        ClassAndStudents clstResult = new ClassAndStudents();
	        
	        clstResult = sql.getClassAndStudents(Class_Id);
	        
		        if(clstResult.q_status == 1){
		        System.out.println(clstResult.classInfo.classid + ", "+ clstResult.classInfo.title + ", "+ clstResult.classInfo.semester
		        		+ ", "+ clstResult.classInfo.year);
		        for(Students std : clstResult.students){
		        	System.out.println(std.sid + ", "+ std.lastname);
		        }
	        }
		        else{System.out.println("class not found");}*/    
	        
	      //-----------------------------------------------------------
	      //7.	      
	      //System.out.println(sql.enrollStudent("B009", "c0001"));
	       
	     //-----------------------------------------------------------
		 //8.
	     //System.out.println(sql.dropEnrollment("B001", "c0001"));
	        
	        
	        
	        
	    }	  
	  catch(Exception e) {System.out.println ("\n*** other Exception caught ***\n" + e.getMessage());}
	}
}
