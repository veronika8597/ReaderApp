package com.example.myreaderapp.model

data class MUser(val id: String?,
                 val userId: String,
                 val displayName: String,
                 val avatarUrl: String,
                 val quote: String,
                 val profession: String){

    //converts the user data into map of its properties
    fun toMap(): MutableMap<String,Any>{
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "qoute" to this.quote,
            "profession" to this.profession,
            "avatar_url" to this.avatarUrl

        )
    }
}
