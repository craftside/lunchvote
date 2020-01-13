Lunch vote: voting system for deciding where to have lunch 
===============================

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users;
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price);
- Menu changes each day (admins do the updates);
- Users can vote on which restaurant they want to have lunch at;
- Only one vote counted per user;
- If user votes again the same day: 
  - If it is before 11:00 we asume that he changed his mind;
  - If it is after 11:00 then it is too late, vote can't be changed;
  
Each restaurant provides new menu each day.

It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

-------------------------
##### Used Technologies
- Java 11;
- Spring (MVC, Security, Data Jpa);
- Hibernate;
- HSQLDB;
- JSON (Jackson);
- Slf4J, Logback;
- Maven;
- Tomcat; 

##### Credentials

- Admin
```
username: admin@gmail.com; 
password: admin;
--user admin@gmail.com:admin
```
- User1
```
username: user1@yandex.ru
password: password
--user user1@yandex.ru:password
```
- User2
```
username: user2@yandex.ru
password: password
--user user2@yandex.ru:password
```
-------------------------
## REST API (curl commands)
### Admin can manipulate with Users:

##### Get all user:
`curl -X GET http://localhost:8080/lunchvote/rest/admin/users -s --user admin@gmail.com:admin`

##### Get user with id=100000:
`curl -X GET http://localhost:8080/lunchvote/rest/admin/users/100000 -s --user admin@gmail.com:admin`

##### Add a new user:
```
{
    "name": "curl-user", 
    "email": "curl-user@yandex.ru", 
    "password": "password"
}
```
`curl -X POST http://localhost:8080/lunchvote/rest/admin/users -H 'Content-Type:application/json;charset=UTF-8' -d '{"name": "curl-user", "email": "curl-user@yandex.ru", "password": "password"}' -s --user admin@gmail.com:admin`

##### Delete user with id=100002:
`curl -X DELETE http://localhost:8080/lunchvote/rest/admin/users/100002 -s --user admin@gmail.com:admin` 

##### Update user with id=100001:
```
{
    "id": 100001,
    "name": "updatedName",
    "email": "updatedName@yandex.ru",
    "password": "password",
    "roles": ["ROLE_USER"]
}
```

`curl -X PUT http://localhost:8080/lunchvote/rest/admin/users/100001 -H 'Content-Type:application/json;charset=UTF-8' -d '{"id": 100001,"name": "updatedName","email": "updatedName@yandex.ru","password": "password","roles": ["ROLE_USER"]}' -s --user admin@gmail.com:admin`

##### Get user by email=user1@yandex.ru:
`curl -X GET http://localhost:8080/lunchvote/rest/admin/users/by?email=user1@yandex.ru -s --user admin@gmail.com:admin`

##### Disable user with id=100002 (enabled=false):
`curl -X PATCH http://localhost:8080/lunchvote/rest/admin/users/100002?enabled=false -s --user admin@gmail.com:admin`

### Admin can manipulate with restaurants

##### Get all restaurants:
`curl -X GET http://localhost:8080/lunchvote/rest/admin/restaurants -s --user admin@gmail.com:admin`

##### Get restaurant with id=100004:
`curl -X GET http://localhost:8080/lunchvote/rest/admin/restaurants/100004 -s --user admin@gmail.com:admin`

##### Get restaurant by name=Starbucks (a case-sensitive search):
`curl -X GET http://localhost:8080/lunchvote/rest/admin/restaurants/by?name=Starbucks -s --user admin@gmail.com:admin`

##### Add a new restaurant:
```
{
    "name": "curl-restaurant"
}
```
`curl -X POST http://localhost:8080/lunchvote/rest/admin/restaurants -H 'Content-Type:application/json;charset=UTF-8' -d '{"name": "curl-restaurant"}' -s --user admin@gmail.com:admin`

##### Delete restaurant with id=100005:
`curl -X DELETE http://localhost:8080/lunchvote/rest/admin/restaurants/100005 -s --user admin@gmail.com:admin` 

##### Update restaurant with id=100004:
```
{
    "id": 100004,
    "name": "McDuck"
}
```
`curl -X PUT http://localhost:8080/lunchvote/rest/admin/restaurants/100004 -H 'Content-Type:application/json;charset=UTF-8' -d '{"id": 100004,"name": "McDuck"}' -s --user admin@gmail.com:admin`

### Admin can manipulate with menus

