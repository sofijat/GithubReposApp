package com.sofija.githubreposapp.view.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sofija.githubreposapp.databinding.RepoItemCardBinding
import com.sofija.githubreposapp.model.Repo

class ReposAdapter(): RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    companion object {
        var listeners : MutableList<RepoCardClickListener> = ArrayList(1)
        fun registerRepoCardClickListener(listener: RepoCardClickListener){
            listeners.add(listener)
        }
        fun unregisterRepoCardClickListener(listener: RepoCardClickListener){
            listeners.remove(listener)
        }
    }

    private var itemsList: List<Repo> = ArrayList<Repo>()

    fun setItems(items: List<Repo>){
        itemsList = items
        notifyDataSetChanged()
    }

    inner class ReposViewHolder(val binding: RepoItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = RepoItemCardBinding.inflate(inflater, parent, false)
        return ReposViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val item = itemsList[position]
        holder.binding.name.text = item.name
        holder.binding.numOfOpenIssues.text = item.open_issues_count.toString()
        holder.binding.cardViewRepoItem.setOnClickListener {
            for (listener in listeners) {
                listener.onRepoCardClicked(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}