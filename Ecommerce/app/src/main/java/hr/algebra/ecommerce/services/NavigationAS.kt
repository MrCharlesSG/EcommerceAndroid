package hr.algebra.ecommerce.services

import android.content.Context
import hr.algebra.ecommerce.auth.AuthManagerRepository
import hr.algebra.ecommerce.utils.UrlValidator

class NavigationAS(private val context: Context) {

    fun canNavigate(id: Int): Boolean =
        !UrlValidator.INSTANCE.needsAuth(id) ||
            UrlValidator.INSTANCE.needsAuth(id) &&
            AuthManagerRepository.INSTANCE.getAuthManager().isUserLogged()


}