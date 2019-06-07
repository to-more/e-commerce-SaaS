## Merchant

A merchant of the e-commerce system.

Properties
----------
| Name                                         | Description                                | Type    | Required
| -------------------------------------------- | ------------------------------------------ | ------- | -------------------------------------------------------------------------------------------------------------------------------------------------
| id                                           | Unique numeric identifier for merchant     |  Number |  yes 
| name                                         | Name of merchant                           |  String |  yes
| email                                        | E-mail of the merchant                     |  String |  yes
| phone                                        | Phone number of the merchant               |  String |  yes
| address                                      | Address of the customer                    |  String |  yes


### Merchant sub resources

### Sale

A purchase of the merchant to register 

Properties
----------
| Name                                         | Description                                | Type    | Required
| -------------------------------------------- | ------------------------------------------ | ------- | -------------------------------------------------------------------------------------------------------------------------------------------
| id                                           | Unique numeric identifier for sale         | Numeric |  yes 
| product                                      | Name of the product sold                   | String  |  yes
| amount                                       | Amount of the sale                         | Float   |  yes

### Plan

A plan to associate to the merchant

Properties
----------
| Name                                         | Description                                | Type      | Required
| -------------------------------------------- | ------------------------------------------ | --------- | -----------------------------------------------------------------------------------------------------------------------------------------
| id                                           | Unique numeric identifier for plan         |  Numeric  |    yes 
| name                                         | Name of the plan                           |  String   |    yes
| fee                                          | Fee to apply on the sale                   |  Float    |    yes


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

GET /merchants/{id}/bill

Get a merchant's bill for the current month

##### Response

```HTTP/1.1 200```

```json
{
  "code": "3860b686-283d-4f6e-bb04-c6be0c71d70b",
  "creation_date": "2019-06-07",
  "total_amount": 1756.3999999999999,
  "total_fee": 35.128
}
```