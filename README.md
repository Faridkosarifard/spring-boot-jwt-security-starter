# Spring-Boot-Security-
Spring Boot Security with JWT and multi Role


# Swagger UI

    http://localhost:8095/swagger-ui/index.html

#  New User registration
 **POST**
 
```
    http://localhost:8086/user/registration
```
Body
```json
{
    "firstName": "First Name",
    "lastName": "Last Name",
    "username": "Username",
    "password": "password",
    "email": "Email@gmail.com",
    "use2FA": "false"
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
    "email": "Email@gmail.com"
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
    "oldPassword":"Old Password",
    "newPassword":"New Password",
    "email":"Email@gmil.com"
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
    "username": "Username",
    "password": "Password" 
}
```
