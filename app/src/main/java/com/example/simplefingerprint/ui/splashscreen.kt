package com.example.simplefingerprint.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.simplefingerprint.R
import java.util.concurrent.Executor

class splashscreen : Fragment() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var count : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        executor = ContextCompat.getMainExecutor(requireActivity())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    activity?.finish()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
//                    Toast.makeText(requireActivity().applicationContext,
//                        "Authentication succeeded!", Toast.LENGTH_SHORT)
//                        .show()

                    Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeFragment)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
//                    Toast.makeText(requireActivity().applicationContext, "Authentication failed",
//                        Toast.LENGTH_SHORT)
//                        .show()
                    count++
                    if(count == 3){
                        activity?.finish()
                    }
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use password")
            .build()

        biometricPrompt.authenticate(promptInfo)

        return inflater.inflate(R.layout.fragment_splashscreen, container, false)
    }
}