package de.gluehloch.groovy.oracle.meta

/**
 * Die Klasse schaltet alle Constraints in einem User-Schema EIN.
 */
class EnableConstraints {

    def sql

    def run() {
        sql.call("""
declare
  v_cmd VARCHAR2(1024);
  procedure enable_foreign_keys(p_table_name VARCHAR2) is
  begin
    for i_constraint in (
      select *
        from user_constraints uc
        where uc.table_name = p_table_name
          and uc.constraint_type = 'R' and uc.status = 'DISABLED'
    )
    loop
      v_cmd := 'ALTER TABLE ' || p_table_name || ' ENABLE CONSTRAINT ' || i_constraint.constraint_name;
      --DBMS_OUTPUT.PUT_LINE(v_cmd);
      execute immediate v_cmd;
    end loop;
  end;
  procedure enable_primary_keys(p_table_name VARCHAR2) is
  begin
    for i_constraint in (
      select *
        from user_constraints uc
        where uc.table_name = p_table_name
          and uc.constraint_type = 'P' and uc.status = 'DISABLED'
    )
    loop
      v_cmd := 'ALTER TABLE ' || p_table_name || ' ENABLE CONSTRAINT ' || i_constraint.constraint_name;
      --DBMS_OUTPUT.PUT_LINE(v_cmd);
      execute immediate v_cmd;
    end loop;
  end;
begin
  for i_table in (
    select * from user_tables
  )
  loop
    enable_primary_keys(i_table.table_name);
  end loop;

  for i_table in (
    select * from user_tables
  )
  loop
    enable_foreign_keys(i_table.table_name);
  end loop;
end;
""")
    }

}