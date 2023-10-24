# Spring-Boot-Security-
Spring Boot Security with JWT and multi Role
﻿

# ****POST**** New User registration
 **URL:** http://localhost:8086/user/registration
﻿

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

# **GET** Email Confirm
**URL:** http://localhost:8086/registrationConfirm?token=token
﻿

Query Params

```
token = example-token
```

# POST Forget Password
**URL:** http://localhost:8086/user/resetPassword
﻿
Body


```json
{
    "email": "farid.kosarifard@gmail.com"
}
```

# GET Rest Password Token Veryfing
**URL:** http://localhost:8086/user/forgetPassword?token=example-token
﻿
Query Params

```
token = example-token
```

# POST Change Password with Token
**URL:** http://localhost:8086/user/savePassword
﻿
Body
```json
{
    "token": "example-token",
    "newPassword": "F2123"
}
```
# POST Change Password
**URL:** http://localhost:8086/user/changePassword
﻿
Body
```json
{
    "oldPassword":"example-password",
    "newPassword":"F271@9506m",
    "email":"farid.kosarifard@gmil.com"
}
```
# GET Login
**URL:** http://localhost:8086/user/login
﻿
Body
```json
{
    "userName": "farid",
    "password": "F271@9506m" 
}
```
