package hr.algebra.ecommerce.ui.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hr.algebra.ecommerce.adapter.ProductCartAdapter
import hr.algebra.ecommerce.databinding.PurchasedProductsSheetBinding
import hr.algebra.ecommerce.model.Purchase
import hr.algebra.ecommerce.utils.getMoneyFormat
import java.time.LocalDate

class PurchasedProductsSheet (
    private val purchase: Purchase
) : BottomSheetDialogFragment() {

    private var _binding: PurchasedProductsSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PurchasedProductsSheetBinding.inflate(inflater, container, false)
        binding.tvTotalPrice.text = getMoneyFormat(0)
        bind()
        return binding.root
    }

    private fun bind() {
        binding.progressBar.visibility = View.GONE
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter =
                purchase.products?.let { ProductCartAdapter(requireContext(), it) }
        }
        binding.tvTotalPrice.text = getMoneyFormat(purchase.price)
        binding.tvTitle.text = LocalDate.ofEpochDay(purchase._id).toString()
    }

}
