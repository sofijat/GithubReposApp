package com.sofija.githubreposapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class RepoCommit(val url: String) {
    @SerializedName("commit")
    var commit: CommitDetails? = null
}

data class CommitDetails(val url: String) {
    @SerializedName("author")
    var author: Author? = null

    @SerializedName("message")
    var message: String? = null
}


data class Author(val name: String) {
    @SerializedName("email")
    var author: String? = null

    @SerializedName("date")
    var date: Date? = null
}