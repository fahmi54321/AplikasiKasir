package com.android.aplikasikasir.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.aplikasikasir.databinding.ItemHistoryBinding
import com.android.aplikasikasir.helpers.Helpers.formatPrice
import com.android.aplikasikasir.history.model.ResponseHistory


class HistoryAdapter(
        var data: List<ResponseHistory?>?,
        var onClick:onClickListener
) : RecyclerView.Adapter<HistoryAdapter.CartPrelovedViewHolder>() {

    class CartPrelovedViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseHistory?) {
            with(binding) {
                binding.txtTanggal.text = item?.tanggal
                binding.txtTotal.formatPrice(item?.harga.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartPrelovedViewHolder {
        val view = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartPrelovedViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartPrelovedViewHolder, position: Int) {
        var item = data?.get(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onClick.item(item)
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    interface onClickListener{
        fun item(data: ResponseHistory?)
    }
}