package com.example.krishinetworkassignment.viewmodels

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class FormActivityViewModel : ViewModel() {

    companion object {
        const val KEY_NAME = "name"
        const val KEY_EMAIL = "email"
    }

    val name = MutableLiveData("")
    val profilePic = MutableLiveData<Bitmap>()
    val email = MutableLiveData("")

    val nameError = MutableLiveData("")
    val emailError = MutableLiveData("")

    val submittedText = MutableLiveData<String>()
    val submittedProfilePic = MutableLiveData<Bitmap>()

    private val fileName = "profile.png"
    private lateinit var fileDirectory: String
    private var defaultProfile: Bitmap? = null

    private var preferences: SharedPreferences? = null

    fun init(preferences: SharedPreferences, fileDirectory: String, defaultProfile: Bitmap?) {
        this.fileDirectory = "$fileDirectory/$fileName"
        this.defaultProfile = defaultProfile
        this.preferences?.apply { return }
        this.preferences = preferences
        if (preferences.contains(KEY_NAME) && preferences.contains(KEY_EMAIL)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val file = File(this@FormActivityViewModel.fileDirectory)
                    val inputStream = file.inputStream()
                    var bitmap = BitmapFactory.decodeStream(inputStream)
                    if (bitmap == null) bitmap = defaultProfile
                    withContext(Dispatchers.Main) {
                        setSubmittedDetails(
                            preferences.getString(KEY_NAME, "")!!,
                            preferences.getString(KEY_EMAIL, "")!!,
                            bitmap
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    fun onSubmit() {
        if (!validateName()) {
            nameError.value = "Name can't be empty"
            return
        }
        if (!validateEmail()) {
            emailError.value = "Invalid Email"
            return
        }

        val bitmap = profilePic.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = File(fileDirectory)
                if (file.exists()) {
                    file.delete()
                }
                file.createNewFile()
                val out = file.outputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, out)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        setSubmittedDetails(name.value!!, email.value!!, bitmap)

        val editor = preferences?.edit()

        editor?.putString(KEY_NAME, name.value)
        editor?.putString(KEY_EMAIL, email.value)

        editor?.apply()

        name.value = ""
        email.value = ""

        nameError.value = ""
        emailError.value = ""

        profilePic.value = defaultProfile
    }

    fun setProfilePic(bitmap: Bitmap) {
        profilePic.value = bitmap
    }

    private fun setSubmittedDetails(name: String, email: String, profile: Bitmap?) {
        submittedText.value = """
                Name: $name
                Email: $email
            """.trimIndent()

        submittedProfilePic.value = profile
    }

    private fun validateName(): Boolean {
        return !name.value.isNullOrEmpty()
    }

    private fun validateEmail(): Boolean {
        if (email.value.isNullOrEmpty() ||
            !Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()
        ) {
            return false
        }
        return true
    }

}