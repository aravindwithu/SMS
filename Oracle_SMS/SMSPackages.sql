CREATE OR REPLACE PACKAGE SMSPack IS
   procedure showStudents (prc out sys_refcursor);
   procedure showCourses (prc out sys_refcursor);
   procedure showPrerequisites (prc out sys_refcursor);
   procedure showClasses (prc out sys_refcursor);
   procedure showEnrollments (prc out sys_refcursor);
   procedure showLogs (prc out sys_refcursor);
   
   PROCEDURE insertStudents(
   v_sid out CHAR,
   std_firstname IN STUDENTS.FIRSTNAME%TYPE, 
   std_lastname IN STUDENTS.LASTNAME%TYPE, 
   std_status IN STUDENTS.STATUS%TYPE,
   std_gpa IN STUDENTS.GPA%TYPE,
   std_email IN STUDENTS.EMAIL%TYPE);
   
   PROCEDURE getStudentAndClasses(
   v_sid IN students.sid%TYPE,
   std_lastname OUT STUDENTS.LASTNAME%TYPE, 
   std_status OUT STUDENTS.STATUS%TYPE,  
   classes_Taken OUT sys_refcursor,
   status OUT number);
   
   PROCEDURE getPrerequisiteCourses(
   cr_dept_code IN COURSES.DEPT_CODE%TYPE, 
   cr_course_no IN COURSES.COURSE_NO%TYPE, 
   PrerequisiteData OUT sys_refcursor);

   PROCEDURE getClassAndStudents(
   v_classid IN CLASSES.CLASSID%TYPE,
   cr_title OUT COURSES.TITLE%TYPE, 
   cl_semester OUT CLASSES.SEMESTER%TYPE, 
   cl_year OUT CLASSES.YEAR%TYPE,
   studentsIn OUT sys_refcursor,
   q_status OUT number);
   
   PROCEDURE enrollStudent(
   st_sid IN STUDENTS.SID%TYPE,
   st_classid IN CLASSES.CLASSID%TYPE,
   status_log OUT VARCHAR2);
   
   PROCEDURE dropEnrollment(
   st_sid IN STUDENTS.SID%TYPE,
   st_classid IN CLASSES.CLASSID%TYPE,
   status_log OUT VARCHAR2);
   
   FUNCTION fn_Validate_Student
   (st_sid IN STUDENTS.SID%TYPE)
   RETURN number;
   
  FUNCTION fn_Validate_Classes
  (cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number;
   
  FUNCTION fn_Validate_Class_Limit
  (cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number;
   
  FUNCTION fn_Student_Saturated
  (st_sid IN STUDENTS.SID%TYPE,
  cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number;
   
   FUNCTION fn_Student_Overloaded
  (st_sid IN STUDENTS.SID%TYPE,
  cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number;
   
   FUNCTION fn_Student_In_Class
  (st_sid IN STUDENTS.SID%TYPE,
  cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number;
   
   FUNCTION fn_prerequisite_Enroll(
   st_sid IN STUDENTS.SID%TYPE,
   cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number;
   
  FUNCTION fn_prerequisite_Check(
   st_sid IN STUDENTS.SID%TYPE,
   cl_classid IN CLASSES.CLASSID%TYPE) 
   RETURN number; 
   
   FUNCTION fn_Is_Student_Enrolled
  (st_sid IN STUDENTS.SID%TYPE)
   RETURN number;
   
   FUNCTION fn_Class_Has_Students
  (cl_classid IN CLASSES.CLASSID%TYPE)
   RETURN number;
   
   FUNCTION fn_Chk_LoopIt
   RETURN number;

END;
/

CREATE OR REPLACE PACKAGE BODY SMSPack IS
   procedure showStudents (prc out sys_refcursor)
   is
    begin
       open prc for select * from students;
    end;
    
    procedure showCourses (prc out sys_refcursor)
   is
    begin
       open prc for select * from courses;
    end;
    
    procedure showPrerequisites (prc out sys_refcursor)
   is
    begin
       open prc for select * from prerequisites;
    end;
    
    procedure showClasses (prc out sys_refcursor)
   is
    begin
       open prc for select * from classes;
    end;
    
    procedure showEnrollments (prc out sys_refcursor)
   is
    begin
       open prc for select * from enrollments;
    end;
    
    procedure showLogs (prc out sys_refcursor)
   is
    begin
       open prc for select * from logs;
    end;
    
    PROCEDURE insertStudents(
     v_sid out CHAR,
     std_firstname IN STUDENTS.FIRSTNAME%TYPE, 
     std_lastname IN STUDENTS.LASTNAME%TYPE, 
     std_status IN STUDENTS.STATUS%TYPE,
     std_gpa IN STUDENTS.GPA%TYPE,
     std_email IN STUDENTS.EMAIL%TYPE
   
    ) IS 
    newSid students.sid%TYPE;
    BEGIN 
           v_sid := null;       
           select 'B' || LPAD((max(rownum)+1), 3, '0') as sid INTO newSid from students ;
           insert into students values (newSid, std_firstname, std_lastname, std_status, std_gpa, std_email); 
           v_sid := newSid;
    END;
    
    PROCEDURE getStudentAndClasses(
     v_sid IN students.sid%TYPE,
     std_lastname OUT STUDENTS.LASTNAME%TYPE, 
     std_status OUT STUDENTS.STATUS%TYPE,  
     classes_Taken OUT sys_refcursor,
     status OUT number
    ) IS          
    BEGIN          
           select lastname, status INTO std_lastname, std_status from students where sid = v_sid ;
           
           open classes_Taken for select cl.classid, cr.dept_code || cr.course_no, cr.title, cl.year, cl.semester
              from Enrollments en join Classes cl on en.classid = cl.classid 
             join Courses cr on cl.dept_code = cr.dept_code and cl.course_no = cr.course_no
             where en.sid = v_sid;
             
           status := 1;
        exception
            when no_data_found then status := 0;
    END;
   PROCEDURE getPrerequisiteCourses(
     cr_dept_code IN COURSES.DEPT_CODE%TYPE, 
     cr_course_no IN COURSES.COURSE_NO%TYPE, 
     PrerequisiteData OUT sys_refcursor
    ) IS 
    loopIt number;
    BEGIN 
     INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)  
       (SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
       WHERE (dept_code || course_no) in (cr_dept_code || cr_course_no));   
     loopIt := 0;
    WHILE loopIt = 0 
    LOOP  
       INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)   
       SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
       WHERE (dept_code || course_no) in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);   
       update tempPrerequisites set isChecked = 1 where preReqCourse_Code in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);   
       loopIt := fn_Chk_LoopIt();   
    END LOOP; 
     open PrerequisiteData for 
     select * from tempPrerequisites order by Course_Count asc;
     delete from tempPrerequisites;     
    END;
    
    PROCEDURE getClassAndStudents(
     v_classid IN CLASSES.CLASSID%TYPE,
     cr_title OUT COURSES.TITLE%TYPE, 
     cl_semester OUT CLASSES.SEMESTER%TYPE, 
     cl_year OUT CLASSES.YEAR%TYPE,
     studentsIn OUT sys_refcursor,
     q_status OUT number
    ) IS 
    BEGIN    
     
     select cr.title, cl.semester, cl.year INTO cr_title, cl_semester, cl_year 
     from Classes cl join Courses cr on cl.dept_code = cr.dept_code and cl.course_no = cr.course_no where cl.CLASSID = v_classid ;
     q_status := 1;
     
      open studentsIn for        
           select st.sid, st.lastname
              from students st join Enrollments en on st.sid in en.sid join Classes cl on en.classid = cl.classid 
             join Courses cr on cl.dept_code = cr.dept_code and cl.course_no = cr.course_no
             where cl.classid = v_classid; 
      
      exception
            when no_data_found then q_status := 0;           
    END;
    
    PROCEDURE enrollStudent(
      st_sid IN STUDENTS.SID%TYPE,
      st_classid IN CLASSES.CLASSID%TYPE,
      status_log OUT VARCHAR2
    ) IS 
    isValid number;
    BEGIN  
     
    --If the student is not in the students table, report “The sid is invalid".
    isValid := 0;
    isValid := fn_Validate_Student(st_sid); 
    if(isValid = 0)then
      status_log := 'failed-'||'The sid is invalid.';   
    End if;
    
    --If the class is not in the classes table, report “The classid is invalid".
    if isValid = 1 then
      isValid := 0;
      isValid := fn_Validate_Classes(st_classid); 
      if(isValid = 0)then
        status_log := 'failed-'||'The classid is invalid.'; 
      End if;
    End if;
    
    --If the enrollment of the student into a class would cause “class_size > limit”, reject the enrollment and report “The class is closed".
    if isValid = 1 then
      isValid := 0;
      isValid := fn_Validate_Class_Limit(st_classid); 
      if(isValid = 0)then
        status_log := 'failed-'||'The class is closed.'; 
      End if;
    End if;
    
    
    --If the student is already in the class, report “The student is already in the class".
    if isValid = 1 then
      isValid := 0;
      isValid := fn_Student_In_Class(st_sid, st_classid); 
      if(isValid = 0)then
        status_log := 'failed-'||'The student is already in the class.'; 
      End if;
    End if;
    
    --If the student is already enrolled in two other classes in the same semester and the same year, 
    --report “You are overloaded.” and allow the student to be enrolled.
    if isValid = 1 then
      isValid := 0;
      isValid := fn_Student_Overloaded(st_sid, st_classid); 
      if(isValid = 0)then
        status_log := 'You are overloaded.';
        isValid := 1;
      End if;
    End if;
    
    --If the student is already enrolled in three other classes in the same semester and the same year, report “Students cannot be
    --enrolled in more than three classes in the same semester.” and reject the enrollment.
    if isValid = 1 then
      isValid := 0;
      isValid := fn_Student_Saturated(st_sid, st_classid); 
      if(isValid = 0)then
        status_log := 'failed-'||'Students cannot be enrolled in more than three classes in the same semester.';
      End if;
    End if;
    
    --If the student has not completed the required prerequisite courses with minimum grade “D”, reject the enrollment
    --and report “Prerequisite courses have not been completed."
    if isValid = 1 then
      isValid := 0;
      isValid := fn_prerequisite_Enroll(st_sid, st_classid); 
      if(isValid = 0)then
        status_log := 'failed-'||'Prerequisite courses have not been completed.';
      End if;
    End if;
    
    --For all the other cases, the requested enrollment should be performed.
    if isValid = 1 then
      insert into enrollments values  (st_sid, st_classid, null);
      status_log := 'success-'||'The requested enrollment is done.-' || status_log;
    End if;
    
    --You should make sure that all data are consistent after each enrollment. 
    --For example, after you successfully enrolled a student into a class, the size of the corresponding class should be updated accordingly.
    --Use trigger(s) to implement the updates of values caused by successfully enrolling a student into a class. It is recommended that all triggers for
    --this project be implemented outside of the package.         
    END;
    
  PROCEDURE dropEnrollment(
    st_sid IN STUDENTS.SID%TYPE,
    st_classid IN CLASSES.CLASSID%TYPE,
    status_log OUT VARCHAR2
    ) IS 
    isValid number;
    BEGIN  
     
    --If the student is not in the students table, report “The sid is invalid.” 
    isValid := 0;
    isValid := fn_Validate_Student(st_sid); 
    if(isValid = 0)then
      status_log := 'failed-'||'The sid is invalid.';   
    End if;
    
    --If the classid is not in the classes table, report “The classid is invalid.” 
    if isValid = 1 then
      isValid := 0;
      isValid := fn_Validate_Classes(st_classid); 
      if(isValid = 0)then
        status_log := 'failed-'||'The classid is invalid.'; 
      End if;
    End if;
    
    --If the student is not enrolled in the class, report “The student is not enrolled in the class.”
    if isValid = 1 then
      isValid := 0;
      isValid := fn_Student_In_Class(st_sid, st_classid); 
      if(isValid = 1)then
        status_log := 'failed-'||'The student is not enrolled in the class.'; 
        isValid := 0;
        else
        isValid := 1;
      End if;
    End if;
    
    --If dropping the student from the class would cause a violation of the prerequisite requirement for another class, 
    --then reject the drop attempt and report “The drop is not permitted because another class uses it as a prerequisite.” 
    if isValid = 1 then
      isValid := 0;
      isValid := fn_prerequisite_Check(st_sid, st_classid); 
      if(isValid = 0)then
        status_log := 'failed-'||'Prerequisite courses have not been completed.';
      End if;
    End if;
    
    --In all the other cases, the student should be dropped from the class.
    if isValid = 1 then
      delete from enrollments where sid = st_sid and classid = st_classid;
      status_log := 'success-'||'The student is droped from the class.';
    End if;
    
    --If the class is the last class for the student, report “This student is not enrolled in any classes.” 
    if isValid = 1 then
      isValid := 0;
      isValid := fn_Is_Student_Enrolled(st_sid);
      if(isValid = 1) then
      status_log := status_log || 'msg-'||'This student is not enrolled in any classes.';
       else
        isValid := 1;
      End if;
    End if;
    
    --If the student is the last student in the class, report “The class now has no students.” 
    if isValid = 1 then
      isValid := 0;
      isValid := fn_Class_Has_Students(st_classid);
      if(isValid = 1) then
      status_log := status_log || 'msg-'||'The class now has no students.';
      End if;
    End if;
    
    --Again, you should make sure that all data are consistent after the drop and all updates caused by the drop should be implemented using trigger(s).    
    END;
    
    FUNCTION fn_Validate_Student
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
    
   FUNCTION fn_Validate_Classes
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
   
   FUNCTION fn_Validate_Class_Limit
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
  
  FUNCTION fn_Student_Saturated
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
  
  FUNCTION fn_Student_Overloaded
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
  
  FUNCTION fn_Student_In_Class
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
  
  FUNCTION fn_prerequisite_Enroll(
   st_sid IN STUDENTS.SID%TYPE,
   cl_classid IN CLASSES.CLASSID%TYPE
    ) RETURN number 
    IS
    Course_Code varchar2(12);
    loopIt number;
    isValid number;
    BEGIN  
 
    select dept_code || course_no INTO Course_Code from classes where classid = cl_classid;
   
   INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)  
     (SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
     WHERE (dept_code || course_no) in (Course_Code));   
   loopIt := 0;
    WHILE loopIt = 0 
    LOOP  
       INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)   
       SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
       WHERE (dept_code || course_no) in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);   
       update tempPrerequisites set isChecked = 1 where preReqCourse_Code in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);   
       loopIt := fn_Chk_LoopIt();   
    END LOOP; 
    
     select 0 INTO isValid 
     from tempPrerequisites tp join classes cl on tp.preReqCourse_Code = (cl.dept_code||cl.course_no) 
       join enrollments en on cl.classid  = en.classid
     where en.sid = st_sid and en.classid = cl.classid and en.lgrade <= 'D'
     order by tp.Course_Count asc; 
     
     return isValid;
     
     delete from tempPrerequisites;  
     
      exception
            when no_data_found then return 1;
             delete from tempPrerequisites;          
    END;
    
    FUNCTION fn_prerequisite_Check(
   st_sid IN STUDENTS.SID%TYPE,
   cl_classid IN CLASSES.CLASSID%TYPE
    ) RETURN number 
    IS
    Course_Code varchar2(12);
    loopIt number;
    isValid number;
    BEGIN  
     
      select dept_code || course_no INTO Course_Code from classes where classid = cl_classid;
     
     INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)  
       (SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
       WHERE (dept_code || course_no) in (Course_Code));   
     loopIt := 0;
    WHILE loopIt = 0 
    LOOP  
       INSERT INTO tempPrerequisites (Course_Count, preReqCourse_Code, isChecked)   
       SELECT Course_Count_Seq.nextval, pre_dept_code || pre_course_no, 0 FROM prerequisites 
       WHERE (dept_code || course_no) in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);   
       update tempPrerequisites set isChecked = 1 where preReqCourse_Code in (select preReqCourse_Code from tempPrerequisites where isChecked != 1);   
       loopIt := fn_Chk_LoopIt();   
    END LOOP; 
    
     select 0 INTO isValid 
     from tempPrerequisites tp join classes cl on tp.preReqCourse_Code = (cl.dept_code||cl.course_no) 
       join enrollments en on cl.classid  = en.classid
     where en.sid = st_sid and en.classid = cl_classid;
     
      return isValid;
     
     delete from tempPrerequisites;  
     
      exception
            when no_data_found then return 1;
             delete from tempPrerequisites; 
         
    END;
    
    FUNCTION fn_Is_Student_Enrolled
  (st_sid IN STUDENTS.SID%TYPE)
   RETURN number
  IS
  isValid number;
  BEGIN
      select 0 INTO isValid from enrollments en where en.sid = st_sid and rownum = 1;      
      return isValid;
      
    exception
          when no_data_found then return 1;
  END;
  
  FUNCTION fn_Class_Has_Students
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
  
  FUNCTION fn_Chk_LoopIt
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
      
END;
/