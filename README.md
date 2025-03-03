
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
- Controller, Service, Repository, Domain model and Value objects to expose to outside
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

