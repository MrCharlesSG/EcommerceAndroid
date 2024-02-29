package hr.algebra.ecommerce.ui.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.Observer
import hr.algebra.ecommerce.adapter.ProductCartAdapter
import hr.algebra.ecommerce.dal.purchase.PurchaseEntity
import hr.algebra.ecommerce.databinding.CartSheetLayoutBinding
import hr.algebra.ecommerce.model.CartElementEcommerce
import hr.algebra.ecommerce.utils.getMoneyFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class CartSheetFragment () : BottomSheetDialogFragment(), Observer {
    private var _binding: CartSheetLayoutBinding ?=null
    private val binding get() = _binding!!
    private var cart : MutableList<CartElementEcommerce> = mutableListOf()
    private var totalPrice = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CartSheetLayoutBinding.inflate(inflater, container, false)
        binding.tvTotalPrice.text = getMoneyFormat(0)
        binding.progressBar.visibility= View.VISIBLE
        setupListeners()
        bind()
        return binding.root
    }

    private fun bind() {
        cart = (context?.applicationContext as App).getCart().getList()
        totalPrice = (context?.applicationContext as App).getCart().getTotalPrice()
        binding.progressBar.visibility = View.GONE
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ProductCartAdapter(requireContext(), cart, this@CartSheetFragment)
        }
        binding.tvTotalPrice.text = getMoneyFormat(totalPrice)
    }

    override fun updateView() {
        bind()
    }

    private fun setupListeners() {
        binding.btnClear.setOnClickListener {
            clearCart()
        }
        binding.btnBuy.setOnClickListener{
            buyCart()
        }
    }

    private fun buyCart() {
        val cart = (context?.applicationContext as App).getCart()
        val purchaseID = LocalDate.now().toEpochDay()
        val purchaseEntity = PurchaseEntity(purchaseID, cart.getTotalPrice())
        val productListEntity = cart.getProductListEntity(purchaseID)
        val daoPurchase = (context?.applicationContext as App).getPurchasedDao()

        GlobalScope.launch (Dispatchers.IO) {
            val purchaseEntityFromDB = daoPurchase.getPurchaseById(purchaseID)
            if(purchaseEntityFromDB!=null){
                purchaseEntity.price+= purchaseEntityFromDB.price
            }
            daoPurchase.insertPurchase(purchaseEntity)
            daoPurchase.insertProducts(productListEntity)
        }
        clearCart()
    }

    private fun clearCart() {
        (context?.applicationContext as App).getCartAS().clearCart()
        dismiss()
    }
}