package id.co.iconpln.dicodingintermediate_storyapp.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class EditTextInputPassword : AppCompatEditText, View.OnTouchListener {

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) {
        init()
    }
    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int) : super(context, attrSet, defStyleAttr) {
        init()
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = "Password"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        minHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48F, resources.displayMetrics).toInt()
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6F, resources.displayMetrics)
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(charSeq: CharSequence?, p1: Int, p2: Int, p3: Int) {
                error = if (charSeq.toString().length < 6) {
                    "Password must be 6 characters or more"
                } else {
                    null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
}