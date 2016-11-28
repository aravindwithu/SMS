CREATE OR REPLACE FUNCTION fn_prerequisite_Enroll(
   st_sid IN STUDENTS.SID%TYPE,
   cl_classid IN CLASSES.CLASSID%TYPE
) RETURN number 
IS
Course_Code varchar2(12);
loopIt number;
isValid number;
BEGIN  
 
  select dept_code || course_no INTO Course_Code from classes where classid = cl_classid;
 
 INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)  
   (SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
   WHERE (dept_code || course_no) in (Course_Code));   
 loopIt := 0;
WHILE loopIt = 0 
LOOP  
   INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)   
   SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
   WHERE (dept_code || course_no) in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);   
   update tempPrerequisites set isChecked = 1 where preReqCourse_Code in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);   
   loopIt := fn_Chk_LoopIt();   
END LOOP; 

 select 0 INTO isValid 
 from tempPrerequisites tp join classes cl on tp.preReqCourse_Code = (cl.dept_code||cl.course_no) 
   join enrollments en on cl.classid  = en.classid
 where en.sid = st_sid and en.classid = cl_classid;
 
  return isValid;
 
 delete from tempPrerequisites;  
 
  exception
        when no_data_found then return 1;
         delete from tempPrerequisites; 
     
END;
 /