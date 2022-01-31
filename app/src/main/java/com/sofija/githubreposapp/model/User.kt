package com.sofija.githubreposapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(val login: String): Serializable {

    @SerializedName("id")
    var id: Long? = null

    @SerializedName("avatar_url")
    var avatar_url: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("company")
    var company: String? = null

    @SerializedName("location")
    var location: String? = null

}