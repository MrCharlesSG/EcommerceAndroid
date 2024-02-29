package hr.algebra.ecommerce.services

import android.content.Context
import hr.algebra.ecommerce.ActivityHost
import hr.algebra.ecommerce.auth.AuthManagerRepository

open class ApplicationAuthenticatedService(context: Context) {
    private val activityHost : ActivityHost = (context.applicationContext as ActivityHost)
    protected suspend fun authenticateFunction(function: () -> Unit) : Boolean =
        if(AuthManagerRepository.INSTANCE.getAuthManager().isUserLogged()) {
            function()
            true
        }else{
            activityHost.showNotAuthenticatedDialogSuspend()
            false
        }

}