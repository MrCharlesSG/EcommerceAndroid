package hr.algebra.ecommerce.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.Observer
import hr.algebra.ecommerce.adapter.CategoryAdapter
import hr.algebra.ecommerce.adapter.ProductAdapter
import hr.algebra.ecommerce.databinding.FragmentMyListBinding
import hr.algebra.ecommerce.model.CategoryEcommerce
import hr.algebra.ecommerce.model.ProductEcommerce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyListFragment : Fragment(), Observer {

    private var _binding: FragmentMyListBinding? = null
    private val binding get() = _binding!!
    private lateinit var products: List<ProductEcommerce>
    private lateinit var productsToShow: List<ProductEcommerce>
    private lateinit var categories: List<CategoryEcommerce>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
    }

    private fun load() {
        binding.pbProgress.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Main) {
            loadCategories()
            loadProducts()
        }
    }

    private fun bindProducts() {
        if(CategoryEcommerce.categorySelected.id != CategoryEcommerce.defaultCategory.id){
            val list = mutableListOf<ProductEcommerce>()
            products.forEach {
                if(it.category?.id == CategoryEcommerce.categorySelected.id){
                    list.add(it)
                }
            }
            productsToShow = list
        }else{
            productsToShow = products
        }
        binding.rvProducts.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter =  ProductAdapter(requireContext(), productsToShow, this@MyListFragment)
        }
    }

    private fun bindCategories() {
        var layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val myList = binding.rvCategories
        myList.layoutManager = layoutManager
        myList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CategoryAdapter(requireContext(), categories, this@MyListFragment)
        }
    }

    private suspend fun loadCategories() {
        val categories: List<CategoryEcommerce> = withContext(Dispatchers.IO) {
            (context?.applicationContext as App).getCategoryAS().getCategories()
        }
        this.categories = categories
        bindCategories()
    }

    private suspend fun loadProducts() {
        val products: List<ProductEcommerce> = withContext(Dispatchers.IO) {
            (context?.applicationContext as App).getMyListAS().getMyList()
        }
        this.products = products
        bindProducts()
        binding.pbProgress.visibility = View.GONE
    }

    override fun updateView() {
        binding.pbProgress.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Main) {
            loadProducts()
            bindProducts()
        }
    }
}