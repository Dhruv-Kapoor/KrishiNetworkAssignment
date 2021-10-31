package com.example.krishinetworkassignment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.krishinetworkassignment.databinding.ActivityFormBinding
import com.example.krishinetworkassignment.viewmodels.FormActivityViewModel

class FormActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val RC_CAMERA = 100
    }

    private lateinit var binding: ActivityFormBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[FormActivityViewModel::class.java]
    }
    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.extras?.get("data")?.let {
                    viewModel.setProfilePic(it as Bitmap)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_form);
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.clickListener = this
        binding.executePendingBindings()

        supportActionBar?.title = "Form Activity"

        viewModel.init(
            sharedPreferences,
            filesDir.path,
            ResourcesCompat.getDrawable(resources, R.drawable.default_profile_photo, theme)
                ?.toBitmap()
        )

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.ivPhoto) {
            if (checkPermissions()) {
                val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                resultLauncher.launch(intent)
            } else {
                requestPermissions()
            }
        } else if (v?.id == R.id.btnMove) {
            val intent = Intent(this, InternetActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkPermissions(): Boolean {
        return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), RC_CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == RC_CAMERA) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}