package com.mobiversal.componentsdemo

import android.os.Bundle
import com.mobiversal.kotlinextensions.logs.logDebug
import kotlinx.android.synthetic.main.activity_spannable_checkbox.*

private const val TERMS = 0
private const val PRIVACY = 1

class SpannableCheckboxActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spannable_checkbox)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        displayAgreeText()
    }

    private fun displayAgreeText() {
        cbAgree.displaySpannableText(R.string.i_agree_with_terms, R.array.terms)
        cbAgree.setSpannableClickListener { index -> agreeTermsClicked(index) }
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
