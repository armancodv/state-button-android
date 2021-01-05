package com.armanco.state_button

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton


class StateButton : MaterialButton {

    constructor(ctx: Context) : super(ctx) {
        init(ctx)
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        init(ctx, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    var state: State = State.ENABLED
        set(value) {
            field = value
            updateState()
        }

    @ColorInt var enabledTextColor: Int = ContextCompat.getColor(context, R.color.enabledText)
    @ColorInt var disabledTextColor: Int = ContextCompat.getColor(context, R.color.disabledText)
    @ColorInt var loadingTextColor: Int = ContextCompat.getColor(context, R.color.loadingText)
    @ColorInt var errorTextColor: Int = ContextCompat.getColor(context, R.color.errorText)
    @ColorInt var enabledBackgroundColor: Int = ContextCompat.getColor(context, R.color.enabledBackground)
    @ColorInt var disabledBackgroundColor: Int = ContextCompat.getColor(context, R.color.disabledBackground)
    @ColorInt var loadingBackgroundColor: Int = ContextCompat.getColor(context, R.color.loadingBackground)
    @ColorInt var errorBackgroundColor: Int = ContextCompat.getColor(context, R.color.errorBackground)
    private var backgroundAnimator: ValueAnimator? = null
    private var textAnimator: ValueAnimator? = null
    var animationDuration: Int = 600

    private fun init(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.state_button,
            defStyleAttr,
            0
        ).apply {
            try {
                enabledTextColor = getColor(
                    R.styleable.state_button_enabledTextColor,
                    ContextCompat.getColor(context, R.color.enabledText)
                )
                disabledTextColor = getColor(
                    R.styleable.state_button_disabledTextColor,
                    ContextCompat.getColor(context, R.color.disabledText)
                )
                loadingTextColor = getColor(
                    R.styleable.state_button_loadingTextColor,
                    ContextCompat.getColor(context, R.color.loadingText)
                )
                errorTextColor = getColor(
                    R.styleable.state_button_errorTextColor,
                    ContextCompat.getColor(context, R.color.errorText)
                )
                enabledBackgroundColor = getColor(
                    R.styleable.state_button_enabledBackgroundColor,
                    ContextCompat.getColor(context, R.color.enabledBackground)
                )
                disabledBackgroundColor = getColor(
                    R.styleable.state_button_disabledBackgroundColor,
                    ContextCompat.getColor(context, R.color.disabledBackground)
                )
                loadingBackgroundColor = getColor(
                    R.styleable.state_button_loadingBackgroundColor,
                    ContextCompat.getColor(context, R.color.loadingBackground)
                )
                errorBackgroundColor = getColor(
                    R.styleable.state_button_errorBackgroundColor,
                    ContextCompat.getColor(context, R.color.errorBackground)
                )
                animationDuration = getInt(
                    R.styleable.state_button_animationDuration,
                    600
                )
                state = when (getInt(R.styleable.state_button_state, 0)) {
                    1 -> State.DISABLED
                    2 -> State.LOADING
                    3 -> State.ERROR
                    else -> State.ENABLED
                }
            } finally {
                recycle()
            }
        }
        updateState()
    }

    private fun updateState() {
        animateBackgroundTint(
            when (state) {
                State.ENABLED -> enabledBackgroundColor
                State.DISABLED -> disabledBackgroundColor
                State.LOADING -> loadingBackgroundColor
                State.ERROR -> errorBackgroundColor
            }
        )
        animateTextColor(
            when (state) {
                State.ENABLED -> enabledTextColor
                State.DISABLED -> disabledTextColor
                State.LOADING -> loadingTextColor
                State.ERROR -> errorTextColor
            }
        )
        icon = when (state) {
            State.ENABLED -> null
            State.DISABLED -> null
            State.LOADING -> ContextCompat.getDrawable(context, R.drawable.ic_baseline_sync_24)
            State.ERROR -> ContextCompat.getDrawable(
                context,
                R.drawable.ic_baseline_error_outline_24
            )
        }

        isEnabled = state == State.ENABLED
    }

    private fun animateBackgroundTint(@ColorInt toColor: Int) {
        val fromColor = backgroundTintList?.defaultColor ?: enabledBackgroundColor
        if(toColor == fromColor) return
        backgroundAnimator?.cancel()
        backgroundAnimator = ValueAnimator.ofArgb(fromColor, toColor)
        backgroundAnimator?.duration = animationDuration.toLong()
        backgroundAnimator?.addUpdateListener { animator ->
            backgroundTintList = ColorStateList.valueOf(animator.animatedValue as? Int ?: enabledBackgroundColor)
        }
        backgroundAnimator?.start()
    }

    private fun animateTextColor(@ColorInt toColor: Int) {
        val fromColor = textColors?.defaultColor ?: enabledTextColor
        if(toColor == fromColor) return
        textAnimator?.cancel()
        textAnimator = ValueAnimator.ofArgb(fromColor, toColor)
        textAnimator?.duration = animationDuration.toLong()
        textAnimator?.addUpdateListener { animator ->
            setTextColor(animator.animatedValue as? Int ?: enabledTextColor)
            iconTint = ColorStateList.valueOf(animator.animatedValue as? Int ?: enabledTextColor)
        }
        textAnimator?.start()
    }

    companion object {
        enum class State {
            ENABLED,
            LOADING,
            ERROR,
            DISABLED
        }
    }

}