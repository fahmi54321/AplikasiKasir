package com.android.aplikasikasir.preview.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.android.aplikasikasir.databinding.ItemProdukBinding
import com.android.aplikasikasir.helpers.Helpers.formatPrice
import com.android.aplikasikasir.produk.model.ResponseProduk
import android.widget.TextView
import com.android.aplikasikasir.databinding.ItemProdukSelectionBinding
import com.android.aplikasikasir.preview.model.Preview
import java.lang.NumberFormatException


class ProdukSelectionAdapter(
        var data: List<Preview?>?
) : RecyclerView.Adapter<ProdukSelectionAdapter.CartPrelovedViewHolder>() {


    class CartPrelovedViewHolder(val binding: ItemProdukSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Preview?) {
            with(binding) {
                binding.txtNamaProduk.text = item?.namaProduk
                binding.txtKalkulasi.text = "${item?.harga} * ${item?.jumlah}"
                var total = item?.harga?.toInt()?.times(item?.jumlah.toInt())
                binding.txtTotal.formatPrice(total.toString())
                if (total?.equals(0) == true){
                    binding.txtKalkulasi.visibility = View.GONE
                    binding.txtTotal.visibility = View.GONE
                    binding.txtNamaProduk.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartPrelovedViewHolder {
        val view = ItemProdukSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartPrelovedViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartPrelovedViewHolder, position: Int) {
        var item = data?.get(position)
        holder.bind(item)

    }

    override fun getItemCount(): Int = data?.size ?: 0
}