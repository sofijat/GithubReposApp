package com.sofija.githubreposapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Repo(val name: String = "") : Serializable {

    @SerializedName("description")
    var description: String? = null

//    @SerializedName("number_of_open_issues")
//    var number_of_open_issues: Int? = 0

    @SerializedName("open_issues_count")
    var open_issues_count: Int? = 0

}