package com.example.moneychild.ui.chores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneychild.R

class ChoreAdapter(
    private val listener: OnChoreClickListener,
    private val choreId: ArrayList<String>,
    private val choreTitle: ArrayList<String>,
    private val choreReward: ArrayList<String>,
    private val choreChild: ArrayList<String>
) : RecyclerView.Adapter<ChoreAdapter.ChoreViewHolder>() {

    interface OnChoreClickListener {
        fun removeChore(position: Int)
    }

    inner class ChoreViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val titleTextView: TextView = view.findViewById(R.id.chore_item_title)
        val rewardTextView: TextView = view.findViewById(R.id.chore_item_reward)
        val childTextView: TextView = view.findViewById(R.id.chore_item_child)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.removeChore(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoreViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.chore_item, parent, false)

        return ChoreViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ChoreViewHolder, position: Int) {
        holder.titleTextView.text = choreTitle[position]
        holder.rewardTextView.text = choreReward[position]
        holder.childTextView.text = choreChild[position]
    }

    override fun getItemCount(): Int {
        return choreId.size
    }
}