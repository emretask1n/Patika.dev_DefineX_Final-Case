# Patika.dev - DefineX Final Case - RestAPI for Patika.bank

Loan form controller and User controller needs authorization, so first you need to Copy your jwtToken by using Authentication Controllers. Then you can Authorize yourself by simply pasting your token to Authorize button pop-up for `SWAGGER-UI`.

### Base URL

http://localhost:8086

### Swagger-UI URL

http://localhost:8086/swagger-ui/index.html

### api-docs URL

http://localhost:8086/v3/api-docs

### Authentication

This API uses JWT authentication. To authorize, use the following header:
```
Authorization: Bearer <JWT Token>
```

## API Endpoints

### User Controller

#### Update User
```
PUT /api/v1/users/{userId}
```

Update name surname or password by id

Path parameters:
- `userId`: ID of the user to update

Request Body:
- `nameSurname`: (string, required): Name and surname
- `password`: (string, required): Password

Response:
- `response`: (string): Response message

#### Delete User 
```
DELETE /api/v1/users/{userId}
```
Delete user by user id

Path parameters:
- `userId`: ID of the user to update

Response:
- `response`: (string): Response message

### Loan Form Controller

#### Get Loan Application Results
```
POST /api/v1/forms/results
```
Get loan application results and credit limits by ID Number and Birthdate

Request Body:
- `idNumber`: (string, required): ID Number
- `birthDate`: (string, required): Birthdate (format: `yyyy-mm-dd`)

Response:
- `idNumber`: (string): ID Number
- `birthDate`: (string, required): Birthdate (format: `yyyy-mm-dd`)
- `loanResult`: (string): (string): Loan result message
- `loanLimit`: (string): Loan limit message

#### Create Loan Form
```
POST /api/v1/forms/apply
```
Apply for loan with Loan Form

Request Body:
- `idNumber`: (string, required): ID Number
- `nameSurname`: (string, required): Name and surname
- `monthlyIncome`: (integer, required): Monthly income
- `phoneNumber`: (string, required): Phone number
- `birthDate`: (string, required): Birthdate (format: `yyyy-mm-dd`)
- `deposit`: (integer): Deposit amount

Response:
- `result`: (string): Result message
- `limit`: (integer): Loan limit


### Authentication Controller

#### Register a new user
```
POST /api/v1/auth/register
```
Register a new user

Request Body:
- `idNumber`: (string, required): ID Number
- `nameSurname`: (string, required): Name and surname
- `password`: (string, required): Password

Response:
- `token`: (string): JWT token
- `idNumber`: (string): ID Number
- `userId`: (integer): User ID

#### Authenticate and login a user
```
POST /api/v1/auth/login
```

Request Body:
- `idNumber`: (string, required): ID Number
- `password`: (string, required): Password

Response:
- `token`: (string): JWT token
- `idNumber`: (string): ID Number
- `userId`: (integer): User ID


### Security

This API requires a JWT token for authorization. The token should be passed in the Authorization header with the Bearer scheme.