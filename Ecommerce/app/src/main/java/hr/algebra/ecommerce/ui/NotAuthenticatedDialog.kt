package hr.algebra.ecommerce.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import hr.algebra.ecomerce2.framework.startActivity
import hr.algebra.ecommerce.LoginActivity
import hr.algebra.ecommerce.databinding.DialogNotAuthenticatedBinding

class NotAuthenticatedDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogNotAuthenticatedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogNotAuthenticatedBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLogIn.setOnClickListener {
            context.startActivity<LoginActivity>()
        }
    }
}
