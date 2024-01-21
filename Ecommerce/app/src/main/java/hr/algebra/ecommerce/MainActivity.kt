package hr.algebra.ecommerce

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import hr.algebra.ecommerce.databinding.ActivityMainBinding
import hr.algebra.ecommerce.ui.sheets.CartSheetFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        setupListeners()
    }

    private fun setupListeners() {
        binding.ibCart.setOnClickListener {
            val cartSheet = CartSheetFragment()
            cartSheet.show(
                (this as AppCompatActivity).supportFragmentManager,
                cartSheet.tag
            )
        }
    }

    private fun initNavigation() {
        val navController = this.findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_shop) {
                binding.ibCart.visibility = View.VISIBLE
            } else {
                binding.ibCart.visibility = View.GONE
            }
            if(destination.id != R.id.navigation_shop
                && destination.id != R.id.navigation_mylist
                && destination.id != R.id.navigation_profile){
                binding.ibBack.visibility = View.VISIBLE
            }else{
                binding.ibBack.visibility = View.GONE
            }
        }
        binding.ibBack.setOnClickListener {
            navController.popBackStack()
        }
        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)
    }
}