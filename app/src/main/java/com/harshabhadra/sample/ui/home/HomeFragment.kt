package com.harshabhadra.sample.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.harshabhadra.sample.R


class HomeFragment : Fragment(), TextWatcher {

    //Declaring Shared-preference
    private lateinit var sharedPreferences: SharedPreferences.Editor

    //Declaring Linear Layout and Edit Text
    private lateinit var ll: LinearLayout
    private lateinit var et: EditText

    /** Declaring to keep track of noOfEditText and assigning id's to edit text.
     * We can use a boolean variable also in this case. But if we want to add
     * multiple edit text then Int is more useful. Other than that we can use the same variable
     * to assign id's to EditText
      */
    private var numberOfLines = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        //Initializing Linear layout
        ll = root.findViewById(R.id.linear_layout)

        //Initializing Shared preference
        sharedPreferences =
            activity?.getSharedPreferences(
                getString(R.string.save_no),
                (Context.MODE_PRIVATE)
            )!!.edit()

        //Get shared preference
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.save_no), Context.MODE_PRIVATE
        )

        //Get saved values from shared preference and populate the saved EditText with values
        sharedPref?.getInt(getString(R.string.no_of_et), 0)?.let {
            if (it > 0) {
                addEditText().let {
                    //Setting text to the EditText
                    et.setText(sharedPref.getString(getString(R.string.et_text), ""))
                }
            }
        }

        //Add Edit Text on add button Click and save the edit text in shared preference
        root.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            addEditText().let {
                sharedPreferences.putInt(getString(R.string.no_of_et), numberOfLines)?.apply()
            }
        }

        //Set Text Watcher to EditText to Save values from it to shared preference when text changes

        return root
    }

    /** Function to Add EditText
     *  The if condition is used to limit the no of Edit Text to 1. We can remove the if statement
     *  to add multiple EditText on add button click. But saving a list of values using
     *  SharedPreference is not a good practice as I limit it to one
     */
    private fun addEditText() {
        if (numberOfLines == 0) {
            et = EditText(context)
            val p = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            et.layoutParams = p
            et.hint = "Enter Text"
            et.inputType = InputType.TYPE_CLASS_TEXT
            et.id = numberOfLines + 1
            ll.addView(et)
            numberOfLines++
            et.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(s: Editable?) {
        s?.let {

            //After EditText is created and and there is some text in that EditText
            //Saving that text into Shared-Preference
            sharedPreferences.putString(getString(R.string.et_text), s.toString()).apply()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}
