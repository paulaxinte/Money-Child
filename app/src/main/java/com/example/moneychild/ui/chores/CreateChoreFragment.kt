package com.example.moneychild.ui.chores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moneychild.R
import com.example.moneychild.ui.children.ChildrenDatabaseHelper
import com.google.android.material.textfield.TextInputEditText

class CreateChoreFragment : Fragment() {

    private lateinit var childrenDB: ChildrenDatabaseHelper
    private lateinit var childName: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_create_chore, container, false)

        val title: TextInputEditText = root.findViewById(R.id.chore_title_edit_text)
        val reward: AutoCompleteTextView = root.findViewById(R.id.act_reward)
        val child: AutoCompleteTextView = root.findViewById(R.id.act_child)
        val createButton: Button = root.findViewById(R.id.create_button)

        val rewards = listOf("$2", "$5", "$10")
        var adapter = ArrayAdapter(requireContext(), R.layout.list_item, rewards)
        reward.setAdapter(adapter)

        childrenDB = ChildrenDatabaseHelper(root.context)
        childName = ArrayList()
        getChildName()

        adapter = ArrayAdapter(requireContext(), R.layout.list_item, childName)
        child.setAdapter(adapter)

        createButton.setOnClickListener {
            val choresDB = ChoresDatabaseHelper(root.context)
            choresDB.createChore(
                title.text.toString().trim(),
                reward.text.toString().trim(),
                child.text.toString().trim()
            )
            findNavController().navigate(R.id.action_createChoreFragment_to_nav_chores)
        }

        return root
    }

    private fun getChildName() {
        val cursor = childrenDB.readAllChildren()
        while (cursor.moveToNext()) {
            childName.add(cursor.getString(1))
        }
    }
}