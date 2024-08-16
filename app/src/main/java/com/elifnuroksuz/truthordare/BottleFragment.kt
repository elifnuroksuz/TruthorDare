package com.elifnuroksuz.truthordare

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlin.random.Random

class BottleFragment : Fragment(R.layout.fragment_bottle) {
    private var currentRotation = 0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottle = view.findViewById<ImageView>(R.id.bottle)
        val button = view.findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val randomAngle = Random.nextFloat() * 360f
            val endRotation = currentRotation + 360f + randomAngle

            val animator = ObjectAnimator.ofFloat(bottle, "rotation", currentRotation, endRotation)
            animator.duration = 1000
            animator.interpolator = LinearInterpolator()
            animator.start()

            animator.addUpdateListener {
                currentRotation = bottle.rotation
            }

            animator.addListener(onEnd = {
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_bottleFragment_to_questionFragment)
                }, 3000)
            })
        }
    }
}

