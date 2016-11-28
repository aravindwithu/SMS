set serveroutput on
declare
   std_firstname students.FIRSTNAME%TYPE;
   std_lastname STUDENTS.LASTNAME%TYPE;
   std_status STUDENTS.STATUS%TYPE;
   std_gpa STUDENTS.GPA%TYPE;
   std_email STUDENTS.EMAIL%TYPE;
   v_sid students.sid%TYPE;
Begin
std_firstname := 'Nar';
std_lastname := 'zike';
std_status := 'freshman';
std_gpa := 3;
std_email := 'zike@bu.edu';
v_sid := null;
insertStudents(v_sid,std_firstname, std_lastname, std_status, std_gpa, std_email);
dbms_output.put_line(v_sid);
End;
/

set serveroutput on
declare
   std_lastname STUDENTS.LASTNAME%TYPE;
   std_status STUDENTS.STATUS%TYPE;
   v_sid students.sid%TYPE;
   status boolean;   
   class_results sys_refcursor;
Begin
std_lastname := null;
std_status := null;
class_results := null;
v_sid := 'B001';
status := 0;
getStudent(v_sid, std_lastname, std_status, class_results, status);
if (status = 1) then
dbms_output.put_line(v_sid || ',' || std_lastname || ',' || std_status);
else
dbms_output.put_line('student not found');
end if;
End;
/

var cr refcursor;
open cr for
select cl.classid, cr.dept_code, cr.course_no, cr.title, cl.year, cl.semester from Enrollments en join Classes cl on en.classid = cl.classid 
         join Courses cr on cl.dept_code = cr.dept_code and cl.course_no = cr.course_no
         where en.sid = 'B001';

print cr;


var rc1 refcursor
execute showStudents(:rc1);

var rc2 refcursor
execute showCourses(:rc2);

var rc3 refcursor
execute showPrerequisites(:rc3);

var rc4 refcursor
execute showClasses(:rc4);

var rc5 refcursor
execute showEnrollments(:rc5);

var rc6 refcursor
execute showLogs(:rc6);

print rc1;
print rc2;
print rc3;
print rc4;
print rc5;
print rc6;

select * from students;


