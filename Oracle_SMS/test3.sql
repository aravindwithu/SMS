
drop table tempPrerequisites;


CREATE TABLE tempPrerequisites (
     Course_Count NUMBER,
     preReqCourse_Code varchar2(12) NOT NULL,
     isChecked NUMBER,
     PRIMARY KEY (Course_Count)
);

CREATE SEQUENCE Course_Count_Seq START WITH 1 increment by 1;

create trigger trg_Course_Count
    before insert on tempPrerequisites
      for each row
       begin
        select Course_Count_Seq.nextval
      into :new.Course_Count
    from dual;
  end;
  /

insert into tempPrerequisites values (Course_Count_Seq.nextval, '232', 0);

select * from tempPrerequisites;

set serveroutput on
declare
   cr_dept_code COURSES.DEPT_CODE%TYPE;
   cr_course_no COURSES.COURSE_NO%TYPE; 
   rec tempPrerequisites%ROWTYPE;
   PrerequisiteData sys_refcursor;
Begin
cr_dept_code := 'CS';
cr_course_no := 432;
PrerequisiteData := null;
get_Prerequisite_Courses(cr_dept_code, cr_course_no, PrerequisiteData);

LOOP
  FETCH PrerequisiteData INTO rec;
  EXIT WHEN PrerequisiteData%NOTFOUND;
  dbms_output.put_line(rec.preReqCourse_Code);
END LOOP;

End;
/

var preCourse_Code varchar2(12);
EXEC :preCourse_Code := fnGetPreRequisite('Ma314');
print preCourse_Code;
