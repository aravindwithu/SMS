CREATE OR REPLACE PROCEDURE getPrerequisiteCourses(
   cr_dept_code IN COURSES.DEPT_CODE%TYPE, 
   cr_course_no IN COURSES.COURSE_NO%TYPE, 
   PrerequisiteData OUT sys_refcursor
) IS 
loopIt number;
BEGIN 
 INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)  
   (SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
   WHERE (dept_code || course_no) in (cr_dept_code || cr_course_no));   
 loopIt := 0;
WHILE loopIt = 0 
LOOP  
   INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)   
   SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
   WHERE (dept_code || course_no) in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);   
   update tempPrerequisites set isChecked = 1 where preReqCourse_Code in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);   
   loopIt := fn_Chk_LoopIt();   
END LOOP; 
 open PrerequisiteData for 
 select * from tempPrerequisites order by Course_Count asc;
 delete from tempPrerequisites;     
END;
 /