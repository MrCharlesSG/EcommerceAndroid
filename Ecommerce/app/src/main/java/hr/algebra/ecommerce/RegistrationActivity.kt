package hr.algebra.ecommerce

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import hr.algebra.ecomerce2.framework.startActivity
import hr.algebra.ecommerce.auth.AuthManagerRepository
import hr.algebra.ecommerce.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setupListeners()
    }

    private fun setupListeners() {
        binding.tvLoginNow.setOnClickListener{
            startActivity<LoginActivity>()
        }

        binding.ivClose.setOnClickListener{
            startActivity<MainActivity>()
        }

        binding.btnSubmit.setOnClickListener {
            binding.pbProgress.visibility = View.VISIBLE
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            AuthManagerRepository.INSTANCE.getAuthManager().registerUserWithEmailAndPassword(
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