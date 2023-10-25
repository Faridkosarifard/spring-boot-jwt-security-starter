# Spring-Boot-Security-
Spring Boot Security with JWT and multi Role
﻿

#  New User registration
 **POST**
 
```
    http://localhost:8086/user/registration
```
Body
```json
{
    "firstName": "Jane",
    "lastName": "Doe",
    "userName": "farid",
    "password": "Faridkosari12##",
    "email": "farid.kosarifard@gmail.com",
    "isUsing2FA": "false"
}
```

#  Email Confirm
**GET** 
```
http://localhost:8086/registrationConfirm?token=token

```

Query Params

```
token = example-token
```

#  Forget Password
**POST** 
```
http://localhost:8086/user/resetPassword

```

Body


```json
{
    "email": "farid.kosarifard@gmail.com"
}
```

#  Rest Password Token Veryfing
**GET** 
```
http://localhost:8086/user/forgetPassword?token=example-token

```
Query Params

```
token = example-token
```

# POST Change Password with Token
**POST** 
```
http://localhost:8086/user/savePassword

```
Body
```json
{
    "token": "example-token",
    "newPassword": "F2123"
}
```
#  Change Password
**POST** 
```
http://localhost:8086/user/changePassword

```

Body
```json
{
    "oldPassword":"example-password",
    "newPassword":"F271@9506m",
    "email":"farid.kosarifard@gmil.com"
}
```
# Login
**GET** 
```
http://localhost:8086/user/login

```

Body
```json
{
    "userName": "farid",
    "password": "F271@9506m" 
}
```
