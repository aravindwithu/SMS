CREATE OR REPLACE PROCEDURE insert_Students(
   v_sid out CHAR,
   std_firstname IN STUDENTS.FIRSTNAME%TYPE, 
   std_lastname IN STUDENTS.LASTNAME%TYPE, 
   std_status IN STUDENTS.STATUS%TYPE,
   std_gpa IN STUDENTS.GPA%TYPE,
   std_email IN STUDENTS.EMAIL%TYPE
   
) IS 
newSid students.sid%TYPE;
BEGIN 
       v_sid := null;       
       select 'B' || LPAD((max(rownum)+1), 3, '0') as sid INTO newSid from students ;
       insert into students values (newSid, std_firstname, std_lastname, std_status, std_gpa, std_email); 
       v_sid := newSid;
END;
 /