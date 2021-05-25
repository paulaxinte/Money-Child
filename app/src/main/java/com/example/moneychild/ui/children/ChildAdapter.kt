package com.example.moneychild.ui.children

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneychild.R

class ChildAdapter(
    private val listener: OnChildClickListener,
    private val childId: ArrayList<String>,
    private val childName: ArrayList<String>,
    private val childEmail: ArrayList<String>
) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    interface OnChildClickListener {
        fun removeChild(position: Int)
    }

    inner class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val nameTextView: TextView = view.findViewById(R.id.child_item_name)
        val emailTextView: TextView = view.findViewById(R.id.child_item_email)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.removeChild(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_item, parent, false)

        return ChildViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.nameTextView.text = childName[position]
        holder.emailTextView.text = childEmail[position]
    }

    override fun getItemCount(): Int {
        return childId.size
    }
}