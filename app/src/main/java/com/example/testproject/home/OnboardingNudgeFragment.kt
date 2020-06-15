package com.example.testproject.home

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import com.example.testproject.R
import kotlinx.android.synthetic.main.fragment_onboarding_nudge.view.*
import kotlin.math.abs

class OnboardingNudgeFragment : Fragment(R.layout.fragment_onboarding_nudge) {
    companion object {
        const val TAG = "OnboardingNudgeFragment"
    }

    var onboardingNudgeListener: OnboardingNudgeListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureAnimation(view)
    }

    private fun configureAnimation(view: View) {
        view.slideUpView.setOnTouchListener(object : View.OnTouchListener {
            private var initialTouch = 0.0f
            private var previousTouch = 0.0f
            private var velocity = 0.0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> initialTouch = event.rawY
                    MotionEvent.ACTION_MOVE -> {
                        if (initialTouch == 0.0f) return true

                        val translation = event.rawY - initialTouch

                        Log.d(
                            "JOLOG",
                            "[JOLOG] $initialTouch ${event.rawY} ${view.frameLayout.translationY}"
                        )
                        if (view.frameLayout.y + translation > 0.0f || view.frameLayout.translationY == translation) return true

                        view.frameLayout.translationY = translation
                        velocity = previousTouch - event.rawY
                        previousTouch = event.rawY
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        if (initialTouch == 0.0f) return true

                        val willSlideUp = velocity > 25 &&
                                abs(view.frameLayout.y) >= view.frameLayout.height.toFloat() / 4
                        val endTranslationY =
                            if (willSlideUp) -view.frameLayout.height.toFloat() else 0.0f
                        val animation =
                            TranslateAnimation(
                                0.0f,
                                0.0f,
                                0.0f,
                                endTranslationY - view.frameLayout.y
                            )
                        animation.apply {
                            setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(animation: Animation?) {}
                                override fun onAnimationRepeat(animation: Animation?) {}
                                override fun onAnimationEnd(animation: Animation?) {
                                    view.frameLayout.postDelayed({
                                        view.frameLayout.translationY = endTranslationY
                                        handleSlideUp(willSlideUp)
                                    }, 5L)
                                }
                            })
                            duration = 200L
                        }
                        view.frameLayout.startAnimation(animation)

                        return false
                    }
                }

                return true
            }
        })
    }

    private fun handleSlideUp(willSlideUp: Boolean) {
        if (!willSlideUp) return

        onboardingNudgeListener?.onSlideUp()
    }

    interface OnboardingNudgeListener {
        fun onSlideUp()
    }
}
