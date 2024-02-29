package hr.algebra.ecommerce.utils

import android.app.Activity
import androidx.navigation.NavController
import hr.algebra.ecommerce.R
import hr.algebra.ecommerce.ui.NotAuthenticatedDialog

enum class UrlValidator {

    INSTANCE;

    private val elementsWithCart = listOf<Int>(
        R.id.navigation_shop
    )

    private val elementsWithNoBack = listOf<Int>(
        R.id.navigation_shop,
        R.id.navigation_mylist,
        R.id.navigation_profile
    )

    private val elementsWithNoNeedOfAuth = listOf<Int>(
        R.id.navigation_shop
    )

    fun needsCart(id: Int) : Boolean = elementsWithCart.contains(id)
    fun needsBack(id: Int) : Boolean = !elementsWithNoBack.contains(id)
    fun needsAuth(id: Int) : Boolean = !elementsWithNoNeedOfAuth.contains(id)
    fun navigationFailed(navController: NavController, activity: Activity) {
        NotAuthenticatedDialog(activity).show()
        navController.navigate(R.id.navigation_shop)
    }

}