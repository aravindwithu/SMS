CREATE OR REPLACE PROCEDURE deleteStudents(
   v_sid IN STUDENTS.SID%TYPE,
   status_log OUT VARCHAR2
) IS 
isValid number;
BEGIN  
  --If the student is not in the students table, report “The sid is invalid".
 isValid := 0;
 isValid := fn_Validate_Student(v_sid); 
 if(isValid = 0)then
    status_log := 'failed-'||'The classid is invalid.'; 
  End if;

 if(isValid = 1) then
    delete from students where sid = v_sid;
    status_log := 'Success-'||'The student is deleted succesfully.';
  End if;
END;
 /