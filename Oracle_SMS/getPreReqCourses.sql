CREATE OR REPLACE PROCEDURE getPrereqCourses(prc OUT sys_refcursor) 
IS 
BEGIN 
 open prc for select * from tempPrerequisites;    
END;
 /
 
 select *  from prerequisites;