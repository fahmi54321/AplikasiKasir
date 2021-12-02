package com.android.aplikasikasir.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.aplikasikasir.databinding.ItemHistoryBinding
import com.android.aplikasikasir.databinding.ItemProdukSelectionBinding
import com.android.aplikasikasir.details.model.ResponseDetails
import com.android.aplikasikasir.helpers.Helpers.formatPrice
import com.android.aplikasikasir.history.model.ResponseHistory


class DetailsAdapter(
        var data: List<ResponseDetails?>?
) : RecyclerView.Adapter<DetailsAdapter.CartPrelovedViewHolder>() {

    class CartPrelovedViewHolder(val binding: ItemProdukSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseDetails?) {
            with(binding) {
                binding.txtNamaProduk.text = item?.nama
                binding.txtKalkulasi.text = "Jumlah * Harga"
                binding.txtTotal.formatPrice(item?.harga.toString())
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