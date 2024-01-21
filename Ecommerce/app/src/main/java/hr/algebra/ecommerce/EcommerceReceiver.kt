package hr.algebra.ecommerce

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.ecomerce2.framework.startActivity

class EcommerceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startActivity<MainActivity>()
    }
}