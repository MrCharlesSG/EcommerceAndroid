package hr.algebra.ecommerce

import android.app.Activity

interface ActivityHost {
    fun setActivity(activity: Activity)

    fun showNotAuthenticatedDialog()
    suspend fun showNotAuthenticatedDialogSuspend()
}