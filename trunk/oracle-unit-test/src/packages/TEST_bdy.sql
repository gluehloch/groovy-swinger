CREATE OR REPLACE
PACKAGE BODY TEST AS

  FUNCTION boolean_to_char
  (
     p_value IN BOOLEAN
  )
  RETURN VARCHAR2
  AS
  BEGIN
    CASE p_value
      WHEN TRUE THEN RETURN 'true';
      WHEN FALSE THEN RETURN 'false';
    END CASE;
  END boolean_to_char;

  PROCEDURE throw_assertion_exception
  (
     p_expected IN VARCHAR2
    ,p_value    IN VARCHAR2
  )
  AS
  BEGIN
    raise_application_error
    (
       -20100
      ,'ASSERTION_FAILED: Expected '
         || p_expected
         || ' but was '
         || p_value
    );
  END throw_assertion_exception;

  FUNCTION assert_equals
  (
     p_expected IN DATE
    ,p_value    IN DATE
  )
  RETURN BOOLEAN
  AS
     v_status BOOLEAN;
  BEGIN
    v_status := (p_expected = p_value);

    IF v_status = FALSE
    THEN
      throw_assertion_exception
      (
          to_char(p_expected)
         ,to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assert_equals;

  FUNCTION assert_equals
  (
     p_expected IN BOOLEAN
    ,p_value    IN BOOLEAN
  )
  RETURN BOOLEAN
  AS
     v_status BOOLEAN;
  BEGIN
    v_status := (p_expected = p_value);

    IF v_status = FALSE
    THEN
      throw_assertion_exception
      (
         boolean_to_char(p_expected)
        ,boolean_to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assert_equals;

  FUNCTION assert_equals
  (
     p_expected IN CHAR
    ,p_value    IN CHAR
  )
  RETURN BOOLEAN
  AS
     v_status BOOLEAN;
  BEGIN
    v_status := (p_expected = p_value);

    IF v_status = FALSE
    THEN
      throw_assertion_exception
      (
          to_char(p_expected)
         ,to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assert_equals;

  FUNCTION assert_equals
  (
     p_expected IN VARCHAR2
    ,p_value    IN VARCHAR2
  )
  RETURN BOOLEAN
  AS
     v_status BOOLEAN;
  BEGIN
    v_status := (p_expected = p_value);

    IF v_status = FALSE
    THEN
      throw_assertion_exception
      (
          to_char(p_expected)
         ,to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assert_equals;

  FUNCTION assert_equals
  (
     p_expected IN NUMBER
    ,p_value    IN NUMBER
  )
  RETURN BOOLEAN
  AS
     v_status BOOLEAN;
  BEGIN
    v_status := (p_expected = p_value);

    IF v_status = FALSE
    THEN
      throw_assertion_exception
      (
          to_char(p_expected)
         ,to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assert_equals;

  FUNCTION assert_equals
  (
     p_expected IN PLS_INTEGER
    ,p_value    IN PLS_INTEGER
  )
  RETURN BOOLEAN
  AS
     v_status BOOLEAN;
  BEGIN
    v_status := (p_expected = p_value);

    IF v_status = FALSE
    THEN
      throw_assertion_exception
      (
          to_char(p_expected)
         ,to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assert_equals;

END TEST;
/