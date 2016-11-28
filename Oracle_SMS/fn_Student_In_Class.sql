create or replace FUNCTION fn_Student_In_Class
  (st_sid IN STUDENTS.SID%TYPE,
  cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number
IS
isValid number;
BEGIN
    select 0 INTO isValid from enrollments en where en.sid = st_sid and en.classid = cl_classid;      
    return isValid;
  exception
        when no_data_found then return 1;
END;