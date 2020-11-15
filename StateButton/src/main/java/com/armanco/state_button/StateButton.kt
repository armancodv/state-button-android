package com.armanco.state_button

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
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

    var enabledTextColor: Int = ContextCompat.getColor(context, R.color.enabledText)
    var disabledTextColor: Int = ContextCompat.getColor(context, R.color.disabledText)
    var loadingTextColor: Int = ContextCompat.getColor(context, R.color.loadingText)
    var errorTextColor: Int = ContextCompat.getColor(context, R.color.errorText)
    var enabledBackgroundColor: Int = ContextCompat.getColor(context, R.color.enabledBackground)
    var disabledBackgroundColor: Int = ContextCompat.getColor(context, R.color.disabledBackground)
    var loadingBackgroundColor: Int = ContextCompat.getColor(context, R.color.loadingBackground)
    var errorBackgroundColor: Int = ContextCompat.getColor(context, R.color.errorBackground)

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
                state = when(getInt(R.styleable.state_button_state, 0)) {
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
        backgroundTintList = ColorStateList.valueOf(
            when (state) {
                State.ENABLED -> enabledBackgroundColor
                State.DISABLED -> disabledBackgroundColor
                State.LOADING -> loadingBackgroundColor
                State.ERROR -> errorBackgroundColor
            }
        )
        setTextColor(
            when (state) {
                State.ENABLED -> enabledTextColor
                State.DISABLED -> disabledTextColor
                State.LOADING -> loadingTextColor
                State.ERROR -> errorTextColor
            }
        )
        iconTint = ColorStateList.valueOf(
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
            State.ERROR -> ContextCompat.getDrawable(context, R.drawable.ic_baseline_error_outline_24)
        }

        isEnabled = state == State.ENABLED
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