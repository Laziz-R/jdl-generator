{
  "${schema.pascalCase}Service": {
    "token": "e8b74e1f-0bfc-4c37-9fad-a8ef4c67988d",
    "http": {
      "host": "0.0.0.0",
      "port": 7024
    },
    "db": {
      "host": "localhost",
      "port": 5432,
      "db_name": "${db_name}",
      "user": "${db_user}",
      "password": "${db_password}",
      "max_pool_size": 30
    }
  },
  "AuthService": {
    "http": {
      "host": "localhost",
      "port": 7011
    }
  }
}