##### Get menu list for all restaurants on date=2020-01-02:
`curl -X GET http://localhost:8080/lunchvote/rest/admin/restaurants/menu/at?date=2020-01-02 -s --user admin@gmail.com:admin`

##### Get menu list for all restaurants on dates between startDate={} and endDate={}:
`curl -X GET 'http://localhost:8080/lunchvote/rest/admin/restaurants/menu/between?startDate=2020-01-01&endDate=2020-01-05' -s --user admin@gmail.com:admin`

##### Get menu list for restaurant with id=100004 (whole period):
`curl -X GET 'http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu' -s --user admin@gmail.com:admin`

##### Get menu with dish list for restaurant with id=100004 on date=2020-01-02:
`curl -X GET 'http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu/at?date=2020-01-02' -s --user admin@gmail.com:admin`

##### Get menu list for restaurant with id=100004 on dates between startDate=2020-01-02 and endDate=2020-01-05:
`curl -X GET 'http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu/between?startDate=2020-01-02&endDate=2020-01-05' -s --user admin@gmail.com:admin`
 
##### Get a menu with id=100007 for restaurant with id=100004:
`curl -X GET 'http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu/100007' -s --user admin@gmail.com:admin`

##### Create menu for restaurant with id=100004:
```
{
    "date": "2020-01-06"
}
```
`curl -X POST 'http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu' -H 'Content-Type:application/json;charset=UTF-8' -d '{"date": "2020-01-06"}' -s --user admin@gmail.com:admin`

##### Put menu for restaurant with id=100005:
```
{
    "id": 100008, 
    "date": "2020-01-10"
}
```
`curl -X PUT 'http://localhost:8080/lunchvote/rest/admin/restaurants/100005/menu/100008' -H 'Content-Type:application/json;charset=UTF-8' -d '{"id": 100008, "date": "2020-01-10"}' -s --user admin@gmail.com:admin`

##### Delete menu for restaurant with id=100004:
`curl -X DELETE http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu/100010 -s --user admin@gmail.com:admin`

### Admin can manipulate with dishes

##### Get dish list for all restaraunts' menu:
`curl -X GET http://localhost:8080/lunchvote/rest/admin/restaurants/menu/dishes/ -s --user admin@gmail.com:admin`

##### Get dish list for menu with id=100010 and restaurant with id=100004:
`curl -X GET http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu/100010/dishes -s --user admin@gmail.com:admin`

##### Get dish with id=100020 for menu with id=100004 and restaurant with id=100007:
`curl -X GET http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu/100010/dishes/100020 -s --user admin@gmail.com:admin`

##### Add dish for restaurant with id=100004 and menu with id=100007:
```
{
    "name": "curl-dish", 
    "price": 123
}
```
`curl -X POST 'http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu/100007/dishes' -s -H 'Content-Type:application/json;charset=UTF-8' -d '{"name": "curl-dish", "price": 123}' -s --user admin@gmail.com:admin`

##### Put dish with id=100020 for restaurant with id=100004 and menu with id=100010:
```
{
    "id": 100020,
    "name": "curl-updated", 
    "price": 456
}
```
`curl -X PUT 'http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu/100010/dishes/100020' -H 'Content-Type:application/json;charset=UTF-8' -d '{"id": 100020,"name": "curl-updated", "price": 456}' -s --user admin@gmail.com:admin`

##### Delete dish:
`curl -X DELETE 'http://localhost:8080/lunchvote/rest/admin/restaurants/100004/menu/100010/dishes/100020' -s --user admin@gmail.com:admin`

### Users can vote
##### Get vote summary:
`curl -X GET http://localhost:8080/lunchvote/rest/profile/voices -s --user admin@gmail.com:admin`

##### Get user vote status:
```
{
    "id":<Menu_ID>,
    "date":<Date>,
    "restaurant":
        {
            "id": <Restaurant_ID>,
            "name":<Restaurant_Name>
        },
            "dishes":
                [
                    {"id":<Dish_ID>,"name":<Dish_Name>,"price":<Dish_Price>}
                    ...
                ]
}
```
`curl -X GET http://localhost:8080/lunchvote/rest/profile/myvote -s --user user1@yandex.ru:password`

##### Add  a vote by user for restaurant with id=100003:
`curl -X POST http://localhost:8080/lunchvote/rest/profile/restaurants/100003/vote -s --user user1@yandex.ru:password`