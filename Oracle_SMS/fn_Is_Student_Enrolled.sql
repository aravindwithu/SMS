create or replace FUNCTION fn_Is_Student_Enrolled
  (st_sid IN STUDENTS.SID%TYPE)
   RETURN number
IS
isValid number;
BEGIN
    select 0 INTO isValid from enrollments en where en.sid = st_sid;      
    return isValid;
  exception
        when no_data_found then return 1;
END;