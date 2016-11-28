set serveroutput on
declare
   std_lastname STUDENTS.LASTNAME%TYPE;
   std_status STUDENTS.STATUS%TYPE;
   v_sid students.sid%TYPE;
   status number;   
   class_results sys_refcursor;
Begin
std_lastname := null;
std_status := null;
class_results := null;
v_sid := 'B001';
status := 0;
getStudentAndClasses(v_sid, std_lastname, std_status, class_results, status);
if status = 1 then
dbms_output.put_line(v_sid || ',' || std_lastname || ',' || std_status);
else
dbms_output.put_line('classes not found');
end if;
End;
/


