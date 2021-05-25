package com.example.moneychild.ui.chores

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


class ChoresFragment : Fragment(), ChoreAdapter.OnChoreClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChoreAdapter

    private lateinit var choresDB: ChoresDatabaseHelper
    private lateinit var choreId: ArrayList<String>
    private lateinit var choreTitle: ArrayList<String>
    private lateinit var choreReward: ArrayList<String>
    private lateinit var choreChild: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_chores, container, false)

        val fab: FloatingActionButton = root.findViewById(R.id.fab_chores)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_nav_chores_to_createChoreFragment)
        }

        choresDB = ChoresDatabaseHelper(root.context)
        choreId = ArrayList()
        choreTitle = ArrayList()
        choreReward = ArrayList()
        choreChild = ArrayList()

        storeChoresInArrays()

        recyclerView = root.findViewById(R.id.recycler_view_chores)
        adapter = ChoreAdapter(this, choreId, choreTitle, choreReward, choreChild)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        return root
    }

    private fun storeChoresInArrays() {
        val cursor = choresDB.readAllChores()
        while (cursor.moveToNext()) {
            choreId.add(cursor.getString(0))
            choreTitle.add(cursor.getString(1))
            choreReward.add(cursor.getString(2))
            choreChild.add(cursor.getString(3))
        }
    }

    override fun removeChore(position: Int) {
        Toast.makeText(this.context, "Chore deleted", Toast.LENGTH_SHORT).show()
        choresDB.deleteChore(choreId[position])
        choreId.removeAt(position)
        choreTitle.removeAt(position)
        choreReward.removeAt(position)
        choreChild.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}