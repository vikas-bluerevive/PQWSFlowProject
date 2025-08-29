package com.example.pqwsflowproject.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pqwsflowproject.model.LoginResponse
import com.example.pqwsflowproject.model.LoginResponse2
import com.example.pqwsflowproject.network.Api
import com.example.pqwsflowproject.network.Repository
import com.example.pqwsflowproject.utils.ApiException
import com.example.pqwsflowproject.utils.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: BaseViewModel() {
    var successfullyLogin: MutableLiveData<LoginResponse2> = MutableLiveData()
    var api: Api

    init {
        api= Repository.retrofit
    }


    fun loginIntoApp(email:String, password:String){
        val body = mapOf(
            "email" to email,
            "password" to password
        )
          progressBar.value= true
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    var response = api.Login(body)

                    successfullyLogin.postValue(response.body())
                    feedBackMessage.postValue(response.raw().message())
                    viewModelScope.launch(Dispatchers.Main) {
                        progressBar.value = false
                    }
                }catch(e:Exception){
                    e.printStackTrace()
                    viewModelScope.launch(Dispatchers.Main) {
                        progressBar.value = false
                        feedBackMessage.value = e.message!!
                    }

                }
            }

            /*val response =
                respository.socialLogin(name, email, image, phone, instagramId, googleId, fbId)*/
        } catch (e: ApiException) {
             progressBar.value = false
            feedBackMessage.value = e.message!!

    } catch (e: NoInternetException) {

            progressBar.value = false
            feedBackMessage.value = e.message!!

    }catch(e: Exception){
            progressBar.value = false
            feedBackMessage.value = e.message!!
        }

    }



}