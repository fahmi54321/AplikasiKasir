package com.android.aplikasikasir.preview.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.android.aplikasikasir.R
import com.android.aplikasikasir.databinding.ActivityPreviewBinding
import com.android.aplikasikasir.preview.adapter.ProdukSelectionAdapter
import com.android.aplikasikasir.preview.model.Preview
import com.android.aplikasikasir.preview.model.ResponseSave
import com.android.aplikasikasir.preview.viewModel.ViewModelSave
import com.android.aplikasikasir.produk.model.ResponseProduk
import com.android.aplikasikasir.produk.view.MainActivity
import com.google.gson.Gson
import java.util.ArrayList

class PreviewActivity : AppCompatActivity() {

    private var getDataPilih: ArrayList<Preview>?=null
    lateinit var binding : ActivityPreviewBinding

    lateinit var viewModelSave: ViewModelSave

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initt()
        observe()
        aksiTombol()

        getDataPilih = intent.getParcelableArrayListExtra<Preview>("dataPilih")

        if (getDataPilih != null) {
            Log.i("dataPilih","$getDataPilih")
        }

        Log.i("dataPilih","$getDataPilih")

        var adapter = ProdukSelectionAdapter(getDataPilih)
        binding.rvListProduk.adapter = adapter
    }

    private fun initt() {
        viewModelSave = ViewModelProvider(this)[ViewModelSave::class.java]
    }

    private fun observe(){
        viewModelSave.isSuksesSave().observe(this,{isSuksesSave(it)})
        viewModelSave.isErrorSave().observe(this,{isErrorSave(it)})
        viewModelSave.isLoading().observe(this,{isLoading(it)})
        viewModelSave.idProdukIsEmpty().observe(this,{idProdukIsEmpty(it)})
        viewModelSave.qtyIsEmpty().observe(this,{qtyIsEmpty(it)})
    }

    private fun isSuksesSave(it: ResponseSave?) {
        AlertDialog.Builder(this).apply {
            setTitle("Selesai")
            setMessage("Pilih ya untuk tutup")
            setPositiveButton("Ya") { dialog, which ->
                startActivity(Intent(this@PreviewActivity,MainActivity::class.java))
            }.show()
        }
    }

    private fun isErrorSave(it: Throwable?) {
        Log.i("error",it?.localizedMessage?:"")
    }

    private fun isLoading(it: Boolean?) {
        if (it==true){
            binding.progreeBar.visibility = View.VISIBLE
        }
        if (it==false){
            binding.progreeBar.visibility = View.GONE
        }
    }

    private fun idProdukIsEmpty(it: Boolean?) {
        if (it==true){
            Log.i("save","id produk kosong")
        }
    }

    private fun qtyIsEmpty(it: Boolean?) {
        if (it==true){
            Log.i("save","qty kosong")
        }
    }

    private fun aksiTombol(){
        binding.selesai.setOnClickListener {
            for (x in getDataPilih?.indices?:0..1){
                var data = getDataPilih?.get(x)
                viewModelSave.save(data?.idProduk?:"",data?.jumlah?:"")
            }
        }

        binding.toolbar.setNavigateUpListener {
            onBackPressed()
        }
    }

    override fun onStop() {
        super.onStop()
        getDataPilih?.clear()
    }
}