create or replace FUNCTION fn_Chk_LoopIt
   RETURN number
IS
 loopIt number;
BEGIN
  
 select 0 INTO loopIt from tempPrerequisites where isChecked = 0;
 loopIt := 0;
 return loopIt;

  exception
        when no_data_found then return 1;

END;
/