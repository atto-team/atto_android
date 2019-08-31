package com.nimontoy.android.view.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import io.reactivex.subjects.BehaviorSubject;


/**
 * Created by plu on 2016/8/29.
 */
@SuppressLint("CheckResult")
public class RxLinearLayout extends LinearLayout implements LayoutLifecycleProvider {
    private final BehaviorSubject<LayoutEvent> lifecycleSubject = BehaviorSubject.create();

    public RxLinearLayout(Context context) {
        super(context);
    }

    public RxLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RxLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull LayoutEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject,event);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        lifecycleSubject.onNext(LayoutEvent.ON_ATTACHED_TO_WINDOW);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        lifecycleSubject.onNext(LayoutEvent.ON_DETACHED_FROM_WINDOW);
    }
}