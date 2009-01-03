CREATE OR REPLACE
PACKAGE BODY T AS

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

  PROCEDURE throwAssertionException
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
  END throwAssertionException;

  FUNCTION assertEquals
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
      throwAssertionException
      (
          to_char(p_expected)
         ,to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assertEquals;

  FUNCTION assertEquals
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
      throwAssertionException
      (
         boolean_to_char(p_expected)
        ,boolean_to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assertEquals;

  FUNCTION assertEquals
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
      throwAssertionException
      (
          to_char(p_expected)
         ,to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assertEquals;

  FUNCTION assertEquals
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
      throwAssertionException
      (
          to_char(p_expected)
         ,to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assertEquals;

  FUNCTION assertEquals
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
      throwAssertionException
      (
          to_char(p_expected)
         ,to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assertEquals;

  FUNCTION assertEquals
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
      throwAssertionException
      (
          to_char(p_expected)
         ,to_char(p_value)
      );
    END IF;

    RETURN v_status;
  END assertEquals;

END T;
/