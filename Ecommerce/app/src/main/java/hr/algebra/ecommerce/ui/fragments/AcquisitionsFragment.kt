package hr.algebra.ecommerce.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.adapter.PurchaseAdapter
import hr.algebra.ecommerce.databinding.FragmentAcquistionsBinding
import hr.algebra.ecommerce.model.Purchase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AcquisitionsFragment : Fragment() {

    private var _binding: FragmentAcquistionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadPurchases()
    }

    private fun loadPurchases() {
        GlobalScope.launch (Dispatchers.Main) {
            //MAIN THREAD
            val purchases : List<Purchase> = withContext( Dispatchers.IO){
                val purchaseDao = (context?.applicationContext as App).getPurchasedDao()
                val purchasesEntity = purchaseDao.getAllPurchases()
                purchasesEntity.map { purchaseEntity ->
                    val productsEntities = purchaseDao.getAllProductOfPurchase(purchaseEntity._id)
                    Purchase.getFromEntity(purchaseEntity, productsEntities)
                }
            }

            //MAin Thread
            binding.rvPurchases.apply{
                layoutManager = LinearLayoutManager(requireContext())
                adapter = PurchaseAdapter(requireContext(), purchases)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAcquistionsBinding.inflate(inflater, container, false)
        return binding.root
    }
}