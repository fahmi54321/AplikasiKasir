package com.android.aplikasikasir.details.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.android.aplikasikasir.R
import com.android.aplikasikasir.databinding.ActivityDetailsBinding
import com.android.aplikasikasir.details.adapter.DetailsAdapter
import com.android.aplikasikasir.details.model.ResponseDetails
import com.android.aplikasikasir.details.viewModel.ViewModelDetails
import com.android.aplikasikasir.helpers.Helpers.formatPrice

class DetailsActivity : AppCompatActivity() {

    private var getId: String?=""
    lateinit var binding : ActivityDetailsBinding
    lateinit var viewModel : ViewModelDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initt()
        observe()
        getHistory()
        aksiTombol()
    }

    private fun initt() {
        getId = intent.getStringExtra("id")
        viewModel = ViewModelProvider(this)[ViewModelDetails::class.java]
    }

    private fun observe(){
        viewModel.isSuksesDetails().observe(this,{isSuksesDetails(it)})
        viewModel.isErrorDetails().observe(this,{isErrorDetails(it)})
        viewModel.idIsEmpty().observe(this,{idIsEmpty(it)})
        viewModel.isLoading().observe(this,{isLoading(it)})
    }

    private fun isSuksesDetails(it: List<ResponseDetails>?) {
        var adapter = DetailsAdapter(it)
        binding.rvDetails.adapter = adapter
        var total = 0
        for (x in it?.indices?:0..1){
            var harga = it?.get(x)?.harga
            total = total+ harga?.toInt()!!
        }
        binding.txtTotal.formatPrice(total.toString())
    }

    private fun isErrorDetails(it: Throwable?) {
        Log.i("details",it?.message?:"")
    }

    private fun idIsEmpty(it: Boolean?) {
        if (it==true){
            Log.i("details","id kosong")
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it==true){
            binding.progreeBar.visibility = View.VISIBLE
        }
        if (it==false){
            binding.progreeBar.visibility = View.GONE
        }
    }

    private fun getHistory(){
        if (getId!=null){
            viewModel.getDetails(getId?:"")
        }
    }

    private fun aksiTombol(){
        binding.toolbar.setNavigateUpListener {
            onBackPressed()
        }
    }
}