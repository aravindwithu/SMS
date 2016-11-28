--If the student is already enrolled in two other classes in the same semester and the same year, 
--report “You are overloaded.” and allow the student to be enrolled.
create or replace FUNCTION fn_Student_Overloaded
  (st_sid IN STUDENTS.SID%TYPE,
  cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number
IS
isValid number;
BEGIN
    select 0 INTO isValid from classes 
    where classid in (select classid from enrollments where sid = st_sid) 
    and semester in (select semester from classes where classid = cl_classid) 
    and year in(select year from classes where classid = cl_classid)
    group by  semester, year having Count(*)>=2;      
    return isValid;   
    
  exception
        when no_data_found then return 1;
END;