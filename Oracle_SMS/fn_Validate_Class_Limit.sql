create or replace FUNCTION fn_Validate_Class_Limit
  (cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number
IS
isValid number;
BEGIN
    select 1 INTO isValid from classes cl where cl.classid = cl_classid and cl.class_size <= cl.limit;      
    return isValid;
  exception
        when no_data_found then return 0;
END;