
CREATE OR REPLACE PROCEDURE enroll_Student(
  st_sid IN STUDENTS.SID%TYPE,
  st_classid IN CLASSES.CLASSID%TYPE,
  status_log OUT VARCHAR2
) IS 
isValid number;
BEGIN  
 
--If the student is not in the students table, report “The sid is invalid".
isValid := 0;
isValid := fn_Validate_Student(st_sid); 
if(isValid = 0)then
  status_log := 'failed-'||'The sid is invalid.';   
End if;

--If the class is not in the classes table, report “The classid is invalid".
if isValid = 1 then
  isValid := 0;
  isValid := fn_Validate_Classes(st_classid); 
  if(isValid = 0)then
    status_log := 'failed-'||'The classid is invalid.'; 
  End if;
End if;

--If the enrollment of the student into a class would cause “class_size > limit”, reject the enrollment and report “The class is closed".
if isValid = 1 then
  isValid := 0;
  isValid := fn_Validate_Class_Limit(st_classid); 
  if(isValid = 0)then
    status_log := 'failed-'||'The class is closed.'; 
  End if;
End if;


--If the student is already in the class, report “The student is already in the class".
if isValid = 1 then
  isValid := 0;
  isValid := fn_Student_In_Class(st_sid, st_classid); 
  if(isValid = 0)then
    status_log := 'failed-'||'The student is already in the class.'; 
  End if;
End if;

--If the student is already enrolled in two other classes in the same semester and the same year, 
--report “You are overloaded.” and allow the student to be enrolled.
if isValid = 1 then
  isValid := 0;
  isValid := fn_Student_Overloaded(st_sid, st_classid); 
  if(isValid = 0)then
    status_log := 'You are overloaded.';
    isValid := 1;
  End if;
End if;

--If the student is already enrolled in three other classes in the same semester and the same year, report “Students cannot be
--enrolled in more than three classes in the same semester.” and reject the enrollment.
if isValid = 1 then
  isValid := 0;
  isValid := fn_Student_Saturated(st_sid, st_classid); 
  if(isValid = 0)then
    status_log := 'failed-'||'Students cannot be enrolled in more than three classes in the same semester.';
  End if;
End if;

--If the student has not completed the required prerequisite courses with minimum grade “D”, reject the enrollment
--and report “Prerequisite courses have not been completed."
if isValid = 1 then
  isValid := 0;
  isValid := fn_prerequisite_Enroll(st_sid, st_classid); 
  if(isValid = 0)then
    status_log := 'failed-'||'Prerequisite courses have not been completed.';
  End if;
End if;

--For all the other cases, the requested enrollment should be performed.
if isValid = 1 then
  insert into enrollments values  (st_sid, st_classid, null);
  status_log := 'success-'||'The requested enrollment is done.-' || status_log;
End if;

--You should make sure that all data are consistent after each enrollment. 
--For example, after you successfully enrolled a student into a class, the size of the corresponding class should be updated accordingly.
--Use trigger(s) to implement the updates of values caused by successfully enrolling a student into a class. It is recommended that all triggers for
--this project be implemented outside of the package.
     
END;
 /
 