package com.example.daisyapp.view.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.example.daisyapp.R
import com.google.android.material.textfield.TextInputLayout

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        hint = context.getString(R.string.enter_your_password)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validatePassword(s)
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing
            }
        })
    }

    private fun validatePassword(s: CharSequence) {
        if (s.length < 8) {
            (parent.parent as? TextInputLayout)?.error = context.getString(R.string.error_password_too_short)
        } else {
            (parent.parent as? TextInputLayout)?.error = null
        }
    }
}