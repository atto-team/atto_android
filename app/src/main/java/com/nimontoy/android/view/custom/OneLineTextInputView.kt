package com.nimontoy.android.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.nimontoy.android.R
import com.nimontoy.android.view.layout.LayoutEvent
import com.nimontoy.android.view.layout.RxRelativeLayout
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate

import android.text.InputType.TYPE_CLASS_TEXT

/**
 * Created by leekijung on 2019. 7. 21..
 */

@SuppressLint("CheckResult")
class OneLineTextInputView : RxRelativeLayout {

    companion object {
        private val TAG = "OneLineTextInputView"
        private val VIEW_VISIBLE = "visible"
        private val VIEW_INVISIBLE = "invisible"
        private val VIEW_GONE = "gone"
    }

    lateinit var visibility: String

    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.one_line_text_input_view, this, false) }

    val textTitle by lazy<TextView> { view.findViewById(R.id.title_text) }
    val optionButton by lazy<Button> { view.findViewById(R.id.option_button) }
    val input by lazy<EditText> { view.findViewById(R.id.input) }
    val textButton by lazy<Button> { view.findViewById(R.id.button_text) }

    val inputImage by lazy<ImageButton> { view.findViewById(R.id.input_image) }
    val imageButton by lazy<ImageButton> { view.findViewById(R.id.button_image) }

    val divInput by lazy<View> { view.findViewById(R.id.div_input) }
    val guideText by lazy<TextView > { view.findViewById(R.id.guide_text) }

    var inputText: String
        get() = input.text.toString()
        set(inputText) = input.setText(inputText)

    var inputType: Int
        get() = input.inputType
        set(inputType) {
            input.inputType = inputType
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
        getAttrs(attrs, defStyleAttr)
    }

    private fun initView() {
        addView(view)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OneLineTextInputView)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OneLineTextInputView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        var visibility = typedArray.getString(R.styleable.OneLineTextInputView_title_visibility)
        setVisible(textTitle, visibility)
        textTitle.text = typedArray.getString(R.styleable.OneLineTextInputView_title_text)

        visibility = typedArray.getString(R.styleable.OneLineTextInputView_option_visibility)
        setVisible(optionButton, visibility)
        optionButton.text = typedArray.getString(R.styleable.OneLineTextInputView_option_text)

        var text = typedArray.getString(R.styleable.OneLineTextInputView_button_text)
        setVisible(textButton, if (!text.isNullOrEmpty()) VIEW_VISIBLE else VIEW_GONE)
        textButton.text = text

        text = typedArray.getString(R.styleable.OneLineTextInputView_guide_text)
        setVisible(guideText, if (!text.isNullOrEmpty()) VIEW_VISIBLE else VIEW_GONE)
        guideText.text = text

        input.hint = typedArray.getString(R.styleable.OneLineTextInputView_input_hint)
        input.setText(typedArray.getString(R.styleable.OneLineTextInputView_input_text))
        input.inputType = typedArray.getInteger(R.styleable.OneLineTextInputView_android_inputType, TYPE_CLASS_TEXT)

        visibility = typedArray.getString(R.styleable.OneLineTextInputView_input_image_visibility)
        setVisible(inputImage, visibility)
        inputImage.setImageResource(typedArray.getResourceId(R.styleable.OneLineTextInputView_input_image_src, 0))

        visibility = typedArray.getString(R.styleable.OneLineTextInputView_button_image_visibility)
        setVisible(imageButton, visibility)
        imageButton.setImageResource(typedArray.getResourceId(R.styleable.OneLineTextInputView_button_image_src, 0))

        typedArray.recycle()
    }

    fun setTitleText(titleText: String) {
        textTitle.text = titleText
    }

    fun setTitleText(resId: Int) {
        textTitle.setText(resId)
    }

    fun setOptionText(optionText: String) {
        optionButton.text = optionText
    }

    fun setOptionText(resId: Int) {
        optionButton.setText(resId)
    }

    fun setButtonTextStr(textStrId: Int) {
        textButton.setText(textStrId)
    }

    fun setButtonTextStr(textStr: String) {
        textButton.text = textStr
    }

    fun setButtonTextColor(textColor: Int) {
        textButton.setTextColor(resources.getColor(textColor))
    }

    fun setInputTextColor(textColor: Int) {
        input.setTextColor(resources.getColor(textColor))
    }

    fun appendInputText(inputText: String) {
        input.append(inputText)
    }

    fun setInputText(resId: Int) {
        input.setText(resId)
    }

    fun setInputHint(inputText: String) {
        input.hint = inputText
    }

    fun setInputHint(resId: Int) {
        input.setHint(resId)
    }

    fun setInputMaxLength(maxLength: Int) {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(maxLength)
        input.filters = filterArray
    }

    fun setInputMaxLine(maxLine: Int) {
        input.maxLines = maxLine
    }

    fun setDisableInput(isDisable: Boolean, action: Consumer<Any>) {
        if (!isDisable) {
            RxView.clicks(input)
                .compose(bindUntilEvent(LayoutEvent.ON_DETACHED_FROM_WINDOW))
                .subscribe { }
            setFocusInput()
        }

        if (isDisable) {
            input.isFocusable = false
            input.isCursorVisible = false

            RxView.clicks(input)
                .compose(bindUntilEvent(LayoutEvent.ON_DETACHED_FROM_WINDOW))
                .subscribe(action)
        }
    }

    fun setRxInputImageAction(action: Consumer<Any>) {
        RxView.clicks(inputImage)
            .compose(bindUntilEvent(LayoutEvent.ON_DETACHED_FROM_WINDOW))
            .subscribe(action)
    }

    fun setRxInputImageAction(vararg filters: Predicate<Any>, action: Consumer<Any>) {
        val observer = RxView.clicks(inputImage)
            .compose(bindUntilEvent(LayoutEvent.ON_DETACHED_FROM_WINDOW))
        filters.forEach { observer.filter(it) }
        observer.subscribe(action)
    }

    fun setInputImageClickListener(listener: OnClickListener) {
        inputImage.setOnClickListener(listener)
    }

    fun setRxOptionButtonAction(action: Consumer<Any>) {
        RxView.clicks(optionButton)
            .compose(bindUntilEvent(LayoutEvent.ON_DETACHED_FROM_WINDOW))
            .subscribe(action)
    }

    fun setRxOptionButtonAction(vararg filters: Predicate<Any>, action: Consumer<Any>) {
        val observer = RxView.clicks(optionButton)
            .compose(bindUntilEvent(LayoutEvent.ON_DETACHED_FROM_WINDOW))
        filters.forEach { observer.filter(it) }
        observer.subscribe(action)
    }

    fun setOptionButtonClickListener(listener: OnClickListener) {
        optionButton.setOnClickListener(listener)
    }

    fun setRxTextButtonAction(action: Consumer<Any>) {
        RxView.clicks(textButton)
            .compose(bindUntilEvent(LayoutEvent.ON_DETACHED_FROM_WINDOW))
            .subscribe(action)
    }

    fun setRxTextButtonAction(vararg filters: Predicate<Any>, action: Consumer<Any>) {
        val observer = RxView.clicks(textButton)
            .compose(bindUntilEvent(LayoutEvent.ON_DETACHED_FROM_WINDOW))
        filters.forEach { observer.filter(it) }
        observer.subscribe(action)
    }

    fun setTextButtonClickListener(listener: OnClickListener) {
        textButton.setOnClickListener(listener)
    }

    fun setRxImageButtonAction(action: Consumer<Any>) {
        RxView.clicks(imageButton)
            .compose(bindUntilEvent(LayoutEvent.ON_DETACHED_FROM_WINDOW))
            .subscribe(action)
    }

    fun setRxImageButtonAction(vararg filters: Predicate<Any>, action: Consumer<Any>) {
        val observable = RxView.clicks(imageButton)
            .compose(bindUntilEvent(LayoutEvent.ON_DETACHED_FROM_WINDOW))
        filters.forEach { observable.filter(it) }
        observable.subscribe(action)
    }

    fun setButtonImageClickListener(listener: OnClickListener) {
        imageButton.setOnClickListener(listener)
    }

    private fun setVisible(view: View, visibility: String?) {
        if (visibility != null) {
            when (visibility) {
                VIEW_VISIBLE -> view.visibility = View.VISIBLE
                VIEW_INVISIBLE -> view.visibility = View.INVISIBLE
                VIEW_GONE -> view.visibility = View.GONE
                else -> view.visibility = View.VISIBLE
            }
        }
    }

    private fun setFocusInput() {
        input.postDelayed({
            input.isCursorVisible = true
            input.isFocusable = true
            input.isEnabled = true
            input.isFocusableInTouchMode = true
            input.requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input, 0)
        }, 100)
    }
}
