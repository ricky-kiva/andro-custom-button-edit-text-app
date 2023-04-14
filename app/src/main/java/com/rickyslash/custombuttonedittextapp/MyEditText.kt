package com.rickyslash.custombuttonedittextapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class MyEditText: AppCompatEditText, View.OnTouchListener {

    private lateinit var clearButtonImage: Drawable

    // call init() on constructors
    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    // setting EditText onDraw default attributes
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Full name"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    // set class initialization attributes & methods
    private fun init() {
        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_24) as Drawable
        setOnTouchListener(this)

        // add TextChangedListener for this EditText
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            // hides clearButtonImage when string is empty
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }

    // `onTouch` handles touch events of a View object
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        // checks whether the third ([2]) compoundDrawables is not null
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false
            // checks user's layout direction (RTL = Right to Left (not default))
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                // define clearButtonEnd ends horizontally in EditText coordinate
                clearButtonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    // checks whether user clicked inside the area of 'clearButtonImage'
                    event.x < clearButtonEnd -> isClearButtonClicked = true
                }
            } else {
                // define clearButtonEnd ends horizontally in EditText coordinate
                clearButtonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()
                when {
                    // checks whether user clicked inside the area of 'clearButtonImage'
                    event.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            // check if isClearButtonClicked is true
            if (isClearButtonClicked) {
                // determine whether user has touched the defined action
                when (event.action) {
                    // checks whether user pressed 'down' (touched) the area
                    MotionEvent.ACTION_DOWN -> {
                        // sets the clearButtonImage
                        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_24) as Drawable
                        // shows the clearButtonImage
                        showClearButton()
                        return true
                    }
                    // checks whether user has released 'up' (untouched) of the area
                    MotionEvent.ACTION_UP -> {
                        // sets the clearButtonImage & hides it
                        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_24) as Drawable
                        when {
                            // clear text this when ACTION_UP
                            text != null -> text?.clear()
                        }
                        // hides the clearButtonImage
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
            }
            else return false
        }
        return false
    }

    // function to be called when EditText not empty
    private fun showClearButton() {
        // calls clearButtonImage on the endOfTheText
        setButtonDrawables(endOfTheText = clearButtonImage)
    }

    // function to be called when EditText are empty
    private fun hideClearButton() {
        setButtonDrawables()
    }

    // sets the drawables to be on position relative to the EditText
    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

}