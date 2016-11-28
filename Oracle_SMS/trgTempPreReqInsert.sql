CREATE OR REPLACE TRIGGER trgTempPreReqInsert
AFTER INSERT
   ON tempPrerequisites
   FOR EACH ROW 
DECLARE loopIt number;
BEGIN  
 
 loopIt := 0;

WHILE loopIt = 0 
LOOP  

   INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)   
   SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
   WHERE (dept_code || course_no) in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);
   
   update tempPrerequisites set isChecked = 1 where preReqCourse_Code in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);
   
   loopIt := fn_Chk_LoopIt();
   
   loopIt := 1;
   
END LOOP;
     
END;
 /

drop TRIGGER trgTempPreReqInsert; 