
CREATE OR REPLACE TRIGGER trgAftStudentsDelete
AFTER DELETE
   ON students
    REFERENCING NEW AS new OLD AS old
   FOR EACH ROW 
DECLARE v_username varchar2(10);
BEGIN  
  SELECT user INTO v_username FROM dual;
   -- Insert record into log table
   INSERT INTO logs
   ( logid,
     who,
     time,
     table_name,
     operation,
     key_value )
   VALUES
   ( logid.nextval,
     v_username,
     sysdate,
     'Students',
     'Delete',
     :old.sid);
     
END;
 /
 
 
 --delete from students where sid = 'B009';
 
 --insert into students values  insert into students values ('B001', 'Anne', 'Broder', 'junior', 3.17, 'broder@bu.edu');

 --select * from students;
 
 --select * from logs;

 