CREATE OR REPLACE FUNCTION fn_prerequisite_Enroll(
   st_sid IN STUDENTS.SID%TYPE,
   cl_classid IN CLASSES.CLASSID%TYPE
) RETURN number 
IS 
Course_Code varchar2(12);
preCourse_Code varchar2(12);
tempCount number;
isValid number;
BEGIN  
 select dept_code || course_no INTO Course_Code from classes where classid = cl_classid;
 preCourse_Code := 'INIT';
 tempCount := 0;
WHILE preCourse_Code != 'NA'
LOOP  
   preCourse_Code := fn_Get_PreRequisite(Course_Code); 
   tempCount := tempCount+1;
   insert into tempPrerequisites values (tempCount, Course_Code, preCourse_Code);
   Course_Code := preCourse_Code;
END LOOP;
  
 select 0 INTO isValid 
 from tempPrerequisites tp join classes cl on tp.Course_Count = (cl.dept_code||cl.course_no) 
   join enrollments en on cl.classid  = en.classid
 where tp.Course_Count !=1 and en.lgrade >= 'D'
 order by tp.Course_Count asc; 
 
 delete from tempPrerequisites; 
 
 exception
        when no_data_found then return 1;
         delete from tempPrerequisites; 
     
END;
 /