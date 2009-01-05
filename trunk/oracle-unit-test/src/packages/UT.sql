CREATE OR REPLACE
PACKAGE UT AS

  ASSERTION_FAILED EXCEPTION;
  PRAGMA EXCEPTION_INIT(ASSERTION_FAILED, -20100);

  PROCEDURE assert_equals
  (
     p_expected BOOLEAN
    ,p_value    BOOLEAN
  );

  PROCEDURE assert_equals
  (
     p_expected DATE
    ,p_value    DATE
  );

  PROCEDURE assert_equals
  (
     p_expected CHAR
    ,p_value    CHAR
  );

  PROCEDURE assert_equals
  (
     p_expected VARCHAR2
    ,p_value    VARCHAR2
  );

  PROCEDURE assert_equals
  (
     p_expected NUMBER
    ,p_value    NUMBER
  );

  PROCEDURE assert_equals
  (
     p_expected PLS_INTEGER
    ,p_value    PLS_INTEGER
  );

END UT;
/