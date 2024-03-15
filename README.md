<!-- TOC --><a name="ecommerceandroid"></a>
# EcommerceAndroid

# V1 (frontend)

<!-- TOC --><a name="description"></a>
## Description

Final project of the subject of Aplication Development for Mobile Devices of the Software Engineering degree at Algebra University College (Zagreb). The project is an e-commerce app. It consumes an API ([https://api.escuelajs.co/api/v1/](https://fakeapi.platzi.com/en/rest/products/)https://fakeapi.platzi.com/en/rest/products/) por getting the product information. It is completely developed in Kotlin. The project had the highest grade.

<!-- TOC --><a name="user-stories"></a>
## User Stories

The user stories of the project are: 
 1. As a User I want to be able to see all the products
 2. As a user I want to be able to filter the products by it category
 3. As a user I want to be able to make a wish list
 4. As a user I want to have a shopping cart and finish it
 5. As a user I want to be able to edit the cart by removing items or increasing/reducing its quantity.
 6. As a user I want to see all the things I have bought
 7. As a user I want to see how much I have spent with a grafic

<!-- TOC --><a name="ui"></a>
## UI
The application has three main sections and the cart and the Splash Screen. It should be noted that the way a product looks is always the same except in the purchases that oviously the add to cart, add to myList and quantity is not shown.

<!-- TOC --><a name="splash-screen"></a>
### Splash Screen

<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/2e1f459b-dede-44b6-b307-1f88f3068f62"  width="225" height="500" />

<!-- TOC --><a name="shop"></a>
### Shop

<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/1ae9e2a5-9af5-40ca-b997-696d0229bbbf"  width="225" height="500" />
<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/f9f2dd24-0740-422d-a61b-a485d4446019"  width="225" height="500" />

<!-- TOC --><a name="mylist"></a>
### MyList

<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/756a2afa-5036-4a4a-8bd3-f55df21c2545"  width="225" height="500" />


<!-- TOC --><a name="profile"></a>
### Profile
<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/7e93aa22-311e-456f-8b50-ddeea54abd0a"  width="225" height="500" />
<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/79ceffe1-5399-4129-9f30-4a03ee1a9a25"  width="225" height="500" />
<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/c0dbee2f-b88f-439c-9ca3-8042b8af8444"  width="225" height="500" />
<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/18098150-6069-4190-a372-a5319e106880"  width="225" height="500" />


<!-- TOC --><a name="cart"></a>
### Cart

<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/56af573c-eb70-4759-a30e-9890aa46564a"  width="225" height="500" />


<!-- TOC --><a name="classes-description"></a>
## Classes Description

<!-- TOC --><a name="consuming-api"></a>
### Consuming API

For the consume of the API I used the worker library. Basically the `SplashScreenActivity` add the `EcommerceWorker` in queue wich call the class `EcommerceFetcher` that basically fecth all the products with the retrofit library and store the products in the local database. When the fetcher finish he sends a broadcast to the `EcommerceReceiver` wich starts the `MainActivity`. 

<!-- TOC --><a name="storing-data"></a>
### Storing Data

For storing the data I use the Room library that stores localy the data. For this, I have created for each of the models its respective entity class.
Every time the application starts it eliminate all the products of the data base and store the ones fetched from the API. The wish list database is a local database, never deleted that just store the id of the products. If a product of the wish list is not anymore in the API products it is removed.
For storyng the purchases there is another independent entry in the database. For each of the purchases I store the total price and a Id that it is the date of the purchase (parsed to long). The purchases stores the products in a diferent table of the other products so they are never eliminated. With all of this information storage of the purchases the app can display the statistics.
The cart is never stored in the database until it is bought. 

<!-- TOC --><a name="other"></a>
### Other

<!-- TOC --><a name="observer-pattern"></a>
#### Observer Pattern

For the actualization of the UI I use the Observer Pattern that is implemented, usually, by the main fragments. 

<!-- TOC --><a name="controller"></a>
#### Controller

The controller (`App`) has the current information of the cart and all the DAOs necessarry for the utilization of the app.

<!-- TOC --><a name="categroy"></a>
#### Categroy

For displaying category I have a horizontal `RecicledView` that uses the `CategoryAdapter`. This adapter for knowing which category is selected  uses the static value of the `CategoryEcommerce` class that store that value. This adapter is the perfect example of the implementation of the Obserever Pattern, becouse when ever a category is selected the list of products must know that.

<img width="385" alt="image" src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/00a285e5-f3b2-4d28-9d6e-2f2a838eb5e0">

<!-- TOC --><a name="list"></a>
#### List

For showing the correct list of products, both `MyListFragment` and `ShopFragment` works the same. They use the products list that store all the product and the products to show that is always updated in the method `updateView` of the `Observer` interface.

# V2 (Authorization)

## Introduction

It is motivated by the need to use an "advanced" project for a subject called Advanced Programming Paradigms. In this subject it will be explore testing  among many other things. For that we have to use a project where there is a business layer. In the subject there will be also cover the refactoring and good principles for coding, that is why in this V2 is not all finished.

The pillar on which this version is based is the Authorization of the user. With so, there will be some differences (in functionality terms) between the anonymous and the authenticated user. 

Basically, now, the anonymous user can only see the list of all products, see its details and add them to the cart, but is unable to add to *my list* products, access to the *profile* and *my list* section and buy and manage the cart.

## How was done (technically)?

Basically now the controller and all the layers that used to use the `daos`, now use a brand new `service layer`, with so, there is a new **`Service Application Class`** for each of the functionalities that used the `daos` as could be the classes `CartSA`,`PurchasesSA`, or `MyListSA`. This `service layer` now is the only one that access to the `Data Access Layer` that also contains all the way the authentication and store of info in cloud is done.

### Firebase

Firebase authentication, real time database and storage is used in the application for what each one is required: 

- Authentication is used only for the user (where is only store the email). The only way of auth is via email.
- Storage for storaging the images of the purchased products.
- Real Time Database is used for storing every thing can be store in a json

### Login

For login, firebase serve a very easy way for manging it. I have created a singleton class `AuthManagerRepository` and a `AuthManager` interface, so the Dependency Inversion principle of SOLID principles is maintainig.

```kotlin
enum class AuthManagerRepository {
    INSTANCE;

    private val authManager: AuthManager = FirebaseAuthManager()
    
    fun getAuthManager() : AuthManager = authManager
}
```

```kotlin
interface AuthManager {

    fun isUserLogged() : Boolean
    fun getUserLogged(): FirebaseUser?
    fun logOut()
    
    //In future this must be in other interface
    fun getDataBaseReference(reference: String=""): DatabaseReference 
    
    fun registerUserWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun logInUserWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
}
```

When ever a user try to access to a service that needs authentication a dialog is shown

<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/308caa30-a112-48d4-9779-f531a93ae2cb" width="225" height="500" />


The login and sign in window looks like this:

<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/69e9425f-39a2-4582-bbec-708c9c39dd7b" width="225" height="500" />
<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/22e2afc4-43f9-443e-aa97-ddb8812a5bd8" width="225" height="500" />

Now in the profile section the user is able to logout

<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/e6d9c74e-22a4-4f9d-879d-395c3d5f827f" width="225" height="500" />
<img src="https://github.com/MrCharlesSG/EcommerceAndroid/assets/94635721/3c79e19e-478b-449e-8f9e-11c9b8e97203" width="225" height="500" />

### Storage

The images of all the product purchases are downloaded in the Firebase Storage service.

### Real Time Database

The reason why is used the Real Time Database is becouse the other option (Firebase Cloud Storage) was much powerfull and I obiously don’t need (for this project) that much resources.

The information is saved in a JSON like this:

```json
{
  "Users": {
    "3ie0TYtXUDdDsqiW72H8bw1FUwe2": {
      "myList": {
        "1": 1,
        "2": 2,
        "28": 28,
        "29": 29,
        "30": 30,
        "33": 33
      },
      "purchases": {
        "19795": {
          "_id": 19795,
          "price": 89,
          "products": [
            {
              "_id": 2,
              "product": {
                "category": {
                  "id": 1,
                  "image": "https://i.imgur.com/QkIa5tT.jpeg",
                  "name": "Clothes",
                  "selected": false
                },
                "description": "Elevate your casual wardrobe with our Classic Red Pullover Hoodie. Crafted with a soft cotton blend for ultimate comfort, this vibrant red hoodie features a kangaroo pocket, adjustable drawstring hood, and ribbed cuffs for a snug fit. The timeless design ensures easy pairing with jeans or joggers for a relaxed yet stylish look, making it a versatile addition to your everyday attire.",
                "id": 2,
                "images": [
                  "-1837684405_1710350965850"
                ],
                "inMyList": true,
                "price": 10,
                "title": "Classic Red Pullover Hoodie"
              },
              "quantity": 2
            }
            ......
```

Basically the `myList` only stores the ids of each product and the `purchases` store the total price of the purchase, the date (as the ID is still the date but converted in long) and all the products with its quantities. As the image is stored in firebase, the images string of the products are just the name of the image in Firebase Storage.

## Future Work

In future it will be done the test for the hole project, a refactoring of the code and some other features. All of this will be part of the subject Advanced Programming Paradigms
