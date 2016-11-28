create or replace FUNCTION fn_Validate_Classes
  (cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number
IS
isValid number;
BEGIN
    select 1 INTO isValid from classes where classid = cl_classid;      
    return isValid;
  exception
        when no_data_found then return 0;
END;
