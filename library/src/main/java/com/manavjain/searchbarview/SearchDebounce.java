package com.manavjain.searchbarview;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;

public final class SearchDebounce {
    private final WeakReference<EditText> editTextWeakReference;
    private final Handler debounceHandler;
    private DebounceCallback debounceCallback;
    private Runnable debounceWorker;
    private long delayMillis;
    private final TextWatcher textWatcher;

    public static SearchDebounce create(@NonNull EditText editText) {
        return new SearchDebounce(editText);
    }

    public static SearchDebounce create(@NonNull EditText editText, long delayMillis) {
        SearchDebounce searchDebounce = new SearchDebounce(editText);
        searchDebounce.setDelayMillis(delayMillis);
        return searchDebounce;
    }

    private SearchDebounce(@NonNull EditText editText) {
        this.debounceHandler = new Handler(Looper.getMainLooper());
        this.debounceWorker = new DebounceRunnable("", null);
        this.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //unused
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //unused
            }

            @Override
            public void afterTextChanged(Editable s) {
                debounceHandler.removeCallbacks(debounceWorker);
                if (!s.toString().isEmpty()) {
                    debounceWorker = new DebounceRunnable(s.toString(), debounceCallback);
                    debounceHandler.postDelayed(debounceWorker, delayMillis);
                }
            }
        };
        this.editTextWeakReference = new WeakReference<>(editText);
        EditText editTextInternal = this.editTextWeakReference.get();
        if (editTextInternal != null) {
            editTextInternal.addTextChangedListener(textWatcher);
        }
    }

    public void watch(@Nullable DebounceCallback debounceCallback) {
        this.debounceCallback = debounceCallback;
    }

    public void watch(@Nullable DebounceCallback debounceCallback, int delayMillis) {
        this.debounceCallback = debounceCallback;
        this.delayMillis = delayMillis;
    }

    public void unwatch() {
        if (editTextWeakReference != null) {
            EditText editText = editTextWeakReference.get();
            if (editText != null) {
                editText.removeTextChangedListener(textWatcher);
                editTextWeakReference.clear();
                debounceHandler.removeCallbacks(debounceWorker);
            }
        }
    }

    private void setDelayMillis(long delayMillis) {
        this.delayMillis = delayMillis;
    }

    private static class DebounceRunnable implements Runnable {

        private final String result;
        private final DebounceCallback debounceCallback;

        DebounceRunnable(String result, DebounceCallback debounceCallback) {
            this.result = result;
            this.debounceCallback = debounceCallback;
        }

        @Override
        public void run() {
            if (debounceCallback != null) {
                debounceCallback.onFinished(result);
            }
        }
    }

    public interface DebounceCallback {
        void onFinished(@NonNull String result);
    }

}