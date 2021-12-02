package com.android.aplikasikasir.history.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.aplikasikasir.R
import com.android.aplikasikasir.databinding.ActivityHistoryBinding
import com.android.aplikasikasir.details.view.DetailsActivity
import com.android.aplikasikasir.history.adapter.HistoryAdapter
import com.android.aplikasikasir.history.model.ResponseHistory
import com.android.aplikasikasir.history.viewModel.ViewModelHistory

class HistoryActivity : AppCompatActivity() {

    lateinit var binding : ActivityHistoryBinding
    lateinit var viewModel:ViewModelHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initt()
        observe()
        aksiTombol()
    }

    private fun initt() {
        viewModel = ViewModelProvider(this)[ViewModelHistory::class.java]
    }
    private fun observe(){
        viewModel.isSuksesHistory().observe(this,{isSuksesHistory(it)})
        viewModel.isErrorHistory().observe(this,{isErrorHistory(it)})
        viewModel.isLoading().observe(this,{isLoading(it)})
    }

    private fun isSuksesHistory(it: List<ResponseHistory>?) {
        var adapter = HistoryAdapter(it,object : HistoryAdapter.onClickListener{
            override fun item(data: ResponseHistory?) {
                val intent = Intent(this@HistoryActivity,DetailsActivity::class.java)
                intent.putExtra("id",data?.id)
                startActivity(intent)
            }

        })

        binding.rvHistory.adapter = adapter
    }

    private fun isErrorHistory(it: Throwable?) {
        Log.i("history",it?.message?:"")
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
        viewModel.getHistory()
    }

    private fun aksiTombol(){
        binding.toolbar.setNavigateUpListener {
            onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        getHistory()
    }
}