package com.manavjain.searchbarview;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintSet;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by YourFather on 30-01-2018.
 */

public class SearchBarView extends View {
    private Context mContext;

    // ConstraintSets for animation
    private ConstraintSet mSearchSet, mAnimatedSearchSet;

    private SearchViewHolder mSearchViewHolder;
    private SearchAnimator mAnimator;
    private SearchDebounce searchDebounce;

    public SearchBarView(Context context) {
        super(context);
        initialize(context);
    }

    public SearchBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void initialize(Context context) {
        mContext = context;
        mSearchViewHolder = new SearchViewHolder(this);

        inflate(context, R.layout.search_view, null);
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
    }

    public void withAnimation(boolean animate) {
        if (animate)
            mAnimator = new SearchAnimator(mContext, mSearchViewHolder);
    }

    public void setText(CharSequence text) {

    }

    public void addTextChangedListener(TextWatcher watcher) {
        mSearchViewHolder.getSearchEditText().addTextChangedListener(watcher);
    }

    public void addDebounceCallback(long delayMillis, SearchDebounce.DebounceCallback callback){
        SearchDebounce.create(mSearchViewHolder.getSearchEditText(), delayMillis)
                .watch(callback);
    }
}
