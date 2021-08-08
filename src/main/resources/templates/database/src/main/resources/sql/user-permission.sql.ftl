DO
$$
DECLARE
db_user TEXT = '${dbUser}';
BEGIN
    EXECUTE 'GRANT ALL ON SCHEMA ${schema.snakeCase} TO ' || db_user;
    EXECUTE 'GRANT ALL ON ALL SEQUENCES IN SCHEMA ${schema.snakeCase} TO ' || db_user;
    EXECUTE 'GRANT SELECT, UPDATE, INSERT ON ALL TABLES IN SCHEMA ${schema.snakeCase} TO ' || db_user;
    EXECUTE 'GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA ${schema.snakeCase} TO ' || db_user;
END
$$;
