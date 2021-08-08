#postgres DataBase configuration
#postgres DB uri
url = jdbc:postgresql://localhost:5432/${dbName}
#DB user pls use Database admin to create objects
user = ${dbUser}
password = ${dbPassword}
ssl = false
#project folder
projectPath = ${currentPath}

# deployment commands order
deploymentScriptCommands = dropSchema|schema|sequences|tables|functions|references|permissions

# Commands
#dropSchema command pls do not use if you only updating schema
#dropSchema = src/main/resources/sql/schema-drop-cascade.sql

#creating schema command pls do not use if you only updating schema
schema = src/main/resources/sql/create-schema.sql

#sequences command run scripts in folder
sequences = src/main/resources/sql/sequences

#tables command run scripts in folder
tables = src/main/resources/sql/tables

#functions command run scripts in folder
functions = src/main/resources/sql/functions

#references command run scripts in folder
references = src/main/resources/sql/references

#permissions command run script. pls update user permission script for non-admin user
permissions = src/main/resources/sql/user-permission.sql
