package com.example.moneychild.ui.children

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneychild.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ChildrenFragment : Fragment(), ChildAdapter.OnChildClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChildAdapter

    private lateinit var childrenDB: ChildrenDatabaseHelper
    private lateinit var childId: ArrayList<String>
    private lateinit var childName: ArrayList<String>
    private lateinit var childEmail: ArrayList<String>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_children, container, false)

        val fab: FloatingActionButton = root.findViewById(R.id.fab_children)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_nav_children_to_addChildFragment)
        }

        childrenDB = ChildrenDatabaseHelper(root.context)
        childId = ArrayList()
        childName = ArrayList()
        childEmail = ArrayList()

        storeChildrenInArrays()

        recyclerView = root.findViewById(R.id.recycler_view_children)
        adapter = ChildAdapter(this, childId, childName, childEmail)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        return root
    }

    private fun storeChildrenInArrays() {
        val cursor = childrenDB.readAllChildren()
        while (cursor.moveToNext()) {
            childId.add(cursor.getString(0))
            childName.add(cursor.getString(1))
            childEmail.add(cursor.getString(2))
        }
    }

    override fun removeChild(position: Int) {
        Toast.makeText(this.context, "Child deleted", Toast.LENGTH_SHORT).show()
        childrenDB.deleteChild(childId[position])
        childId.removeAt(position)
        childName.removeAt(position)
        childEmail.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}