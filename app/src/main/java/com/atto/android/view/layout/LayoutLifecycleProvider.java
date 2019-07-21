package com.atto.android.view.layout;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by plu on 2016/8/29.
 */
public interface LayoutLifecycleProvider {
    @NonNull
    @CheckResult
    <T>LifecycleTransformer<T> bindUntilEvent(@NonNull LayoutEvent event);
}