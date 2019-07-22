-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile

-- 1. table
create table sp_test_tb_01 (
    name varchar2(100) primary key,
    age number(3) default(20),
    email varchar2(100),
    created DATE
)
INSERT INTO sp_test_tb_01 (NAME, AGE, EMAIL, CREATED) VALUES ( 'JaneDoe', 32, 'janedoe@waikato.ac.nz', SYSDATE);
INSERT INTO sp_test_tb_01 (NAME, AGE, EMAIL, CREATED) VALUES ( 'DavidButton', 22, 'dbutton@waikato.ac.nz', SYSDATE);
INSERT INTO sp_test_tb_01 (NAME, AGE, EMAIL, CREATED) VALUES ( 'JohnSmith', 26, 'johnsm@waikato.ac.nz', SYSDATE);
commit;
select * from sp_test_tb_01;

-- 2. type
CREATE OR REPLACE PACKAGE test_type_01 AUTHID CURRENT_USER as
    TYPE type_contact_01 IS RECORD (
        email    VARCHAR2(100),
        created  DATE DEFAULT SYSDATE
    );
    TYPE type_person_01 IS RECORD (
        name     VARCHAR2(100),
        age     NUMBER(3) DEFAULT 20,
        contact type_contact_01
        );
END test_type_01;


-- 3.1 Simple procedure declaration
CREATE OR REPLACE PACKAGE test_pkg_01 AS
  PROCEDURE test_procedure_01 (
       p_name VARCHAR2,
       p_age NUMBER,
       p_email VARCHAR2 default null
   );
END test_pkg_01;
--3.2 Simple procedure body
CREATE OR REPLACE PACKAGE BODY test_pkg_01 AS
    PROCEDURE test_procedure_01 (p_name VARCHAR2, p_age NUMBER, p_email VARCHAR2) IS
        BEGIN
            DBMS_OUTPUT.PUT_LINE('Email: '||p_email);
        END test_procedure_01;
END test_pkg_01;
--3.3 Simple procedure invoke
DECLARE
   p_name VARCHAR2(100);
   p_age NUMBER(3);
   p_email VARCHAR2(100) default null;
BEGIN
    p_name := 'John';
    p_age := 23 ;
    p_email := 'john@waikato.ac.nz' ;
    test_pkg_01.test_procedure_01(p_name, p_age, p_email);
END;


-- 4.1 User-defined param procedure declaration
CREATE OR REPLACE PACKAGE test_pkg_01 AS
  PROCEDURE test_procedure_01 (
       p_name IN VARCHAR2,
       p_age IN NUMBER,
       p_contact IN test_type_01.type_contact_01 default null
   );
END test_pkg_01;

--4.2 User-defined param procedure body
CREATE OR REPLACE PACKAGE BODY test_pkg_01 AS
    PROCEDURE test_procedure_01 (p_name IN VARCHAR2, p_age IN NUMBER, p_contact IN test_type_01.type_contact_01) IS
        BEGIN
            DBMS_OUTPUT.PUT_LINE('Email: '||p_contact.email);
        END test_procedure_01;
END test_pkg_01;

--4.3 User-defined param procedure invoke
DECLARE
   p_name VARCHAR2(100);
   p_age NUMBER(3);
   p_contact test_type_01.type_contact_01;
BEGIN
    p_name := 'John';
    p_age := 23 ;
    p_contact.email := 'john@waikato.ac.nz' ;
    test_pkg_01.test_procedure_01(p_name, p_age, p_contact);
END;


---------------------------------------------------------
-- 5.1 User-defined param procedure declaration
CREATE OR REPLACE PACKAGE test_pkg_01 AS
  PROCEDURE test_procedure_01 (
       p_person IN OUT test_type_01.type_person_01,
       p_etc IN VARCHAR2
   );
END test_pkg_01;

--5.2 User-defined param procedure body
CREATE OR REPLACE PACKAGE BODY test_pkg_01 AS
    PROCEDURE test_procedure_01 (p_person IN OUT test_type_01.type_person_01, p_etc IN VARCHAR2) IS
      --  DECLARE
      --      l_person test_type_01.type_person_01 ;
        BEGIN
            DBMS_OUTPUT.PUT_LINE('Email: '||p_person.contact.email);

        END test_procedure_01;
END test_pkg_01;

--5.3 User-defined param procedure invoke
DECLARE
   p_person test_type_01.type_person_01;
   p_etc VARCHAR2(100);
BEGIN
    p_person.contact.email := 'john@waikato.ac.nz';
    p_etc := '_old';
    test_pkg_01.test_procedure_01(p_person, p_etc);
END;


---------------------------------------------------------
-- 6.1 Using 'IN OUT' Parameters : procedure declaration
CREATE OR REPLACE PACKAGE test_pkg_01 AS
  PROCEDURE test_procedure_01 (
       p_person IN test_type_01.type_person_01,
       p_etc IN OUT VARCHAR2
   );
END test_pkg_01;

