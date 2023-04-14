package com.rickyslash.custombuttonedittextapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var myButton: MyButton
    private lateinit var myEditText: MyEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myButton = findViewById(R.id.my_button)
        myEditText = findViewById(R.id.my_edit_text)

        setMyButtonEnabled()

        myEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMyButtonEnabled()
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        myButton.setOnClickListener { Toast.makeText(this@MainActivity, myEditText.text, Toast.LENGTH_SHORT).show() }
    }

    private fun setMyButtonEnabled() {
        val result = myEditText.text
        myButton.isEnabled = result != null && result.toString().isNotEmpty()
    }

}

/* Advanced UI */
// - Custom View: Capability of making inheritance of another View class (like Button, extending from TextView)
// --- you could also make custom view from scratch
// - Canvas: serves method to draw Bitmap, then display it to screen and set it to ImageView / View
// --- It's allowed to add properties using `Paint` (color, style, size, & instructions)
// --- It also serves method to `clip` to some shape. The `clipped view` inside a `view` called as `viewport`
// - Android Widget: additional component in Android's OS Launcher, that act as `teaser` of an app
// - Webview: allows user to open web as a part of Activity

// CustomView:
// - it's possible to make subclass from `View` class to take over control over UI & `element screen function`. Example:
// --- Make custom rendered view (example: volume control, but like electronic analog)
// --- Group multiple component into one (example: ComboBox (popup list with text input))
// --- Make custom EditText
// --- Catch `event` / other `handle` with in particular way (like in game)
// - Basic Approach of CustomView:
// --- Do `inheritance` of class, with class that is being made
// --- Overriding couple the superclass method (onDraw(), onMeasure(), onKeyDown(), etc)
// --- Use the new extended class

// CustomView from `scratch`, action that could be used:
// - Creation:
// --- Constructors: called when the `view` is already being made & determine attributes to be in the layout
// --- onFinishInflate: called when View & all of its inherited successfully inflated from XML
// - Layout:
// --- onMeasure(int, int): called to determine View's & all of it's inherited `size`
// --- onLayout(bool, int, int, int, int): called when View need to determine `size` & position for all of it's inherited
// --- onSizeChanged(int, int, int, int): called when View size is changed
// - Drawing:
// --- onDraw(Canvas): called when `View` need to `render` its content
// - Event Processing:
// --- onKeyDown(int, KeyEvent): called when key-down event triggered
// --- onKeyUp(int, KeyEvent): called when key-up event triggered
// --- onTrackballEvent(MotionEvent): called when there is movement on trackball
// --- onTouchEvent(MotionEvent): called when there is touches on screen
// - Focus:
// --- onFocusChanged(boolean, int, Rect): called when `View` gets/lose focus
// --- onWindowFocusChanged(bool): called when `Window` that contains `view` gets/lose focus
// - Attaching:
// --- onAttachedToWindow(): called when `View` is attached to window
// --- onDetachedFromWindow(): called when `View` is detached from window
// --- onWindowVisibilityChanged(int): called when Window visibility that contains view, is `changed`

// Things to be considered, in making CustomView:
// - Suitable with Android's standard
// - Give `style` attributes that could be customized with Android's layout
// - Have accessibility to `event handling`
// - Compatible with multiple Android platform