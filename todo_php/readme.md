# todo

A simple web api using PHP and MySQL database

## Database schema

**Enter these commands to your mysql shell line by line to create the database and tables**

```
create database todo_db;
```

```
use todo_db;
```

```
create table accounts_table (account_id int NOT NULL AUTO_INCREMENT, email varchar(100) NOT NULL, password varchar(500) NOT NULL, PRIMARY KEY(account_id)) Engine=InnoDB, charset=latin1, AUTO_INCREMENT=1;
```

```
create table todo_table (todo_id int NOT NULL AUTO_INCREMENT, account_id INT NOT NULL, date date NOT NULL, todo varchar(500) NOT NULL PRIMARY KEY(todo_id)) Engine=InnoDB, charset=latin1, AUTO_INCREMENT=1;
```

**Enter these commands to create the user**

```
create user 'default-user'@'localhost';
```

```
grant insert, select, update, delete on todo_db.* to default-user'@'localhost';
```


## Running the api

To run the api make sure that the directory where you cloned this repo is under the *htdocs* folder of your xampp installation. The default location is at `C:/xampp/htdocs/`

Once you moved the clone repository under the htdocs folder of your xampp installation, simply start `apache` and `mysql` in your xampp control panel.

**Note:** If you have a separate installation of mysql, you do not need to start the `mysql` service in your control panel because it is a service and will therefore be always active.

Finally, to be able to use the api, the baseurl is `localhost/todo/`. Enter this url in your browser and you should see a text saying something like *Web API*

## Endpoints

**login(POST)**
- This endpoint is used to login. Credentials must be passed in the body as a JSON object like so:

```javascript
{
    "email": "email@site.com",
    "password": "password"
}
```


**register(POST)**
- This endpoint is used to register a new account. Just like in the login endpoint, you must pass the user credentials in the body of the request like so:

```javascript
{
    "email": "email@site.com",
    "password": "password"
}
```


**todos(POST)**
- This endpoint displays all of todos of a given account. You need to pass the account id as a JSON object in the body of the request like so:

```javascript
{
    "account_id": 1
}
```


**inserttodo(POST)**
- This endpoint allows you to add a todo for a given account. You need to pass the todo as a JSON object in the body of the request like so:

```javascript
{
    "account_id": 1,
    "date": "yyyy-mm-dd",
    "todo": "content of todo"
}
```


**updatetodo(POST)**
- This endpoint is used to update an existing todo item. You need to pass the `todo_id`, `date` and `todo` in the body of the request as a JSON object like so:

```javascript
{
    "todo_id": 1,
    "date": "yyyy-mm-dd",
    "todo": "content of todo"
}
```


**deletetodo(POST)**
- This endpoint is used to delete an existing todo item. You need to pass the id of the todo in the body of the request as a JSON object like so:

```javascript
{
    "todo_id": 1
}
```
