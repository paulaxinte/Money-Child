package com.example.moneychild.ui.send

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.moneychild.R
import com.example.moneychild.ui.children.ChildrenDatabaseHelper
import com.example.moneychild.ui.chores.ChoresDatabaseHelper

class SendFragment : Fragment() {

    private lateinit var choresDB: ChoresDatabaseHelper
    private lateinit var childrenDB: ChildrenDatabaseHelper

    private lateinit var choreTitle: ArrayList<String>
    private lateinit var choreReward: ArrayList<String>
    private lateinit var choreChild: ArrayList<String>
    private lateinit var childEmail: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_send, container, false)

        choresDB = ChoresDatabaseHelper(root.context)
        childrenDB = ChildrenDatabaseHelper(root.context)
        choreTitle = ArrayList()
        choreReward = ArrayList()
        choreChild = ArrayList()
        childEmail = ArrayList()

        storeChoresAndEmailsInArrays()

        val sendButton: Button = root.findViewById(R.id.button_send)
        sendButton.setOnClickListener {
            sendListOfChores()
        }

        return root
    }

    private fun storeChoresAndEmailsInArrays() {
        val cursorChores = choresDB.readAllChores()
        val cursorChildren = childrenDB.readAllChildren()

        while (cursorChores.moveToNext()) {
            choreTitle.add(cursorChores.getString(1))
            choreReward.add(cursorChores.getString(2))
            choreChild.add(cursorChores.getString(3))
        }

        while (cursorChildren.moveToNext()) {
            childEmail.add(cursorChildren.getString(2))
        }
    }

    private fun sendListOfChores() {
        val emailList = childEmail.toTypedArray()
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, emailList)
            putExtra(Intent.EXTRA_SUBJECT, "List of chores")
        }

        var choresList = ""
        for (index in choreTitle.indices) {
            val chore = getString(
                R.string.chores_details,
                choreTitle[index],
                choreChild[index],
                choreReward[index]
            )
            choresList += chore
        }
        intent.putExtra(Intent.EXTRA_TEXT, choresList)

        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        }
    }
}