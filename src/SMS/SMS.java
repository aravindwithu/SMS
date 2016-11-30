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
	       
		    /*BufferedReader  readKeyBoard; 	        
	        while (true) {
	            System.out.println("\n\n------- STUDENT MANAGEMENT SYSTEM  -------");
	            System.out.println("Select the Operation to perform:");
	            System.out.println(" " +
	                "- (1) Show Tables\n " +
	                "- (2) Insert Student \n " +
	                "- (3) Delete Student\n " +
	                "- (4) Enroll Student\n " +
	                "- (5) Un-Enroll Student\n " +
	                "- (6) Get Student and Class\n " +
	                "- (7) Get Class and Students\n" +
	                "- (e) Exit \n");
	            System.out.println("-------------------------------------------");
	            System.out.print("\nYour Selection >> ");
	            
	        }*/
	        
	        
	        
		    boolean loop1 = true;
	        Scanner in = new Scanner(System.in);
			while(loop1){	
				System.out.println("-------------------------------------- STUDENT MANAGEMENT SYSTEM  ------------------------------------------\n");
				System.out.println(" 1-> Show Tables	2->Insert Student		3-> Delete Student		4-> Enroll Student\n"); 
			    System.out.println(" 5-> Un-Enroll Student	6-> Get Student with Class	7-> Get Class with Students	8-> Get PreRequisite Courses \n");	
			    System.out.println(" 9-> Exit\n");
				System.out.println("Select one of the above menu item: ");
				String command = in.nextLine();				
				 switch (command.trim()) {
                 case "1":
                	 boolean loop2 = true;
                	 while(loop2){
                		 System.out.println(" " +
                                 "- (1) Show Students Table\n " +
                                 "- (2) Show Courses Table \n " +
                                 "- (3) Show Prerequisites Table\n " +
                                 "- (4) Show Classes Table\n " +
                                 "- (5) Show Enrollments Table\n " +
                                 "- (6) Show Log Table\n " +
                                 "- (7) Back \n");
                		 String command1 = in.nextLine();	
                		 switch (command1.trim()) {
                		 	case "1":
	                		 		ArrayList<Students> resultList = new ArrayList<Students>();
	                		        resultList = sql.getAllStudents();	                		        
	                		        System.out.println(
	                		        		String.format("%-"+ 10 + "s", "sid")+
                		        			String.format("%-"+ 10 + "s", "firstname")+
                		        			String.format("%-"+ 10 + "s", "lastname")+
                		        			String.format("%-"+ 10 + "s", "status")+
                		        			String.format("%-"+ 10 + "s", "gpa")+
                		        			String.format("%-"+ 10 + "s", "email")
	                		        		);
	                		        System.out.println("------------------------------------------------------------------------");
	                		        for(Students result : resultList){
	                		        	System.out.println(
	                		        			String.format("%-"+ 10 + "s", result.sid)+
	                		        			String.format("%-"+ 10 + "s", result.firstname)+
	                		        			String.format("%-"+ 10 + "s", result.lastname)+
	                		        			String.format("%-"+ 10 + "s", result.status)+
	                		        			String.format("%-"+ 10 + "s", result.gpa)+
	                		        			String.format("%-"+ 10 + "s", result.email)
	                		        			);}
	                		        System.out.println("-----------------------------------------------------------------------\n");
                		 		break;
                		 	case "2":
                		 		 ArrayList<Courses> CoursesList = new ArrayList<Courses>();
                			        CoursesList = sql.getAllCourses();
                			       
                			        System.out.println(
	                		        		String.format("%-"+ 12 + "s", "dept_code")+
                		        			String.format("%-"+ 12 + "s", "course_no")+
                		        			String.format("%-"+ 12 + "s", "title")
	                		        		);
	                		        System.out.println("------------------------------------------------------------------------");
	                		        for(Courses result : CoursesList){
	                		        	System.out.println(
	                		        			String.format("%-"+ 12 + "s", result.dept_code)+
	                		        			String.format("%-"+ 12 + "s", result.course_no)+
	                		        			String.format("%-"+ 12 + "s", result.title)
	                		        			);}
	                		        System.out.println("-----------------------------------------------------------------------\n");
                			        
                			       
                		 		break;
                		 	case "3":
                		 		 ArrayList<Prerequisites> PrerequisitesList = new ArrayList<Prerequisites>();
                			        PrerequisitesList = sql.getAllPrerequisites();
                			       
                			        System.out.println(
	                		        		String.format("%-"+ 12 + "s", "dept_code")+
                		        			String.format("%-"+ 12 + "s", "course_no")+
                		        			String.format("%-"+ 12 + "s", "pre_dept_code")+
                		        			String.format("%-"+ 12 + "s", "pre_course_no")
	                		        		);
	                		        System.out.println("------------------------------------------------------------------------");
	                		        for(Prerequisites result : PrerequisitesList){
	                		        	System.out.println(
	                		        			String.format("%-"+ 12 + "s", result.dept_code)+
	                		        			String.format("%-"+ 12 + "s", result.course_no)+
	                		        			String.format("%-"+ 12 + "s", result.pre_dept_code)+
	                		        			String.format("%-"+ 12 + "s", result.pre_course_no)
	                		        			);}
	                		        System.out.println("-----------------------------------------------------------------------\n");
                			                        			       
                		 		break;
                		 	case "4":
                		 		 ArrayList<Classes> ClassesList = new ArrayList<Classes>();
                			        ClassesList = sql.getAllClasses();
                			       
                			        System.out.println(
	                		        		String.format("%-"+ 10 + "s", "classid")+
                		        			String.format("%-"+ 10 + "s", "dept_code")+
                		        			String.format("%-"+ 10 + "s", "course_no")+
                		        			String.format("%-"+ 10 + "s", "sect_no")+
                		        			String.format("%-"+ 10 + "s", "year")+
                		        			String.format("%-"+ 10 + "s", "semester")+
                		        			String.format("%-"+ 10 + "s", "limit")+
                		        			String.format("%-"+ 10 + "s", "class_size")
	                		        		);
	                		        System.out.println("------------------------------------------------------------------------");
	                		        for(Classes result : ClassesList){
	                		        	System.out.println(
	                		        			String.format("%-"+ 10 + "s", result.classid)+
	                		        			String.format("%-"+ 10 + "s", result.dept_code)+
	                		        			String.format("%-"+ 10 + "s", result.course_no)+
	                		        			String.format("%-"+ 10 + "s", result.sect_no)+
	                		        			String.format("%-"+ 10 + "s", result.year)+
	                		        			String.format("%-"+ 10 + "s", result.semester)+
	                		        			String.format("%-"+ 10 + "s", result.limit)+
	                		        			String.format("%-"+ 10 + "s", result.class_size)
	                		        			);}
	                		        System.out.println("-----------------------------------------------------------------------\n");
                			       
                		 		break;
                		 	case "5":
                		 		ArrayList<Enrollments> EnrollmentsList = new ArrayList<Enrollments>();
                		        EnrollmentsList = sql.getAllEnrollments();
                		       
                		        System.out.println(
                		        		String.format("%-"+ 10 + "s", "sid")+
            		        			String.format("%-"+ 10 + "s", "classid")+
            		        			String.format("%-"+ 10 + "s", "course_no")+
            		        			String.format("%-"+ 10 + "s", "lgrade")
                		        		);
                		        System.out.println("-----------------------------------------------");
                		        for(Enrollments result : EnrollmentsList){
                		        	System.out.println(
                		        			String.format("%-"+ 10 + "s", result.sid)+
                		        			String.format("%-"+ 10 + "s", result.classid)+
                		        			String.format("%-"+ 10 + "s", result.lgrade)
                		        			);}
                		        System.out.println("------------------------------------------------\n");                		        
                		        
                		 		break;
                		 	case "6":
                		 		ArrayList<Logs> LogsList = new ArrayList<Logs>();
                		        LogsList = sql.getAllLogs();
                		       
                		        System.out.println(
                		        		String.format("%-"+ 12 + "s", "logid")+
            		        			String.format("%-"+ 12 + "s", "who")+
            		        			String.format("%-"+ 22 + "s", "time")+
            		        			String.format("%-"+ 16 + "s", "table_name")+
            		        			String.format("%-"+ 12 + "s", "operation")+
            		        			String.format("%-"+ 12 + "s", "key_value")
                		        		);
                		        System.out.println("----------------------------------------------------------------------------------");
                		        for(Logs result : LogsList){
                		        	System.out.println(
                		        			String.format("%-"+ 12 + "s", result.logid)+
                		        			String.format("%-"+ 12 + "s", result.who)+
                		        			String.format("%-"+ 22 + "s", result.time)+
                		        			String.format("%-"+ 16 + "s", result.table_name)+
                		        			String.format("%-"+ 12 + "s", result.operation)+
                		        			String.format("%-"+ 12 + "s", result.key_value)
                		        			);}
                		        System.out.println("----------------------------------------------------------------------------------\n");
            			          
                		 		break;
                		 	case "7":
                		 		loop2 = false;
                		 		break;
                                
                		 	}
                	 }
                     break;
                     
                 case "2":
                	 Students std = new Students();
                	 System.out.println("Enter student details");
                	
         	         std.sid = null;
         	         
         	        System.out.println("firstname: ");
                	 String firstname = in.nextLine();
                	 std.firstname = firstname;
                	 
                	 System.out.println("lastname: ");
                	 String lastname = in.nextLine();
                	 std.lastname = lastname;
                	 
                	 
                	 
                	 while(true){
                		 
                		 System.out.println("status: ");
                    	 String status = in.nextLine();
                		 
                	 if(status == "freshman" || status == "sophomore" || status == "junior" || status == "senior" || status == "graduate")
                	 {
                	 std.status = status;
                	 break;
                	 }
                	 else{
                		 System.out.println("Enter valid status.");
                	 }
                	 }
                	 
                	 while(true){
                	 System.out.println("gpa: ");
                	 String gpa = in.nextLine();
                	 try{
         	        	 if((Integer.parseInt(gpa)<=4) && (Integer.parseInt(gpa)>0)){
         	        		std.gpa = Integer.parseInt(gpa);
         	        		break;
         	        	 }
         	        	 else{
         	        		System.out.println("Enter only Number between 1 and 4");
         	        	 }
         	         
         	         }
         	         catch(Exception e)
         	         {System.out.println("Enter only Number between 1 and 4"); }
                	 
                	 }
                	 
                	 while(true){
                	 System.out.println("email: ");
                	 String email = in.nextLine();
         	         if(email != ""){
         	         std.email = email;
         	         break;}
         	         else{
         	        	System.out.println("Enter valid email.");
         	         }
                	 }
         	         std.sid = sql.insertStudent(std);
         	         System.out.println("The new B number is: " + std.sid);         	        
         	        break;
                	 
                 case "3":
                	 	System.out.println("sid: ");
                	 	String sid = in.nextLine();
                	 	System.out.println(sql.deleteStudent(sid));
                     break;
                     
                 case "4":
                	 System.out.println("sid: ");
             	 	String sid4 = in.nextLine();
             	 	System.out.println("classid: ");
             	 	String classid4 = in.nextLine();
             	 	String result4 = sql.enrollStudent(sid4, classid4);
                	 System.out.println(result4);
                	 break;
                	 
                 case "5":
                	 System.out.println("sid: ");
              	 	String sid5 = in.nextLine();
              	 	System.out.println("classid: ");
              	 	String classid5 = in.nextLine();
              	 	String result5 = sql.dropEnrollment(sid5, classid5);
                 	 System.out.println(result5);
                     break;
                     
                 case "6":
                	 
                	 System.out.println("sid: ");
               	 	String sid6 = in.nextLine();
               	 	
                	  StudentAndClasses stclResult = new StudentAndClasses();
          	        
          	        stclResult = sql.getStudentAndClasses(sid6);
          	        
          	        if(stclResult.q_status == 1){
          	        	
          	        	System.out.println(
        		        		String.format("%-"+ 10 + "s", "sid")+
    		        			String.format("%-"+ 10 + "s", "firstname")+
    		        			String.format("%-"+ 10 + "s", "lastname")+
    		        			String.format("%-"+ 10 + "s", "status")+
    		        			String.format("%-"+ 10 + "s", "gpa")+
    		        			String.format("%-"+ 10 + "s", "email")
        		        		);
          	        	
          	        	System.out.println("------------------------------------------------------------------------------------");
          	        	
          	        	System.out.println(
    		        			String.format("%-"+ 10 + "s", stclResult.student.sid)+
    		        			String.format("%-"+ 10 + "s", stclResult.student.firstname)+
    		        			String.format("%-"+ 10 + "s", stclResult.student.lastname)+
    		        			String.format("%-"+ 10 + "s", stclResult.student.status)+
    		        			String.format("%-"+ 10 + "s", stclResult.student.gpa)+
    		        			String.format("%-"+ 10 + "s", stclResult.student.email)
    		        			);
          	        	
          	        	System.out.println("------------------------------------------------------------------------------------");
          	        	
          		        
          		        for(Classes result : stclResult.classes){
          		        	
          		        	System.out.println(
            		        		String.format("%-"+ 10 + "s", "classid")+
        		        			String.format("%-"+ 10 + "s", "dept_code")+
        		        			String.format("%-"+ 10 + "s", "course_no")+
        		        			String.format("%-"+ 10 + "s", "sect_no")+
        		        			String.format("%-"+ 10 + "s", "year")+
        		        			String.format("%-"+ 10 + "s", "semester")+
        		        			String.format("%-"+ 10 + "s", "limit")+
        		        			String.format("%-"+ 10 + "s", "class_size")
            		        		);
          		        	System.out.println("------------------------------------------------------------------------------------");
          		        	System.out.println(
        		        			String.format("%-"+ 10 + "s", result.classid)+
        		        			String.format("%-"+ 10 + "s", result.dept_code)+
        		        			String.format("%-"+ 10 + "s", result.course_no)+
        		        			String.format("%-"+ 10 + "s", result.sect_no)+
        		        			String.format("%-"+ 10 + "s", result.year)+
        		        			String.format("%-"+ 10 + "s", result.semester)+
        		        			String.format("%-"+ 10 + "s", result.limit)+
        		        			String.format("%-"+ 10 + "s", result.class_size)
        		        			);
          		        	System.out.println("------------------------------------------------------------------------------------");
          		        	
          		        }
          	        }
          	        else{System.out.println("Student not found");}
                	 
                 case "7":
                	 
                	 System.out.println("class_Id: ");
                	 	String class_Id6 = in.nextLine();
                	  
                	 ClassAndStudents clstResult = new ClassAndStudents();
         	        
         	        clstResult = sql.getClassAndStudents(class_Id6);
         	        
         		        if(clstResult.q_status == 1){
         		        	
         		        	System.out.println(
            		        		String.format("%-"+ 10 + "s", "classid")+
        		        			String.format("%-"+ 10 + "s", "dept_code")+
        		        			String.format("%-"+ 10 + "s", "course_no")+
        		        			String.format("%-"+ 10 + "s", "sect_no")+
        		        			String.format("%-"+ 10 + "s", "year")+
        		        			String.format("%-"+ 10 + "s", "semester")+
        		        			String.format("%-"+ 10 + "s", "limit")+
        		        			String.format("%-"+ 10 + "s", "class_size")
            		        		);
          		        	System.out.println("------------------------------------------------------------------------------------");
          		        	System.out.println(
        		        			String.format("%-"+ 10 + "s", clstResult.classInfo.classid)+
        		        			String.format("%-"+ 10 + "s", clstResult.classInfo.dept_code)+
        		        			String.format("%-"+ 10 + "s", clstResult.classInfo.course_no)+
        		        			String.format("%-"+ 10 + "s", clstResult.classInfo.sect_no)+
        		        			String.format("%-"+ 10 + "s", clstResult.classInfo.year)+
        		        			String.format("%-"+ 10 + "s", clstResult.classInfo.semester)+
        		        			String.format("%-"+ 10 + "s", clstResult.classInfo.limit)+
        		        			String.format("%-"+ 10 + "s", clstResult.classInfo.class_size)
        		        			);
          		        	System.out.println("------------------------------------------------------------------------------------");
         		        	
         		        
         		        for(Students result : clstResult.students){
         		        	
         		        	System.out.println(
            		        		String.format("%-"+ 10 + "s", "sid")+
        		        			String.format("%-"+ 10 + "s", "firstname")+
        		        			String.format("%-"+ 10 + "s", "lastname")+
        		        			String.format("%-"+ 10 + "s", "status")+
        		        			String.format("%-"+ 10 + "s", "gpa")+
        		        			String.format("%-"+ 10 + "s", "email")
            		        		);
              	        	
              	        	System.out.println("------------------------------------------------------------------------------------");
              	        	
              	        	System.out.println(
        		        			String.format("%-"+ 10 + "s", result.sid)+
        		        			String.format("%-"+ 10 + "s", result.firstname)+
        		        			String.format("%-"+ 10 + "s", result.lastname)+
        		        			String.format("%-"+ 10 + "s", result.status)+
        		        			String.format("%-"+ 10 + "s", result.gpa)+
        		        			String.format("%-"+ 10 + "s", result.email)
        		        			);
              	        	
              	        	System.out.println("------------------------------------------------------------------------------------");
         		        	         		        	
         		        } 
         		       }
              	        else{System.out.println("Classes not found");}
                	 
                	 break;
                
                 case "8":
                	 ArrayList<Prerequisites> resultList = new ArrayList<Prerequisites>();
         	        
                	 System.out.println("dept code: ");
             	 	String dept_code = in.nextLine();
             	 	
             	 	 
                	
              	 	while(true)
              	 	{
              	 		System.out.println("course no: ");
                  	 	int course_no = 0;
              	 	try{
              	 		course_no = Integer.parseInt(in.nextLine());
              	 		break;
              	 		
              	 	}catch(Exception e){System.out.println("please enter valid course no");}
              	 	}
         	        resultList = sql.getPrerequisites(dept_code, course_no);
         	        
         	        if(resultList != null)
         	        {
         	        	for(Prerequisites preReq : resultList){
         		        	System.out.println(preReq.course_code);
         		        }
         	        }
                	 break;
				
                 case "9":
                	 System.out.println("Student Management System Exited");
                	 loop1 = false;
                	 break;
				
			}
		}
	        
	        
	        
	        
	        
	        
	        	        
	        //-----------------------------------------
	        //--2.
	       /*ArrayList<Students> resultList = new ArrayList<Students>();
	        resultList = sql.getAllStudents();
	       
	        for(Students result : resultList){
	        	System.out.println(result.sid);}
	        
	        ArrayList<Courses> CoursesList = new ArrayList<Courses>();
	        CoursesList = sql.getAllCourses();
	       
	        for(Courses result : CoursesList){
	        	System.out.println(result.dept_code);}
	        
	        ArrayList<Prerequisites> PrerequisitesList = new ArrayList<Prerequisites>();
	        PrerequisitesList = sql.getAllPrerequisites();
	       
	        for(Prerequisites result : PrerequisitesList){
	        	System.out.println(result.pre_dept_code);}
	        
	        ArrayList<Classes> ClassesList = new ArrayList<Classes>();
	        ClassesList = sql.getAllClasses();
	       
	        for(Classes result : ClassesList){
	        	System.out.println(result.classid);}
	        
	        ArrayList<Enrollments> EnrollmentsList = new ArrayList<Enrollments>();
	        EnrollmentsList = sql.getAllEnrollments();
	       
	        for(Enrollments result : EnrollmentsList){
	        	System.out.println(result.sid);}
	        
	        ArrayList<Logs> LogsList = new ArrayList<Logs>();
	        LogsList = sql.getAllLogs();
	       
	        for(Logs result : LogsList){
	        	System.out.println(result.logid);}*/
	        
	        
	        //------------------------------------------
	        //--3.
	        /*Students std = new Students();
	        std.sid = null;
	        std.firstname = "hamL";
	        std.lastname = "baKl";
	        std.status = "freshman";
	        std.gpa = 3.5;
	        std.email = "baKl@bu.edu";
	        
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
	        
	        /*readKeyBoard = new BufferedReader(new InputStreamReader(System.in)); 
	         String class_Id;
	        System.out.print("Please Enter Class Id:");
	         class_Id = readKeyBoard.readLine();	        
	        System.out.println("Class Id is " + class_Id);
	        
	        ClassAndStudents clstResult = new ClassAndStudents();
	        
	        clstResult = sql.getClassAndStudents(class_Id);
	        
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
	        
	      //9  
	        //System.out.println(sql.deleteStudent("B001"));
	        
	    }	  
	  catch(Exception e) {System.out.println ("\n*** other Exception caught ***\n" + e.getMessage());}
	}
	 
	 
}
