CREATE OR REPLACE TRIGGER trgAftEnrollmentsInsert
AFTER INSERT
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
     'Insert',
     (:new.sid || '-' ||:new.classid));
     
  --Update class table
  update classes set class_size = class_size + 1 where classid = :new.classid;
     
END;
 /
 
-- select * from enrollments;
 
-- select * from classes;

-- insert into enrollments values  ('B010', 'c0005', 'A');
 
-- select * from logs
 
 