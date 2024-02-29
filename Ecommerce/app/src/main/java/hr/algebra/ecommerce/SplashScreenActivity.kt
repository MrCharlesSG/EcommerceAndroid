package hr.algebra.ecommerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.algebra.ecomerce2.framework.applyAnimation
import hr.algebra.ecomerce2.framework.callDelayed
import hr.algebra.ecomerce2.framework.isOnline
import hr.algebra.ecommerce.api.EcommerceWorker
import hr.algebra.ecommerce.databinding.ActivitySplashScreenBinding

private const val DELAY = 3000L

const val DATA_IMPORTED = "hr.algebra.ecomerce2.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        binding.ivSplashScreenCart.applyAnimation(R.anim.pass_through)
        binding.loadingSplashScreen.applyAnimation(R.anim.blink)
    }

    private fun redirect() {
        if (isOnline()) {
            WorkManager.getInstance(this).apply {

                enqueueUniqueWork(
                    DATA_IMPORTED,
                    ExistingWorkPolicy.KEEP,
                    OneTimeWorkRequest.from(EcommerceWorker::class.java)
                )
            }
        } else {
            binding.loadingSplashScreen.text = getString(R.string.no_internet)
            callDelayed(DELAY) {
                finish()
            }
        }
    }
}
