CREATE OR REPLACE
PACKAGE TEST AS

  ASSERTION_FAILED EXCEPTION;
  PRAGMA EXCEPTION_INIT(ASSERTION_FAILED, -20100);

  FUNCTION assert_equals
  (
     p_expected BOOLEAN
    ,p_value    BOOLEAN
  )
  RETURN BOOLEAN;

  FUNCTION assert_equals
  (
     p_expected DATE
    ,p_value    DATE
  )
  RETURN BOOLEAN;

  FUNCTION assert_equals
  (
     p_expected CHAR
    ,p_value    CHAR
  )
  RETURN BOOLEAN;

  FUNCTION assert_equals
  (
     p_expected VARCHAR2
    ,p_value    VARCHAR2
  )
  RETURN BOOLEAN;

  FUNCTION assert_equals
  (
     p_expected NUMBER
    ,p_value    NUMBER
  )
  RETURN BOOLEAN;

  FUNCTION assert_equals
  (
     p_expected PLS_INTEGER
    ,p_value    PLS_INTEGER
  )
  RETURN BOOLEAN;

END TEST;
/