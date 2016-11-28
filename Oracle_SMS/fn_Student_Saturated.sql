--If the student is already enrolled in three other classes in the same semester and the same year, report “Students cannot be
--enrolled in more than three classes in the same semester.” and reject the enrollment.
create or replace FUNCTION fn_Student_Saturated
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
    group by  semester, year having Count(*)>=3;      
    return isValid;   
    
  exception
        when no_data_found then return 1;
END;