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
+- com.example.webservice
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
    |   +- ProdutcUpdate.java
```

All products and orders are saved respectively in the files *products.dat* and *orders.dat*,
located in the main project directory.

---
# 2. Products

## 2.1 Entity Classes

Class **Product** has 2 parameters:
* Name
* Price

Class **ProductUpdate** extends *Product* and it has an additional parameter:
* OldName (from *Product*)
* NewName
* Price (from *Product*)

*ProductUpdate* is used to update a product, which is found through the parameter *OldName*.

## 2.2 Methods

Description | Method
----------- | ------
Create a new product | *createProduct(Product)*
Retrieve all products | *retrieveProdcuts()*
Update a product | *updateProduct(ProductUpdate)*

---
# 3. Orders

## 3.1 Entity Class

Class **Order** has 4 parameters:
* ID
* Buyer's e-mail
* Products list
* Placed time

## 3.2 Methods

Description | Method
----------- | ------
Place an order | *placeOrder(Order)*
Calculate the amount of an order | *calculateAmount(Order)*
Retrieve all orders within a given time period | *retrieveOrders(Integer)*

---
# 4. RestController

Run the application and connect to the local address ```localhost:8080```

Path | Method | Mandatory Params | JSON Input | JSON Output
-----|--------|--------------|-------|-------
/products | POST | name, price | ```{"name": string, "price": double}``` | ```{"ID": integer, "name": string, "price": double}```
/products/{ID} | PUT | name, price | ```{"ID": integer, "name": string, "price": double}``` | ```{"ID": integer, "name": string, "price": double}```
/products | GET | - | - | ```[{"ID": integer, "name": string, "price": double}]```
/orders | POST | email, products | ```{"email": string, "products": [{"ID": integer, "name": string, "price": double}]}``` | ```{"ID": integer, "email": string, "products": [{"ID": integer, "name": string, "price": double}]}, "time": long```}
/orders/amount/{ID} | GET | - | - | ```Double```
/orders/{days} | GET | - | - | ```[{"ID": integer, "email": string, "products": [{"ID": integer, "name": string, "price": double}]}, "time": long```}]
