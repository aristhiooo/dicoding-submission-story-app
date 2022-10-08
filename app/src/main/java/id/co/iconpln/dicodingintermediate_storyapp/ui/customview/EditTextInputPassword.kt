package id.co.iconpln.dicodingintermediate_storyapp.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import id.co.iconpln.dicodingintermediate_storyapp.R

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
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = resources.getString(R.string.password)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        minHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48F, resources.displayMetrics).toInt()
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6F, resources.displayMetrics)
    }

    private fun init() {
        addTextChangedListener(onTextChanged = { charSeq, _, _, _ ->
            error = if (charSeq.toString().length < 6) {
                "Password must be 6 characters or more"
            } else {
                null
            }
        })
    }
}