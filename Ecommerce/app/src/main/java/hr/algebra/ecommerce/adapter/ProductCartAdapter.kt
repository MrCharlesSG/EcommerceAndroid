package hr.algebra.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.ecommerce.Observer
import hr.algebra.ecommerce.R
import hr.algebra.ecommerce.model.CartElementEcommerce
import hr.algebra.ecommerce.ui.sheets.BottomProductSheetFragment
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class ProductCartAdapter(
    private val context: Context,
    private val cart: List<CartElementEcommerce>,
    private val observer: Observer ? = null
) : RecyclerView.Adapter<ProductCartAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvProductName)
        private val tvPrice = itemView.findViewById<TextView>(R.id.tvProductPrice)
        private val tvQuantity = itemView.findViewById<TextView>(R.id.tvProductQuantity)
        private val ivImage = itemView.findViewById<ImageView>(R.id.ivProductImage)

        fun bind(cart: CartElementEcommerce) {
            tvTitle.text = cart.product.title
            tvPrice.text = (cart.product.price).toString() + "€"
            tvQuantity.text = "x"+ cart.quantity.toString()
            if(cart.product.images.isNotEmpty()){
                Picasso.get()
                    .load(File(cart.product.images[0]))
                    .error(R.drawable.error_loading_image)
                    .transform(RoundedCornersTransformation(50, 3))
                    .into(ivImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.product_cart_card, parent, false)
        )
    }

    override fun getItemCount() = cart.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            startBottomSheet(position)
        }
        // holder.setCardListener(position, context, products[position])
        holder.bind(cart[position])
    }

    private fun startBottomSheet(position: Int) {
        val bottomSheetFragment = BottomProductSheetFragment(cart[position].product, cart[position], observer)
        bottomSheetFragment.show(
            (context as AppCompatActivity).supportFragmentManager,
            bottomSheetFragment.tag
        )
    }

}