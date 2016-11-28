create or replace FUNCTION fn_Class_Has_Students
  (cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number
IS
isValid number;
BEGIN
    select 0 INTO isValid from enrollments en where en.classid = cl_classid;      
    return isValid;
  exception
        when no_data_found then return 1;
END;