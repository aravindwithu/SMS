CREATE OR REPLACE PROCEDURE initPreReqCourses(
   cr_dept_code IN COURSES.DEPT_CODE%TYPE, 
   cr_course_no IN COURSES.COURSE_NO%TYPE
) IS
Course_Code Varchar2(12);
preCourse_Code Varchar2(12);
BEGIN  

Course_Code := 'cs' || 432;

select * from prerequisites

   SELECT  pre_dept_code || pre_course_no  FROM prerequisites 
   WHERE (dept_code || course_no) in ('CS432');


 INSERT INTO tempPrerequisites values (1, preCourse_Code, 0);   

     
END;
 /
 
 
 