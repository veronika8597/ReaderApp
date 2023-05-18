package com.example.myreaderapp.screens.login

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myreaderapp.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {

   //val loadingState = MutableStateFlow(LoadingState.IDLE)

    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false) //used internally
    val loading: LiveData<Boolean> = _loading //used outside the class


    fun signInWithEmailAndPassword(email: String, password: String, home:() -> Unit) = viewModelScope.launch{
        try{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Log.d("FB", "signInWithEmailAndPassword: YAY! ${task.result.toString()}")
                        home()
                    }else{
                        Log.d("FB", "signInWithEmailAndPassword: ${task.result.toString()}")
                    }
                }

        }catch (ex: Exception){
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
        }

    }



    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(displayName)
                        home()
                    }else{
                        Log.d("FB", "cretedInWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
        }


    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = MUser(userId = userId.toString(), displayName = displayName.toString(), avatarUrl = "",
        quote = "Life's great", profession = "Android developer", id = null).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)

    }


}