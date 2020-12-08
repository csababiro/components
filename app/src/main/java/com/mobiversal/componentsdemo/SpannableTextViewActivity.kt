package com.mobiversal.componentsdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobiversal.kotlinextensions.logs.logDebug
import kotlinx.android.synthetic.main.activity_spannable_text_view.*

private const val TERMS = 0
private const val PRIVACY = 1

class SpannableTextViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spannable_text_view)

        displayAgreeText()
    }

    private fun displayAgreeText() {
        txtAgree.displaySpannableText(R.string.i_agree_with_terms, R.array.terms)
        txtAgree.setSpannableClickListener { index -> agreeTermsClicked(index) }
    }

    private fun agreeTermsClicked(index: Int) {
        when (index) {
            TERMS -> termsClicked()
            PRIVACY -> privacyClicked()
        }
    }

    private fun termsClicked() {
        logDebug("TERMS", "Terms clicked")
    }

    private fun privacyClicked() {
        logDebug("PRIVACY", "Privacy clicked")
    }
}