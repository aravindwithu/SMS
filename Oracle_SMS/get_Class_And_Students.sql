 CREATE OR REPLACE PROCEDURE get_Class_And_Students(
   v_classid IN CLASSES.CLASSID%TYPE,
   cr_title OUT COURSES.TITLE%TYPE, 
   cl_semester OUT CLASSES.SEMESTER%TYPE, 
   cl_year OUT CLASSES.YEAR%TYPE,
   studentsIn OUT sys_refcursor,
   q_status OUT number
) IS 
BEGIN    
 
 select cr.title, cl.semester, cl.year INTO cr_title, cl_semester, cl_year 
 from Classes cl join Courses cr on cl.dept_code = cr.dept_code and cl.course_no = cr.course_no where cl.CLASSID = v_classid ;
 q_status := 1;
 
  open studentsIn for        
       select st.sid, st.lastname
          from students st join Enrollments en on st.sid in en.sid join Classes cl on en.classid = cl.classid 
         join Courses cr on cl.dept_code = cr.dept_code and cl.course_no = cr.course_no
         where cl.classid = v_classid; 
  
  exception
        when no_data_found then q_status := 0;
       
END;
 /