package hr.algebra.ecommerce.auth

import com.google.firebase.auth.FirebaseAuth

internal class FirebaseAuthManager : AuthManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun isUserLogged(): Boolean  = auth.currentUser != null
    override fun getUserLogged() = auth.currentUser

    override fun logOut() {
        auth.signOut()
    }

    override fun registerUserWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if(!areValidValues(email, password, onFailure)) return
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Authentication failed")
                }
            }
    }

    override fun logInUserWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if(!areValidValues(email, password, onFailure)) return
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Authentication failed")
                }
            }
    }

    private fun areValidValues(email: String, password: String, onFailure: (String) -> Unit) : Boolean{
        if(email.isEmpty()){
            onFailure("Email Is Required")
            return false
        }

        if(password.isEmpty()){
            onFailure("password Is Required")
            return false
        }
        return true
    }
}