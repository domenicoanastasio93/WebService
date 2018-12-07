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

It is possible to do 3 operations:
* Create a new product ( *createProduct(Product)* )
* Retrieve all products ( *retrieveProducts()* )
* Update a product ( *updateProduct(ProductUpdate)* )

---
# 3. Orders

## 3.1 Entity Class

Class **Order** has 4 parameters:
* ID
* Buyer's e-mail
* Products list
* Placed time

## 3.2 Methods

It is possible to do 3 operations:
* Place and order ( *placeOrder(Order)* )
* Calculate the amount of an order ( *calculateAmount(Order)* )
* Retrieve all orders within a given time period ( *retrieveOrders(Integer)* )

---
# 4. RestController

First of all, run the Spring application and connect to the address ```localhost:8080```.

## 4.1 Products Operations

For products operations, connect to ```\products```.

```\POST```: Create a new product
* You must send the **name** and the **price** of the product

```\PUT```: Update a product
* You must send the **name** of product to research, the **new name** and the **new price**
* If the name or the price are the same, just rewrite them

```\GET```: Retrieve all products

## 4.2 Orders Operations

For orders operations, connect to ```\orders```.

```\POST```: Place an order
* You must send the buyer's **e-mail** and the **list** of products in relation to the order
* Obviously, the list can't be empty and products must correspond to the products in the list

```\GET``` (**require** ```/amount/{ID}```): Calculate the amount of and order

```\GET``` (**require** ```{days}```): Retrieve all orders within a given time period

# 5. JSON

Path | Method | Input | Output
-----|--------|-------|-------
/products | POST | ```{"name": string, "price": double}``` | ```{"ID": integer, "name": string, "price": double}```
/products/{ID} | PUT | ```{"ID": integer, "name": string, "price": double}``` | ```{"ID": integer, "name": string, "price": double}```
/products | GET | - | ```[{"ID": integer, "name": string, "price": double}]```
/orders | POST | ```{"email": string, "products": [{"ID": integer, "name": string, "price": double}]}``` | ```{"ID": integer, "email": string, "products": [{"ID": integer, "name": string, "price": double}]}, "time": long```}
/orders/amount/{ID} | GET | - | ```Double```
/orders/{days} | GET | - | ```[{"ID": integer, "email": string, "products": [{"ID": integer, "name": string, "price": double}]}, "time": long```}]
