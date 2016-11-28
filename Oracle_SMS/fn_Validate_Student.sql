create or replace FUNCTION fn_Validate_Student
  (st_sid IN STUDENTS.SID%TYPE)
   RETURN number
IS
isValid number;
BEGIN
    select 1 INTO isValid from students where sid = st_sid;      
    return isValid;
  exception
        when no_data_found then return 0;
END;
