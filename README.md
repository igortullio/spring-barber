# Spring Barber

## What is?

Spring barber is a project to test knowledge in Spring and develop an API for registering barbershops and scheduling 
appointments.

--------

## Tables overview

![tables overview](/Users/igortullio/Downloads/Spring Barber.png)

--------

## API documentation

* [Address](#address)
* [Auth](#auth)
* [Barbershop](#barbershop)
* [City](#city)
* [Operation](#operation)
* [Permission](#permission)
* [PermissionGroup](#permissiongroup)
* [Schedule](#schedule)
* [State](#state)
* [User](#user)

## Address

### 1. Delete

***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{URL}}/addresses/6
```

### 2. Get

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/addresses/6
```

### 3. Post

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{URL}}/addresses
```

***Body:***

```js        
{
    "zipCode": "72010-010",
    "place": "place",
    "number": "number",
    "district": "district",
    "complement": "complement",
    "latitude": 15,
    "longitude": 100,
    "city": {
        "id": 1
    }
}
```

### 4. Put

***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{URL}}/addresses/6
```

***Body:***

```js        
{
    "zipCode": "72010-011",
    "place": "place",
    "number": "number",
    "district": "district",
    "complement": "complement",
    "latitude": 15,
    "longitude": 100,
    "city": {
        "id": 1
    }
}
```

## Auth

### 1. Login

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{URL}}/auth/login
```

***Body:***

```js        
{
    "email": "igortullio@hotmail.com",
    "password": "123456"
}
```

## Barbershop

### 1. Delete

***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{URL}}/barbershops/1
```

### 2. Get

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/barbershops/1
```

### 3. Get All

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/barbershops
```

***Query params:***

| Key    | Value | Description |
| ------ | ----- | ----------- |
| cityId | 1     |             |

### 4. Post

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{URL}}/barbershops
```

***Body:***

```js        
{
    "name": "Barbershop 1",
    "active": true,
    "open": true,
    "address": {
        "id": 1
    }
}
```

### 5. Put

***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{URL}}/barbershops/1
```

***Body:***

```js        
{
    "name": "Barbershop 1",
    "active": true,
    "open": true,
    "address": {
        "id": 1
    }
}
```

## City

### 1. Delete

***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{URL}}/cities/4
```

### 2. Get

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/cities/1
```

### 3. Get All

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/cities
```

***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| stateId | 7 |  |

### 4. Post

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{URL}}/cities
```

***Body:***

```js        
{
    "name": "City 1",
    "state": {
        "id": 1
    }
}
```

### 5. Put

***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{URL}}/cities/4
```

***Body:***

```js        
{
    "name": "City 2",
    "state": {
        "id": 1
    }
}
```

## Operation

### 1. Delete

***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{URL}}/operations/1
```

### 2. Get

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/operations/1
```

### 3. Get All

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/operations
```

***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| barbershopId | 1 |  |

### 4. Post

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{URL}}/operations
```

***Body:***

```js        
{
    "day": "SUNDAY",
    "openTime": "08:00",
    "closeTime": "20:00",
    "barbershop": {
        "id": 2
    }
}
```

### 5. Put

***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{URL}}/operations/1
```

***Body:***

```js        
{
    "day": "MONDAY",
    "openTime": "07:00",
    "closeTime": "20:00",
    "barbershop": {
        "id": 1
    }
}
```

## Permission

### 1. Delete

***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{URL}}/permissions/1
```

### 2. Get

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/permissions/1
```

### 3. Get All

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/permissions
```

### 4. Post

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{URL}}/permissions
```

***Body:***

```js        
{
    "name": "permissions test",
    "description": "permissions test description",
    "permissionGroup": {
        "id": 1
    }
}
```

### 5. Put

***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{URL}}/permissions/5
```

***Body:***

```js        
{
    "name": "permissions test 1",
    "description": "permissions test description 1",
    "permissionGroup": {
        "id": 1
    }
}
```

## PermissionGroup

### 1. Delete

***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{URL}}/groups/3
```

### 2. Get

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/groups/1
```

### 3. Get All

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/groups
```

### 4. Post

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{URL}}/groups
```

***Body:***

```js        
{
    "name": "group test"
}
```

### 5. Put

***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{URL}}/groups/2
```

***Body:***

```js        
{
    "name": "GROUP_1"
}
```

## Schedule

### 1. Delete

***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{URL}}/schedules/1
```

### 2. Get

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/schedules/1
```

### 3. Get All

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/schedules
```

***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| operationId | 2 |  |

### 4. Post

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{URL}}/schedules
```

***Body:***

```js        
{
    "dateTime": "2021-11-17T13:00:00-03:00",
    "operation": {
        "id": 4
    }
}
```

### 5. Put

***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{URL}}/schedules/1
```

***Body:***

```js        
{
    "dateTime": "2021-11-17T15:00:00-03:00",
    "operation": {
        "id": 4
    }
}
```

### 6. Put Cancel

***Endpoint:***

```bash
Method: PUT
Type: 
URL: {{URL}}/schedules/1/cancel
```

### 7. Put Confirm

***Endpoint:***

```bash
Method: PUT
Type: 
URL: {{URL}}/schedules/1/confirm
```

## State

### 1. Delete

***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{URL}}/users/3
```

### 2. Get

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/states/1
```

### 3. Get All

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/states
```

***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| page | -1 |  |
| size | 5 |  |

### 4. Post

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{URL}}/states
```

***Body:***

```js        
{
    "name": "Estado",
    "initials": "ET"
}
```

### 5. Put

***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{URL}}/states/28
```

***Body:***

```js        
{
    "name": "Estado 1",
    "initials": "ET"
}
```

## User

### 1. Delete

***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{URL}}/users/3
```

### 2. Get

***Endpoint:***

```bash
Method: GET
Type: 
URL: {{URL}}/users/1
```

### 3. Post

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{URL}}/users
```

***Body:***

```js        
{
    "name": "User 1",
    "password": "123456",
    "email": "user@email.com",
    "phone": "61912345689",
    "permissionGroupSet": [
        {
            "id": 2
        }
    ]
}
```

### 4. Put

***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{URL}}/users/3
```

***Body:***

```js        
{
    "name": "User 10",
    "password": "123456",
    "email": "user@email.com",
    "phone": "61912345679",
    "permissionGroupSet": [
        {
            "id": 1
        }
    ]
}
```


---
[Back to top](#spring-barber)
