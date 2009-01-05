CREATE OR REPLACE
PACKAGE BODY UT AS

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
  IS
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

  PROCEDURE assert_equals
  (
     p_expected IN DATE
    ,p_value    IN DATE
  )
  IS
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
  END assert_equals;

  PROCEDURE assert_equals
  (
     p_expected IN BOOLEAN
    ,p_value    IN BOOLEAN
  )
  IS
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
  END assert_equals;

  PROCEDURE assert_equals
  (
     p_expected IN CHAR
    ,p_value    IN CHAR
  )
  IS
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
  END assert_equals;

  PROCEDURE assert_equals
  (
     p_expected IN VARCHAR2
    ,p_value    IN VARCHAR2
  )
  IS
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
  END assert_equals;

  PROCEDURE assert_equals
  (
     p_expected IN NUMBER
    ,p_value    IN NUMBER
  )
  IS
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
  END assert_equals;

  PROCEDURE assert_equals
  (
     p_expected IN PLS_INTEGER
    ,p_value    IN PLS_INTEGER
  )
  IS
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
  END assert_equals;

END UT;
/