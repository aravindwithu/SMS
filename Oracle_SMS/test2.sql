set serveroutput on
declare
   v_classid CLASSES.CLASSID%TYPE;
   cr_title COURSES.TITLE%TYPE;
   cl_semester CLASSES.SEMESTER%TYPE; 
   cl_year CLASSES.YEAR%TYPE;
   studentsIn sys_refcursor;
   q_status number;
Begin
v_classid := 'c0001';
cr_title := null;
cl_semester := null;
cl_year := 0;
studentsIn := null;
q_status := 0;
getClassAndStudents(v_classid, cr_title, cl_semester, cl_year, studentsIn, q_status);

if q_status = 1 then
dbms_output.put_line(v_classid || ',' || cr_title || ',' || cl_semester);
else
dbms_output.put_line('student not found');
end if;

End;
/