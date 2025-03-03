
### Requirements 
There are 3 things I need to achieve,
1. get all phone numbers
2. get all phone numbers for customer 
3. activate phone number 

### Options,
1. GET /phone return all phone numbers. This could be large set of data and need a way of limiting. Should implement pagination.
2. Two options GET /customer/{customerId}/phone return customers phone numbers or GET /phone?customerId={customerId} where allow ability to filer by customerId. 
3. PATCH /phone/{phoneId} BODY: { "status": "ACTIVE" }. Other options is to use PUT /phone but that is not very safe given we only update status.

TODO:
Convert status to enum 
Need to do error handling

