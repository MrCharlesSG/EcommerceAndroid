package hr.algebra.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.ecommerce.Observer
import hr.algebra.ecommerce.R
import hr.algebra.ecommerce.model.ProductEcommerce
import hr.algebra.ecommerce.ui.sheets.BottomProductSheetFragment

class ProductAdapter(
    private val context: Context,
    private val products: List<ProductEcommerce>,
    private val observer: Observer?=null
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvProductName)
        private val tvPrice = itemView.findViewById<TextView>(R.id.tvProductPrice)
        private val rvImages = itemView.findViewById<RecyclerView>(R.id.rvImages)
        private val cvProductCard = itemView.findViewById<CardView>(R.id.cvProductCard)

        fun bind(product: ProductEcommerce){
            tvTitle.text=product.title
            tvPrice.text = product.price.toString() + "€"
            rvImages.apply{
                layoutManager = LinearLayoutManager(context)
                adapter = ImageAdapter(context, product.images)
            }
        }

        fun setCardListener(position: Int, context: Context, product: ProductEcommerce){
            val bottomSheetFragment = BottomProductSheetFragment(product)
            bottomSheetFragment.show(
                (context as AppCompatActivity).supportFragmentManager,
                bottomSheetFragment.tag
            )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.product_card, parent, false)
        )
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            startBottomSheet(position)
        }
       // holder.setCardListener(position, context, products[position])
        holder.bind(products[position])
    }

    private fun startBottomSheet(position: Int) {
        val bottomSheetFragment = BottomProductSheetFragment(
            products[position],
            null,
            observer
        )
        bottomSheetFragment.show(
            (context as AppCompatActivity).supportFragmentManager,
            bottomSheetFragment.tag
        )
    }

}