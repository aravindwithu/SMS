create or replace procedure show_Students (prc out sys_refcursor)
   is
    begin
       open prc for select * from students;
    end;
    /
    
    create or replace procedure show_Courses (prc out sys_refcursor)
   is
    begin
       open prc for select * from courses;
    end;
    /
    
    create or replace procedure show_Prerequisites (prc out sys_refcursor)
   is
    begin
       open prc for select * from prerequisites;
    end;
    /

 create or replace procedure show_Classes (prc out sys_refcursor)
   is
    begin
       open prc for select * from classes;
    end;
    /
    
     create or replace procedure show_Enrollments (prc out sys_refcursor)
   is
    begin
       open prc for select * from enrollments;
    end;
    /
    
    create or replace procedure show_Logs (prc out sys_refcursor)
   is
    begin
       open prc for select * from logs;
    end;
    /
    