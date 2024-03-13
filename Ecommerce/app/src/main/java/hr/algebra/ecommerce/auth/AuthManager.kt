package hr.algebra.ecommerce.auth

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

interface AuthManager {

    fun isUserLogged() : Boolean
    fun getUserLogged(): FirebaseUser?
    fun logOut()

    fun getDataBaseReference(reference: String=""): DatabaseReference
    fun registerUserWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun logInUserWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
}