
CREATE OR REPLACE PROCEDURE drop_Enrollment(
  st_sid IN STUDENTS.SID%TYPE,
  st_classid IN CLASSES.CLASSID%TYPE,
  status_log OUT VARCHAR2
) IS 
isValid number;
BEGIN  
 
--If the student is not in the students table, report “The sid is invalid.” 
isValid := 0;
isValid := fn_Validate_Student(st_sid); 
if(isValid = 0)then
  status_log := 'failed-'||'The sid is invalid.';   
End if;

--If the classid is not in the classes table, report “The classid is invalid.” 
if isValid = 1 then
  isValid := 0;
  isValid := fn_Validate_Classes(st_classid); 
  if(isValid = 0)then
    status_log := 'failed-'||'The classid is invalid.'; 
  End if;
End if;

--If the student is not enrolled in the class, report “The student is not enrolled in the class.”
if isValid = 1 then
  isValid := 0;
  isValid := fn_Student_In_Class(st_sid, st_classid); 
  if(isValid = 1)then
    status_log := 'failed-'||'The student is not enrolled in the class.'; 
    isValid := 0;
    else
    isValid := 1;
  End if;
End if;

--If dropping the student from the class would cause a violation of the prerequisite requirement for another class, 
--then reject the drop attempt and report “The drop is not permitted because another class uses it as a prerequisite.” 
if isValid = 1 then
  isValid := 0;
  isValid := fn_prerequisite_Check(st_sid, st_classid); 
  if(isValid = 0)then
    status_log := 'failed-'||'Prerequisite courses have not been completed.';
  End if;
End if;

--In all the other cases, the student should be dropped from the class.
if isValid = 1 then
  delete from enrollments where sid = st_sid and classid = st_classid;
  status_log := 'success-'||'The student is droped from the class.';
End if;

--If the class is the last class for the student, report “This student is not enrolled in any classes.” 
if isValid = 1 then
  isValid := 0;
  isValid := fn_Is_Student_Enrolled(st_sid);
  if(isValid = 1) then
  status_log := status_log || 'msg-'||'This student is not enrolled in any classes.';
   else
    isValid := 1;
  End if;
End if;

--If the student is the last student in the class, report “The class now has no students.” 
if isValid = 1 then
  isValid := 0;
  isValid := fn_Class_Has_Students(st_classid);
  if(isValid = 1) then
  status_log := status_log || 'msg-'||'The class now has no students.';
  End if;
End if;

--Again, you should make sure that all data are consistent after the drop and all updates caused by the drop should be implemented using trigger(s).

END;
 /
 