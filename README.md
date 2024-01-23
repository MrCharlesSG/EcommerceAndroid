# EcommerceAndroid
 
## Description

Final project of the subject of Aplication Development for Mobile Devices of the Software Engineering degree at Algebra University College (Zagreb). The project is an e-commerce app. It consumes an API ([https://api.escuelajs.co/api/v1/](https://fakeapi.platzi.com/en/rest/products/)https://fakeapi.platzi.com/en/rest/products/) por getting the product information. It is completely developed in Kotlin. The project had the highest grade.

## User Stories

The user stories of the project are: 
 1. As a User I want to be able to see all the products
 2. As a user I want to be able to filter the products by it category
 3. As a user I want to be able to make a wish list
 4. As a user I want to have a shopping cart and finish it
 5. As a user I want to be able to edit the cart by removing items or increasing/reducing its quantity.
 6. As a user I want to see all the things I have bought
 7. As a user I want to see how much I have spent with a grafic

## UI
The application has three main sections and the cart and the Splash Screen. It should be noted that the way a product looks is always the same except in the purchases that oviously the add to cart, add to myList and quantity is not shown.

### Splash Screen

<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/2e1f459b-dede-44b6-b307-1f88f3068f62" width="200" height="400" />

### Shop

![WhatsApp Image 2024-01-21 at 13 24 14 (1)](https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/1ae9e2a5-9af5-40ca-b997-696d0229bbbf)
![WhatsApp Image 2024-01-21 at 13 24 13 (1)](https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/f9f2dd24-0740-422d-a61b-a485d4446019)

### MyList

![WhatsApp Image 2024-01-21 at 13 24 12 (2)](https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/756a2afa-5036-4a4a-8bd3-f55df21c2545)


### Profile

![WhatsApp Image 2024-01-21 at 13 24 12 (1)](https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/7e93aa22-311e-456f-8b50-ddeea54abd0a)
![WhatsApp Image 2024-01-21 at 13 24 12](https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/79ceffe1-5399-4129-9f30-4a03ee1a9a25)
![WhatsApp Image 2024-01-21 at 13 24 11](https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/c0dbee2f-b88f-439c-9ca3-8042b8af8444)
![WhatsApp Image 2024-01-21 at 13 24 14 (2)](https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/18098150-6069-4190-a372-a5319e106880)


### Cart

![WhatsApp Image 2024-01-21 at 13 24 13](https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/56af573c-eb70-4759-a30e-9890aa46564a)


## Classes Description

### Consuming API

For the consume of the API I used the worker library. Basically the `SplashScreenActivity` add the `EcommerceWorker` in queue wich call the class `EcommerceFetcher` that basically fecth all the products with the retrofit library and store the products in the local database. When the fetcher finish he sends a broadcast to the `EcommerceReceiver` wich starts the `MainActivity`. 

### Storing Data

For storing the data I use the Room library that stores localy the data. For this, I have created for each of the models its respective entity class.
Every time the application starts it eliminate all the products of the data base and store the ones fetched from the API. The wish list database is a local database, never deleted that just store the id of the products. If a product of the wish list is not anymore in the API products it is removed.
For storyng the purchases there is another independent entry in the database. For each of the purchases I store the total price and a Id that it is the date of the purchase (parsed to long). The purchases stores the products in a diferent table of the other products so they are never eliminated. With all of this information storage of the purchases the app can display the statistics.
The cart is never stored in the database until it is bought. 

### Other

#### Observer Pattern

For the actualization of the UI I use the Observer Pattern that is implemented, usually, by the main fragments. 

#### Controller

The controller (`App`) has the current information of the cart and all the DAOs necessarry for the utilization of the app.

#### Categroy

For displaying category I have a horizontal `RecicledView` that uses the `CategoryAdapter`. This adapter for knowing which category is selected  uses the static value of the `CategoryEcommerce` class that store that value. This adapter is the perfect example of the implementation of the Obserever Pattern, becouse when ever a category is selected the list of products must know that.

<img width="385" alt="image" src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/00a285e5-f3b2-4d28-9d6e-2f2a838eb5e0">

#### List

For showing the correct list of products, both `MyListFragment` and `ShopFragment` works the same. They use the products list that store all the product and the products to show that is always updated in the method `updateView` of the `Observer` interface.
