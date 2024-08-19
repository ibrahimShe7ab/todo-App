package com.example.todo.settingFragment

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import androidx.transition.Visibility.Mode
import com.example.todo.R
import com.example.todo.databinding.FragmentSettingBinding
import java.util.Locale


class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLanguageSpinner(requireContext())
        modeSpinner(requireContext())
    }





    fun setupLanguageSpinner(context: Context) {
        val spinner: Spinner = requireView().findViewById(R.id.language_spinner)
        val languages = arrayOf("English","العربية" )
        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            languages
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> setLocale(context, "en") // English
                    1 -> setLocale(context, "ar") // Arabic
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where no item is selected if needed
            }
        }
    }


    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        // Update the configuration of the app context
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        // Refresh the activity to apply the new language settings
        if(activity?.isDestroyed==true){ (context as? Activity)?.recreate() }

    }

    fun modeSpinner(context: Context) {
        // Array of modes
        val modes = arrayOf("Light", "Dark")

        // Find the Spinner view
        val spinner: Spinner = (context as Activity).findViewById(R.id.mode_spinner)

        // Create an ArrayAdapter with the modes array
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, modes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Set the current selection based on the current mode
        spinner.setSelection(if (isDarkModeOn()) 1 else 0)

        // Set the Spinner's item selected listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    // Light mode selected
                    changeToLightMode()
                } else if (position == 1) {
                    // Dark mode selected
                    changeToDarkMode()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No action needed
            }
        }
    }

    // Function to check if dark mode is currently on
    fun isDarkModeOn(): Boolean {
        val nightModeFlags = Resources.getSystem().configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    // Function to change to light mode
    fun changeToLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        if(activity?.isDestroyed==true){ (context as? Activity)?.recreate() }

    }

    // Function to change to dark mode
    fun changeToDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        if(activity?.isDestroyed==true){ (context as? Activity)?.recreate() }

    }


}





