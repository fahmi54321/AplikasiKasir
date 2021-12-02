package com.android.aplikasikasir.produk.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.aplikasikasir.databinding.ActivityMainBinding
import com.android.aplikasikasir.helpers.Helpers.formatPrice
import com.android.aplikasikasir.history.view.HistoryActivity
import com.android.aplikasikasir.preview.model.Preview
import com.android.aplikasikasir.preview.view.PreviewActivity
import com.android.aplikasikasir.produk.adapter.ProdukAdapter
import com.android.aplikasikasir.produk.model.ResponseProduk
import com.android.aplikasikasir.produk.viewmodel.ViewModelListProduk
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelList:ViewModelListProduk
    private lateinit var binding : ActivityMainBinding
    var dataProduk = arrayListOf<Preview>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initt()
        observe()
    }

    private fun initt() {
        viewModelList = ViewModelProvider(this)[ViewModelListProduk::class.java]
        binding.toolbar.setNavigateUpListener {
            onBackPressed()
        }
        binding.toolbar.setNavigateHistoryListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    private fun observe(){
        viewModelList.isSuksesGetProduk().observe(this,{isSuksesGetProduk(it)})
        viewModelList.isErrorGetProduk().observe(this,{isErrorGetProduk(it)})
        viewModelList.isLoading().observe(this,{isLoading(it)})
    }

    private fun isSuksesGetProduk(it: List<ResponseProduk>?) {
        var adapter = ProdukAdapter(it,object : ProdukAdapter.onClickListener{

            override fun item(total: Int, data: ArrayList<Preview>) {
                binding.txtTotal.formatPrice(total.toString())
                aksiTombol(data)
            }

        })
        binding.rvListProduk.adapter = adapter
    }

    private fun isErrorGetProduk(it: Throwable?) {
        Log.i("produk","error : ${it?.message}")
    }

    private fun isLoading(it: Boolean?) {
        if (it==true){
            binding.progreeBar.visibility = View.VISIBLE
        }
        if (it==false){
            binding.progreeBar.visibility = View.GONE
        }
    }

    private fun getProduk(){
        viewModelList.getProduk()
    }

    private fun aksiTombol(data: ArrayList<Preview>) {
        if (binding.txtTotal.equals("Rp. 0")){
            Toast.makeText(this, "Total masih kosong", Toast.LENGTH_SHORT).show()
        }else {
            binding.total.setOnClickListener {
                Log.i("hai", "$data")
                val intent = Intent(this, PreviewActivity::class.java)
                intent.putParcelableArrayListExtra("dataPilih", data)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getProduk()
        binding.txtTotal.text = "Rp. 0"
    }
}