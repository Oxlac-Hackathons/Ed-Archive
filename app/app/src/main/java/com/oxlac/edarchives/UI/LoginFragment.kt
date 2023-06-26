package com.oxlac.edarchives.UI

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.FirebaseApp
import com.oxlac.edarchives.MainActivity
import com.oxlac.edarchives.R

class LoginFragment : Fragment() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(requireContext())
        super.onCreate(savedInstanceState)
        enterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.right_slide)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // bind the on click listener for the next button
        view.findViewById<View>(R.id.login_button).setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build(),
            )
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)
        }
        // bind the skip login
        view.findViewById<View>(R.id.skiplogin).setOnClickListener {
            this.skipLogin()
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == Activity.RESULT_OK) {
            // set the shared preference to false and signed in as true
            val settings = requireActivity().getSharedPreferences("PREFS", 0)
            val editor = settings.edit()
            editor.putBoolean("firstLaunch", false)
            editor.putBoolean("signedIn", true)
            editor.apply()
            // start the main activity
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        } else {
            // we are not signed in
        }
    }

    private fun skipLogin() {
        // set the shared preference to false and signed in as true
        val settings = requireActivity().getSharedPreferences("PREFS", 0)
        val editor = settings.edit()
        editor.putBoolean("firstLaunch", false)
        editor.putBoolean("signedIn", false)
        editor.apply()
        // start the main activity
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }


}