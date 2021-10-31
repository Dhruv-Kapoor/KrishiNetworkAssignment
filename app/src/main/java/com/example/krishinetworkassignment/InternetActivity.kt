package com.example.krishinetworkassignment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.krishinetworkassignment.adapters.MandiAdapter
import com.example.krishinetworkassignment.databinding.ActivityInternetBinding
import com.example.krishinetworkassignment.utils.KrishiApplication
import com.example.krishinetworkassignment.viewmodels.InternetActivityViewModel
import com.example.krishinetworkassignment.viewmodels.InternetActivityViewModelFactory

class InternetActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityInternetBinding

    private val viewModel by viewModels<InternetActivityViewModel> {
        InternetActivityViewModelFactory((application as KrishiApplication).repository)
    }
    private val adapter by lazy {
        MandiAdapter(ArrayList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_internet)

        binding.viewModel = viewModel
        binding.clickListener = this
        binding.executePendingBindings()

        binding.rvOtherMandi.adapter = adapter

        supportActionBar?.title = "Internet Activity"

        viewModel.otherMandiItems.observe(this) {
            if (it.isEmpty()) {
                return@observe
            }
            adapter.setContent(it)
        }

        viewModel.repositoryError.observe(this) {
            it ?: return@observe
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnMove) {
            onBackPressed()
        }
    }
}