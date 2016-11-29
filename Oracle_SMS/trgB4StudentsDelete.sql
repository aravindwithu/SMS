
CREATE OR REPLACE TRIGGER trgB4StudentsDelete
AFTER DELETE
   ON students
    REFERENCING NEW AS new OLD AS old
   FOR EACH ROW 
DECLARE v_username varchar2(10); isValid number;
BEGIN  

 isValid := 0;
      isValid := SMSPack.fn_Is_Student_Enrolled(:old.sid);
      if(isValid = 0) then
         delete from enrollments where sid = :old.sid;     
      End if;
     
END;
 /
 
  --delete from students where sid = 'B009';
 
 --insert into students values  insert into students values ('B001', 'Anne', 'Broder', 'junior', 3.17, 'broder@bu.edu');

 --select * from students;
 
 --select * from classes;
 
 --delete from enrollments where sid = 'B009' and classid = 'c0005';
 
 --insert into enrollments values  ('B009', 'c0005', 'A');

 --select * from enrollments;
 
 --select * from logs;

 