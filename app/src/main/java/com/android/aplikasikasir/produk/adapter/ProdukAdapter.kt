package com.android.aplikasikasir.produk.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.aplikasikasir.databinding.ItemProdukBinding
import com.android.aplikasikasir.helpers.Helpers.formatPrice
import com.android.aplikasikasir.preview.model.Preview
import com.android.aplikasikasir.produk.model.ResponseProduk
import java.lang.NumberFormatException


class ProdukAdapter(
        var data: List<ResponseProduk?>?,
        var onClick:onClickListener
) : RecyclerView.Adapter<ProdukAdapter.CartPrelovedViewHolder>() {

    private var model: Preview

    init {
        model = Preview("","","","")
    }
    var ExpAmtArray = ArrayList<String>()
    var dataProduk = ArrayMap<String,String>()
    var isOnTextChanged = false
    var ExpenseFinalTotal = 0
    var test = arrayListOf<Preview>()

    class CartPrelovedViewHolder(val binding: ItemProdukBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseProduk?) {
            with(binding) {
                binding.txtNamaProduk.text = item?.nama
                binding.txtHargaProduk.formatPrice(item?.harga?:"")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartPrelovedViewHolder {
        val view = ItemProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartPrelovedViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartPrelovedViewHolder, position: Int) {
        var item = data?.get(position)
        holder.bind(item)

        holder.binding.edtJumlah.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isOnTextChanged = true
            }

            override fun afterTextChanged(editable: Editable?) {
                ExpenseFinalTotal = 0
                if (isOnTextChanged){
                    isOnTextChanged = false

                    try {
                        ExpenseFinalTotal = 0
                        for (i in 0..position){
                            var inposition1 = position
                            if (i != position){
                                ExpAmtArray.add("0")
                                dataProduk.put("0","0")
                            }else{
                                ExpAmtArray.add("0")
                                var total = editable.toString().toInt()
                                var harga = total.times(item?.harga?.toInt()?:0)
                                ExpAmtArray.set(inposition1,harga.toString())
                                break
                            }
                        }

                        for (i in ExpAmtArray.indices?:0..1){
                            var tempTotalExpenase = ExpAmtArray.get(i).toInt()
                            ExpenseFinalTotal = ExpenseFinalTotal + tempTotalExpenase
                        }
                        dataProduk.put(ExpenseFinalTotal.toString(),item?.nama)

                        model = Preview(
                            item?.id?:"",
                            item?.nama?:"",
                            item?.harga?:"",
                            editable.toString()
                        )
                        test.add(model)

                        onClick.item(ExpenseFinalTotal,test)
                        Log.i("dataProduk","${test}")
                    }catch (e: NumberFormatException){
                        ExpenseFinalTotal = 0
                        for (i in 0..position){
                            Log.d("TimesRemoved", " : " + i);
                            var newposition = position
                            if (i == newposition){
                                ExpAmtArray.set(newposition,"0")
//                                test.set(newposition,model)
                            }
                        }

                        for (i in ExpAmtArray.indices?:0..1){
                            var tempTotalExpenase = ExpAmtArray.get(i).toInt()
                            ExpenseFinalTotal = ExpenseFinalTotal + tempTotalExpenase
                        }
                        dataProduk.put(ExpenseFinalTotal.toString(),item?.nama)
                        val model = Preview(
                            item?.nama?:"",
                            item?.nama?:"",
                            item?.harga?:"",
                            editable.toString()
                        )
                        test.add(model)
                        onClick.item(ExpenseFinalTotal,test)
                        Log.i("dataProduk","${test}")
                    }
                }
            }

        })

    }

    override fun getItemCount(): Int = data?.size ?: 0

    interface onClickListener{
        fun item(total: Int, data: ArrayList<Preview>)
    }
}