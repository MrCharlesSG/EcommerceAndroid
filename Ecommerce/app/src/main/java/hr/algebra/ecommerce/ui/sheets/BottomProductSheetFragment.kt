package hr.algebra.ecommerce.ui.sheets

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.Observer
import hr.algebra.ecommerce.R
import hr.algebra.ecommerce.adapter.ImageAdapter
import hr.algebra.ecommerce.dal.cart.CartEntity
import hr.algebra.ecommerce.dal.mylist.MyListEntity
import hr.algebra.ecommerce.databinding.BottomProductSheetLayoutBinding
import hr.algebra.ecommerce.model.CartElementEcommerce
import hr.algebra.ecommerce.model.ProductEcommerce
import hr.algebra.ecommerce.utils.getMoneyFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class BottomProductSheetFragment(
    private val product: ProductEcommerce,
    private val cart: CartElementEcommerce? = null,
    private val observer: Observer? = null
) : BottomSheetDialogFragment() {

    private var _binding: BottomProductSheetLayoutBinding? = null
    private val binding get() = _binding!!
    private var textExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomProductSheetLayoutBinding.inflate(inflater, container, false)

        bindProduct()
        setupExpandableText()
        setupListeners()
        setUpCorrectView()
        setupViewMyList(product.isInMyList())
        return binding.root
    }

    private fun setUpCorrectView() {
        if(cart!=null && observer==null){
            onlyShowView()
        }
    }

    private fun bindProduct() {
        binding.tvProductName.text = product.title
        binding.tvDescription.text = product.description
        binding.tvProductPrice.text = getMoneyFormat(product.price)
        binding.tvCategory.text = product.category?.name

        binding.rvProductImages.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ImageAdapter(requireContext(), product.images)
        }
        especialBinding()
    }

    private fun especialBinding() {
        if(cart!=null){
            binding.btnAddToChart.text = "Remove"
            binding.etQuantity.text = Editable.Factory.getInstance().newEditable(cart.quantity.toString())
        }
    }

    private fun setupExpandableText() {
        binding.tvDescription.setOnClickListener {
            expandText()
        }
        binding.tvSeeMore.setOnClickListener {
            expandText()
        }
    }

    private fun expandText() {
        textExpanded = !textExpanded;

        if (textExpanded) {
            binding.tvDescription.maxLines = Integer.MAX_VALUE;
            binding.tvSeeMore.text = getString(R.string.see_less)
        } else {
            binding.tvDescription.maxLines = 2;
            binding.tvSeeMore.text = getString(R.string.see_more);
        }
    }

    private fun setupListeners() {
        binding.btnAddToChart.setOnClickListener {
            if(cart!=null){
                removeFromCart()
            }else{
                addToCart()
            }
        }

        binding.ivAddToMyList.setOnClickListener {
            addToMyList()
        }

        binding.btnLess.setOnClickListener {
            val currentQuantity = binding.etQuantity.text.toString().toInt()
            val newQuantity = if(currentQuantity - 1 <0) currentQuantity else currentQuantity - 1
            binding.etQuantity.text = Editable.Factory.getInstance().newEditable(newQuantity.toString())
        }
        binding.btnMore.setOnClickListener {
            val currentQuantity = binding.etQuantity.text.toString().toInt()
            val newQuantity = currentQuantity + 1
            binding.etQuantity.text = Editable.Factory.getInstance().newEditable(newQuantity.toString())
        }

    }

    private fun addToMyList() {
        if(product.isInMyList()){
            setupViewMyList(false)
            product.setInMyList(false)
            val myListDao = (context?.applicationContext as App).getMyListDao()
            GlobalScope.launch (Dispatchers.IO) {
                myListDao.delete(
                    MyListEntity(
                        product.id
                    )
                )
            }
        }else{
            setupViewMyList(true)
            product.setInMyList(true)
            val myListDao = (context?.applicationContext as App).getMyListDao()
            GlobalScope.launch (Dispatchers.IO) {
                myListDao.insert(
                    MyListEntity(
                        product.id
                    )
                )
            }
        }
        (context?.applicationContext as App).updateCartMyList(product)
    }

    private fun setupViewMyList(inMyList: Boolean) {
        if(!inMyList){
            binding.ivAddToMyList.setImageResource(R.drawable.heart_empty_24)
        }else{
            binding.ivAddToMyList.setImageResource(R.drawable.heart_24)
        }
    }

    private fun onlyShowView() {
        binding.llButtons.visibility = View.GONE
        binding.llQuantity.visibility = View.GONE
    }

    private fun removeFromCart() {
        val quantity = binding.etQuantity.text.toString().toInt()
        (context?.applicationContext as App).removeFromCart(
            CartEntity(
                product.id,
                quantity
            ),
            product
        )
        observer!!.updateView()
        dismiss()
    }

    private fun addToCart() {
        val quantity = binding.etQuantity.text.toString().toInt()
        (context?.applicationContext as App).addToCart(
            CartEntity(
                product.id,
                quantity
            ),
            product
        )
        dismiss()
        Toast.makeText(context, "Added to Chart", Toast.LENGTH_SHORT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(cart!=null ){
            (context?.applicationContext as App).getCart().changeQuantity(cart, binding.etQuantity.text.toString().toInt())
        }
        observer?.updateView()
        _binding = null
    }

}
