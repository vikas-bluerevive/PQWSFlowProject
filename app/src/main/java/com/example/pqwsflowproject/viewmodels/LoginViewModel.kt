package com.example.pqwsflowproject.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pqwsflowproject.model.LoginResponse
import com.example.pqwsflowproject.network.Api
import com.example.pqwsflowproject.network.Repository
import com.example.pqwsflowproject.utils.ApiException
import com.example.pqwsflowproject.utils.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: BaseViewModel() {
    var successfullyLogin: MutableLiveData<LoginResponse> = MutableLiveData()
    var api: Api

    init {
        api= Repository.retrofit
    }


    fun loginIntoApp(email:String, password:String){

        try {
            viewModelScope.launch(Dispatchers.IO) {
                var response  = api.Login(email,password)

                successfullyLogin.postValue(response.body())
            }

            /*val response =
                respository.socialLogin(name, email, image, phone, instagramId, googleId, fbId)*/
        } catch (e: ApiException) {
             progressBar.value = false
            feedBackMessage.value = e.message!!

    } catch (e: NoInternetException) {

            progressBar.value = false
            feedBackMessage.value = e.message!!

    }

    }



}