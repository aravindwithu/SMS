set serveroutput on
declare
  st_sid STUDENTS.SID%TYPE;
  st_classid CLASSES.CLASSID%TYPE;
  status_log varchar2(100);
Begin
st_sid := 'B009';
st_classid := 'c0001';
status_log := 'nothing';
enroll_Student(st_sid,st_classid,status_log);
dbms_output.put_line(status_log);
End;
/
