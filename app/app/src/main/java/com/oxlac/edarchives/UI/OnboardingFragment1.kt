package com.oxlac.edarchives.UI

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import android.transition.TransitionInflater
import com.oxlac.edarchives.R


class OnboardingFragment1 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.left_slide)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding1, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // start animating all the elements
        val title = view.findViewById<View>(R.id.appTitle)
        val text = view.findViewById<View>(R.id.appDesc)
        val alpha1 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
        val pos1 = PropertyValuesHolder.ofFloat("translationY", 100f, 0f)
        ObjectAnimator.ofPropertyValuesHolder(title, alpha1, pos1).apply {
            duration = 750
            interpolator = AnticipateOvershootInterpolator()
            start()
        }
        ObjectAnimator.ofPropertyValuesHolder(text, alpha1, pos1).apply {
            duration = 1000
            interpolator = AnticipateOvershootInterpolator()
            start()
        }
        // bind the on click listener for the next button
        view.findViewById<View>(R.id.start_button).setOnClickListener {
            vibrate("click")
            this.showLoginFragment()
        }
    }

    private fun showLoginFragment() {
        val loginFragment = LoginFragment()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.OnboardingContainer, loginFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun vibrate(type: String) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                activity?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION") activity?.getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (type == "click") {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        30, VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else if (type == "error") {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        30, VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }
        } else {
            if (type == "click") {
                vibrator.vibrate(30)
            } else if (type == "error") {
                vibrator.vibrate(50)
            }
        }
    }
}