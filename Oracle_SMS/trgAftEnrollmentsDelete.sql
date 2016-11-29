
CREATE OR REPLACE TRIGGER trgAftEnrollmentsDelete
AFTER DELETE
   ON enrollments
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
     'Enrollments',
     'Delete',
     (:old.sid || ' - ' ||:old.classid));
     
  --Update class table
  update classes set class_size = class_size - 1 where classid = :old.classid;
     
END;
 /
 
 --select * from classes;
 
 --delete from enrollments where sid = 'B009' and classid = 'c0005';
 
 --insert into enrollments values  ('B009', 'c0005', 'A');

 --select * from enrollments;
 
 --select * from logs;

 