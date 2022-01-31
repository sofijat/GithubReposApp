package com.sofija.githubreposapp.view.commits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sofija.githubreposapp.databinding.CommitItemCardBinding
import com.sofija.githubreposapp.model.RepoCommit
import com.sofija.githubreposapp.util.Util
import kotlin.collections.ArrayList

class CommitsAdapter(): RecyclerView.Adapter<CommitsAdapter.CommitsViewHolder>() {

    private var itemsList: List<RepoCommit> = ArrayList<RepoCommit>()

    fun setItems(items: List<RepoCommit>){
        var newList = ArrayList<RepoCommit>()
        newList.addAll(itemsList)
        newList.addAll(items)
        itemsList = newList
        notifyDataSetChanged()
    }

    inner class CommitsViewHolder(val binding: CommitItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = CommitItemCardBinding.inflate(inflater, parent, false)
        return CommitsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommitsViewHolder, position: Int) {
        val item = itemsList[position]
        holder.binding.message.text = item.commit?.message
        holder.binding.authorAndDate.text = item.commit?.author?.name + "@" + Util.convertDateToSimpleString(item.commit?.author?.date)
        holder.binding.order.text = "#" + (position + 1).toString()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

}