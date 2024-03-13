package hr.algebra.ecommerce.services

import android.content.Context
import com.google.firebase.database.DatabaseReference
import hr.algebra.ecommerce.ActivityHost
import hr.algebra.ecommerce.auth.AuthManagerRepository

open class ApplicationAuthenticatedService(context: Context) {
    private val activityHost : ActivityHost = (context.applicationContext as ActivityHost)

    protected val ref: DatabaseReference by lazy {
        AuthManagerRepository.INSTANCE.getAuthManager().getDataBaseReference()
    }
    protected val user by lazy {
        AuthManagerRepository.INSTANCE.getAuthManager().getUserLogged()?.uid
    }
    protected suspend fun authenticateFunction(function: () -> Unit) : Boolean =
        if(AuthManagerRepository.INSTANCE.getAuthManager().isUserLogged()) {
            function()
            true
        }else{
            activityHost.showNotAuthenticatedDialogSuspend()
            false
        }

    protected fun isAuthenticated() = AuthManagerRepository.INSTANCE.getAuthManager().isUserLogged()

}