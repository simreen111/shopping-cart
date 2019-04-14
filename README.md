# Shopping Cart BaaS

Below APIs are supported : 

### GET /products ###
Request -

```curl -X GET \
  http://localhost:8083/products \
  -H 'Content-Type: application/json'
```
Response -

```
[
    {
        "productName": "Apple",
        "productID": 1,
        "productCount": 10
    },
    {
        "productName": "Orange",
        "productID": 2,
        "productCount": 20
    },
    {
        "productName": "Cherry",
        "productID": 3,
        "productCount": 30
    },
    {
        "productName": "Mango",
        "productID": 4,
        "productCount": 40
    }
]
```

### GET /products/{id} ###
Request - 

```curl -X GET \
  http://localhost:8083/products/1 \
  -H 'Content-Type: application/json'
```
Response - 

```
{
    "productName": "Apple",
    "productID": 1,
    "productCount": 10
}
```

### PUT /products ###
```curl -X PUT \
  http://localhost:8083/products \
  -H 'Content-Type: application/json' \
  -d '{
    "productName": "Guava",
    "productID": 4,
    "productCount": 80
}'
```

### DELETE /products/{id} ###
```curl -X DELETE \
  http://localhost:8083/products/4 \
  -H 'Content-Type: application/json'
```
  
### POST /checkout/{id} ###
```curl -X POST \
  http://localhost:8083/checkout/4 \
  -H 'Content-Type: application/json' \
  -d '{
    "quantity": 40
}'
```

Note: You can't order more than the current inventory level of a product. For example, if there are currently 4 Bananas in the inventory, then a checkout call with quantity 6 will fail with below 400 Bad Request error -

```
Ordered product quantity exceeds current product inventory count
```