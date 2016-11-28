 CREATE OR REPLACE PROCEDURE getStudentAndClasses(
   v_sid IN students.sid%TYPE,
   std_lastname OUT STUDENTS.LASTNAME%TYPE, 
   std_status OUT STUDENTS.STATUS%TYPE,  
   classes_Taken OUT sys_refcursor,
   status OUT number
) IS   
   
BEGIN          
       select lastname, status INTO std_lastname, std_status from students where sid = v_sid ;
       
       open classes_Taken for select cl.classid, cr.dept_code || cr.course_no, cr.title, cl.year, cl.semester
          from Enrollments en join Classes cl on en.classid = cl.classid 
         join Courses cr on cl.dept_code = cr.dept_code and cl.course_no = cr.course_no
         where en.sid = v_sid;
         
       status := 1;
    exception
        when no_data_found then status := 0;
END;
 /