# WebService

This application simulates the management of a **RESTful** web service, composed of *products* and related *orders*,
by CRUD operations implemented in **Spring Boot** (RestController).

---
# 1. Structure

The application is composed of 2 main concepts:
* Products
* Orders

Therefore, it has the following layout:

```
+- .webservice
    +- Init.java
    +- WebSecurityConfig.java
    +- WebServiceApplication.java
    |
    +- orders
    |   +- Order.java
    |   +- OrdersOperation.java
    |
    +- products
    |   +- Product.java
    |   +- ProductsOperation.java
    |
    +- test
    |   +- UnitTest.java
```

All products and orders are saved respectively in *database.db*, located in *WebService/database/*.

---
# 2. Products

## 2.1 Entity Class

Class **Product** has 3 parameters:

Param | Description
----- | -----------
ID | Product ID
Name | Product name
Price | Product price

## 2.2 Methods

Class **ProductsOperation** has 3 methods:

Method | Description
------ | -----------
*createProduct(Product)* | Create a new product
*retrieveProdcuts()* | Retrieve all products
*updateProduct(Product)* | Update a product

---
# 3. Orders

## 3.1 Entity Class

Class **Order** has 4 parameters:

Param | Description
----- | -----------
ID | Product ID
Email | Buyer's email
Product | Products list related to the order
Time | Time when the order was placed

## 3.2 Methods

Class **OrdersOperation** has 3 methods:

Method | Description
------ | -----------
*placeOrder(Order)* | Place an order
*calculateAmount(Order)* | Calculate the amount of an order
*retrieveOrders(Integer)* | Retrieve all orders within a given time period

---
# 4. RestController

Run the application and connect to the local address ```localhost:8080```

Path | Params | Method | JSON Input | JSON Output
-----|--------|--------|------------|------------
/products | - | POST | ```{"name": string, "price": double}``` | ```{"ID": integer, "name": string, "price": double}```
/products/{ID} | ID = Product ID | PUT | ```{"ID": integer, "name": string, "price": double}``` | ```{"ID": integer, "name": string, "price": double}```
/products | - | GET | - | - | ```[{"ID": integer, "name": string, "price": double}]```
/orders | - | POST |  ```{"email": string, "products": [{"ID": integer, "name": string, "price": double}]}``` | ```{"ID": integer, "email": string, "products": [{"ID": integer, "name": string, "price": double}]}, "time": string}```
/orders/amount/{ID} | ID = Order ID | GET | - | ```Double```
/orders/{days} | days = Number of days ago | GET | - | ```[{"ID": integer, "email": string, "products": [{"ID": integer, "name": string, "price": double}]}, "time": string}]```

---
# 5. UnitTest

**Important:** Database will be deleted during the test. Backup file before starting (located in *WebService/database/*).

To do Unit tests just run *UnitTest* class, located in package *test*.
