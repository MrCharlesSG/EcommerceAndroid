package hr.algebra.ecommerce

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.ecomerce2.framework.startActivity
import hr.algebra.ecommerce.auth.AuthManagerRepository
import hr.algebra.ecommerce.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    public override fun onStart() {
        super.onStart()
        /*if(AuthManagerRepository.INSTANCE.getAuthManager().isUserLogged()){
            startActivity<MainActivity>()
        }
         */
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.tvRegisterNow.setOnClickListener {
            startActivity<RegistrationActivity>()
        }

        binding.ivClose.setOnClickListener{
            startActivity<MainActivity>()
        }

        binding.btnSubmit.setOnClickListener {
            binding.pbProgress.visibility = View.VISIBLE
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            AuthManagerRepository.INSTANCE.getAuthManager().logInUserWithEmailAndPassword(
                email,
                password,
                onSuccess = {
                    Toast.makeText(this, "Authentication Successful.", Toast.LENGTH_SHORT).show()
                    startActivity<MainActivity>()
                },
                onFailure = { message ->
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            )

        }
    }

}