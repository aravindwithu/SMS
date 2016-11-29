Drop table tempPrerequisites;
/
  CREATE TABLE tempPrerequisites (
     Course_Count NUMBER,
     preReqCourse_Code varchar2(12) NOT NULL,
     isChecked NUMBER,
     PRIMARY KEY (Course_Count));
/
drop SEQUENCE Course_Count_Seq;
/
CREATE SEQUENCE Course_Count_Seq START WITH 1 increment by 1;
/