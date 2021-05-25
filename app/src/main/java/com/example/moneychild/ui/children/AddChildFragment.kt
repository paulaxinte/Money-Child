package com.example.moneychild.ui.children

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moneychild.R
import com.google.android.material.textfield.TextInputEditText

class AddChildFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_child, container, false)

        val name: TextInputEditText = root.findViewById(R.id.child_name_edit_text)
        val email: TextInputEditText = root.findViewById(R.id.child_email_edit_text)
        val addButton: Button = root.findViewById(R.id.add_button)

        addButton.setOnClickListener {
            val childrenDB = ChildrenDatabaseHelper(root.context)
            childrenDB.addChild(
                name.text.toString().trim(),
                email.text.toString().trim()
            )
            findNavController().navigate(R.id.action_addChildFragment_to_nav_children)
        }

        return root
    }
}