-- 6.2 Using 'IN OUT' Parameters : procedure body
CREATE OR REPLACE PACKAGE BODY test_pkg_01 AS
    PROCEDURE test_procedure_01 (p_person IN test_type_01.type_person_01, p_etc IN OUT VARCHAR2) IS
        BEGIN
            p_etc := 'converted';
            DBMS_OUTPUT.PUT_LINE('Email: '||p_person.contact.email);

        END test_procedure_01;
END test_pkg_01;

--6.3 Using 'IN OUT' Parameters : procedure invoke
DECLARE
   p_person test_type_01.type_person_01;
   p_etc VARCHAR2(100);
BEGIN
    p_person.contact.email := 'john@waikato.ac.nz';
    p_etc := '_old';
    test_pkg_01.test_procedure_01(p_person, p_etc);
    DBMS_OUTPUT.PUT_LINE('p_etc: '||p_etc);
END;


---------------------------------------------------------
-- 7.1 Using 'IN OUT' User defined parameters : procedure declaration
CREATE OR REPLACE PACKAGE test_pkg_01 AS
  PROCEDURE test_procedure_01 (
       p_person IN OUT test_type_01.type_person_01,
       p_etc IN VARCHAR2
   );
END test_pkg_01;

-- 7.2 Using 'IN OUT' User defined parameters : procedure body
CREATE OR REPLACE PACKAGE BODY test_pkg_01 AS
    PROCEDURE test_procedure_01 (p_person IN OUT test_type_01.type_person_01, p_etc IN VARCHAR2) IS
        BEGIN
            DBMS_OUTPUT.PUT_LINE('Email: '||p_person.contact.email);
            p_person.contact.email := 'new@waikato.ac.nz';
        END test_procedure_01;
END test_pkg_01;

-- 7.3 Using 'IN OUT' User defined parameters : procedure invoke
DECLARE
   p_person test_type_01.type_person_01;
   p_etc VARCHAR2(100);
BEGIN
    p_person.name := 'john';
    p_person.age := 30;
    p_person.contact.email := 'john@waikato.ac.nz';
    p_person.contact.created := sysdate;
    p_etc := '_old';
    test_pkg_01.test_procedure_01(p_person, p_etc);
    DBMS_OUTPUT.PUT_LINE('Name: '||p_person.name||', Age: '||p_person.age||', Contact.email: '||p_person.contact.email);
END;











--++++++++++++++ working ++++++++++++++++++++++++

-- 8.1 Using 'IN OUT' User defined parameters : procedure declaration
CREATE OR REPLACE PACKAGE test_pkg_01 AS
  PROCEDURE test_procedure_01 (
       p_person IN OUT test_type_01.type_person_01,
       p_etc IN VARCHAR2
   );
END test_pkg_01;

-- 8.2 Using 'IN OUT' User defined parameters : procedure body
CREATE OR REPLACE PACKAGE BODY test_pkg_01 AS
    PROCEDURE test_procedure_01 (p_person IN OUT test_type_01.type_person_01, p_etc IN VARCHAR2) IS
        c_name sp_test_tb_01.name%type;
        c_age sp_test_tb_01.age%type;
        c_email sp_test_tb_01.email%type;
        c_created sp_test_tb_01.created%type;
        CURSOR c_person IS
            SELECT name, age, email, created FROM sp_test_tb_01;

        BEGIN
            OPEN c_person;
            LOOP
                FETCH c_person into c_name, c_age, c_email, c_created;
                EXIT WHEN c_person%notfound;
                --DBMS_OUTPUT.PUT_LINE('Name: '||c_person||'  Age: '||c_age||'  Email: '||c_email||'  Created: '||to_char(c_created,'DD/MM/YYYY'));
                DBMS_OUTPUT.PUT_LINE('Name: '||c_person||'  Age: '||to_char(c_age)||'  Email: '||c_email||'  Created: ');
            END LOOP;
            CLOSE c_person;
            p_person.contact.email := 'new@waikato.ac.nz';
        END test_procedure_01;
END test_pkg_01;

-- 8.3 Using 'IN OUT' User defined parameters : procedure invoke
DECLARE
   p_person test_type_01.type_person_01;
   p_etc VARCHAR2(100);
BEGIN
    p_person.contact.email := 'john@waikato.ac.nz';
    p_etc := '_old';
    test_pkg_01.test_procedure_01(p_person, p_etc);
    DBMS_OUTPUT.PUT_LINE('contact.email: '||p_person.contact.email);
END;
--++++++++++++++ working ++++++++++++++++++++++++















-- ?.1 declare package
CREATE OR REPLACE PACKAGE sp_test_pkg_01 AS
  PROCEDURE findMin(x IN number, y IN number, z OUT number);
END sp_test_pkg_01;

-- ?.2 declare body
CREATE OR REPLACE PACKAGE BODY sp_test_pkg_01 AS
    PROCEDURE findMin(x IN number, y IN number, z OUT number) IS
    BEGIN
       IF x < y THEN
          z:= x;
       ELSE
          z:= y;
       END IF;
    END findMin;
END sp_test_pkg_01;

-- ?.3 invoke package
DECLARE
   a number;
   b number;
   c number;
BEGIN
   a:= 23;
   b:= 45;
   sp_test_pkg_01.findMin(a, b, c);
   dbms_output.put_line(' Minimum of (23, 45) : ' || c);
END;




