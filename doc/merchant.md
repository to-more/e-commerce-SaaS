## Merchant

A merchant of the e-commerce system.

Properties
----------
| Name                                         | Description                                | Required
| -------------------------------------------- | ------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------
| id                                           | Unique numeric identifier for merchant     |  yes 
| name                                         | Name of merchant                           |  yes
| email                                        | E-mail of the merchant                     |  yes
| phone                                        | Phone number of the merchant               |  yes
| address                                      | Address of the customer                    |  yes


### Merchant sub resources

### Sale

A purchase of the merchant to register 

Properties
----------
| Name                                         | Description                                | Required
| -------------------------------------------- | ------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------
| id                                           | Unique numeric identifier for sale         |  yes 
| product                                      | Name of the product sold                   |  yes
| amount                                       | Amount of the sale                         |  yes

### Plan

A plan to associate to the merchant

Properties
----------
| Name                                         | Description                                | Required
| -------------------------------------------- | ------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------
| id                                           | Unique numeric identifier for plan         |  yes 
| name                                         | Name of the plan                           |  yes
| fee                                          | Fee to apply on the sale                   |  yes


Response codes
----------

| Code                                         | Description
| -------------------------------------------- | -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| 201                                          | Indicates a successful creation
| 200                                          | Success for GET, DELETE, PUT
| 404                                          | Error when a resource is not found for GET, DELETE, PUT
| 409                                          | Conflicting creation, indicates that a merchant already exist with the same phone or email 
| 400                                          | Malformed  request or for invalid constraints


## Endpoints

#### GET /merchants

##### Response

Retrieves a merchant with sales

```HTTP/1.1 200```

```json
{
  "id": 3,
  "name": "merchant",
  "creationDate": "2019-06-04",
  "email": "merchant@email.com",
  "phone": "1164304572",
  "address": "Address 1234 1D",
  "plan": {
    "id": 1,
    "name": "Basic",
    "fee": 2
  },
  "sales": [
    {
      "id": 1,
      "product": "prueba",
      "amount": 189.1,
      "creationDate": "2019-06-04"
    },
    {
      "id": 3,
      "product": "prueba",
      "amount": 189.1,
      "creationDate": "2019-06-04"
    }
  ],
  "balance": 0,
  "credit": 0
}

```

#### POST /merchants

Creates a new merchant

##### Request

```json
{
    "id": 1,
    "name": "merchant",
    "email": "merchant@email.com",
    "phone": "21643045712",
    "address": "Address 1234 1D"
}
```

##### Response

Headers
```
HTTP/1.1 201 
Location: http://$HOST/merchants/{id}
```

```json
{
    "id": 1,
    "name": "merchant",
    "email": "merchant@email.com",
    "phone": "21643045712",
    "address": "Address 1234 1D"
}
```

PUT /merchants/{id}

Update a merchant

##### Request

```json
{
    "id": 1,
    "name": "merchant",
    "email": "merchant@email.com",
    "phone": "21643045712",
    "address": "Address 1234 1D"
}
```

##### Response

```HTTP/1.1 200```

```json
{
    "id": 1,
    "name": "merchant",
    "email": "merchant@email.com",
    "phone": "21643045712",
    "address": "Address 1234 1D"
}
```

DELETE /merchants/{id}

Remove a merchant

```HTTP/1.1 200```

PUT /merchants/{id}/sale

Register a merchant sale

##### Request

```json
{
 "id": 23,
 "product": "prueba",
 "amount": 189.10
}
```

##### Response

```HTTP/1.1 200```

```json
{
  "id": 3,
  "name": "merchant",
  "creationDate": "2019-06-04",
  "email": "merchant@email.com",
  "phone": "1164304572",
  "address": "Address 1234 1D",
  "plan": {
    "id": 1,
    "name": "Basic",
    "fee": 2
  },
  "sales": [
    {
      "id": 1,
      "product": "prueba",
      "amount": 189.1,
      "creationDate": "2019-06-04"
    },
    {
      "id": 3,
      "product": "prueba",
      "amount": 189.1,
      "creationDate": "2019-06-04"
    }
  ],
  "balance": 0,
  "credit": 0
}
```

PUT /merchants/{id}/plan

Update merchant plan

##### Request

```json
{ 
  "id": 1, 
  "name": "Medium",   
  "fee": 0.5 
}
```
##### Response

```HTTP/1.1 200```

```json
{
  "id": 3,
  "name": "merchant",
  "creationDate": "2019-06-04",
  "email": "merchant@email.com",
  "phone": "1164304572",
  "address": "Address 1234 1D",
  "plan": {
    "id": 1,
    "name": "Basic",
    "fee": 2
  },
  "sales": [
    {
      "id": 1,
      "product": "prueba",
      "amount": 189.1,
      "creationDate": "2019-06-04"
    },
    {
      "id": 3,
      "product": "prueba",
      "amount": 189.1,
      "creationDate": "2019-06-04"
    }
  ],
  "balance": 0,
  "credit": 0
}
```