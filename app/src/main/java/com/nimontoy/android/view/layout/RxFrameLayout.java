package com.nimontoy.android.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by chengXing on 2016/9/18.
 */
public class RxFrameLayout extends FrameLayout implements LayoutLifecycleProvider {

    private BehaviorSubject<LayoutEvent> lifecycleSubject = BehaviorSubject.create();

    public RxFrameLayout(Context context) {
        super(context);
    }

    public RxFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RxFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull LayoutEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
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