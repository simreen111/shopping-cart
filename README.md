# Shopping Cart Backend-as-a-Service

## Below APIs are supported : 

#### GET /products ###
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

#### GET /products/{id} ###
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

#### PUT /products ###
```curl -X PUT \
  http://localhost:8083/products \
  -H 'Content-Type: application/json' \
  -d '{
    "productName": "Guava",
    "productID": 4,
    "productCount": 80
}'
```

#### DELETE /products/{id} ###
```curl -X DELETE \
  http://localhost:8083/products/4 \
  -H 'Content-Type: application/json'
```
  
#### POST /checkout/{id} ###
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

## Building Docker image and running it on container ##
#### Building a Docker image ####
```
docker build -t shopping-cart:latest ./docker
```
If everything works fine, then you should see something like below:

```
Sending build context to Docker daemon  36.09MB
Step 1/3 : FROM openjdk:8-jre-alpine
 ---> ce8477c7d086
Step 2/3 : COPY shopping-cart-0.0.1-SNAPSHOT.jar /app.jar
 ---> d6206e92a424
Step 3/3 : CMD [ "usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.jar" ]
 ---> Running in 8d8d1d40948c
Removing intermediate container 8d8d1d40948c
 ---> e547cd619ef2
Successfully built e547cd619ef2
Successfully tagged shopping-cart:latest
```
#### Running the docker image on the container ####
```
 docker run -d -p 8080:8080 shopping-cart:latest
 ```
 On successful execution you should see something like below:
 
 ```
 docker run -d -p 8080:8080 shopping-cart:latest
 ed32b7190344d852a039db988d2450e55caaf96464309a2513dac6e00776cf8d
 ```











