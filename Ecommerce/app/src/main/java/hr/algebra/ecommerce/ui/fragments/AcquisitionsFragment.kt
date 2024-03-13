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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AcquisitionsFragment : Fragment() {

    private var _binding: FragmentAcquistionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadPurchases()
    }

    private fun loadPurchases() {
        GlobalScope.launch (Dispatchers.IO) {
            (context?.applicationContext as App).getPurchaseAS().getAllPurchases{ purchases ->
                binding.rvPurchases.apply{
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = PurchaseAdapter(requireContext(), purchases)
                }
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