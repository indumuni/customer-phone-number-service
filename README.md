
## Version  
Java 17
Gradle 8.12.1

## API schema
[indumuni-PhoneService-1.0.0-swagger.yaml](api/indumuni-PhoneService-1.0.0-swagger.yaml)


### Requirements 
There are 3 things I need to achieve,
1. get all phone numbers
2. get all phone numbers for customer 
3. activate phone number 

### Options,
1. GET /phones return all phone numbers. This could be large set of data and need a way of limiting. Should implement pagination.
2. GET /phones?customerId={customerId} where allow ability to filer by customerId. Missing customerId means first use case and will return all phone numbers
3. PATCH /phones/{phoneId} BODY: { "status": "ACTIVE" }. Other options is to use PUT /phone but. Follow PATCH since it is more specific.

## Plugins integrated 
- Checkstyle - Code quality 
- jacoco - Test coverage

## Architecture
Followed standard practices,
- Controller, Service, Repository, Domain and model objects to expose to outside
- Added Exception handler for status update to cover phone not found scenario 
- Most of the 400's are taken care by Spring Boot

## Non functional requirements
- Correctness: Requested functionality implemented correctly as to my knowledge. Tried not to take shortcuts but for sure there are more improvement I can do. Raised condition is covered.   
- Code structure: Package them base on their responsibilities.    
- Data structures: Simple and try keep them that way
- Extensibility: Update (patch) is extensible. Try to implement single responsibility in mind. 
- Maintainability: Latest dependencies are used, well structured, standard arachitecture is used. 
- Test coverage: Pretty good test coverage with unit tests and integration test.
- Performance: 

## Command 

```bash
## build project
./gradlew clean build
```

```bash
## run spring boot app
./gradlew bootRun
```


After deploying local, below are some API requests
### Find all phone numbers
```bash
curl -X GET http://localhost:8080/api/phones
```

### Find all phone numbers paginated
```bash
curl -X GET 'http://localhost:8080/api/phones?pageNo=0&pageSize=2'
```

### Find phones for customer 8271
```bash
curl -X GET 'http://localhost:8080/api/phones?customerId=8271&pageNo=0&pageSize=100'
```

### Bad Request due to bad customerId
```bash
curl -X GET 'http://localhost:8080/api/phones?customerId=A8271&pageNo=0&pageSize=100'
```

### Update status
```bash
curl -X PATCH http://localhost:8080/api/phones/5 \
-H "Content-Type: application/json" \
-d '{
    "status": "ACTIVE"
}'
```

### Bad Update status, Not Found with invalid phone id
```bash
curl -X PATCH http://localhost:8080/api/phones/10 \
-H "Content-Type: application/json" \
-d '{
    "status": "ACTIVE"
}'
```