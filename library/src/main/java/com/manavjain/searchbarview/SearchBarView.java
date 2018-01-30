package com.manavjain.searchbarview;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by YourFather on 30-01-2018.
 */

public class SearchBarView extends ConstraintLayout {
    private Context mContext;

    private SearchViewHolder mSearchViewHolder;
    private SearchAnimator mAnimator;
    private boolean mAnimate;

    public SearchBarView(Context context) {
        super(context);
        initialize(context);
    }

    public SearchBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        mContext = context;
        inflate(mContext, R.layout.search_view, this);

        mSearchViewHolder = new SearchViewHolder(this);
        loadSearchView();
    }

    private void loadSearchView() {
        if (!mAnimate) {
            mSearchViewHolder.getRootLayout().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchViewHolder.setEditTextFocus(true, mContext);
                }
            });
            mSearchViewHolder.getSearchEditText().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchViewHolder.setEditTextFocus(true, mContext);
                }
            });
        }else {
            mSearchViewHolder.getRootLayout().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    open();
                }
            });
            mSearchViewHolder.getSearchEditText().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    open();
                }
            });
            mSearchViewHolder.getCancelTextView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    close();
                }
            });
        }
    }

    @Override
    public void setBackgroundResource(int resId) {
        mSearchViewHolder.getRootLayout().setBackgroundResource(resId);
    }

    public void withAnimation(boolean animate) {
        mAnimate = animate;
        if (mAnimate) {
            mAnimator = new SearchAnimator(mContext, mSearchViewHolder);
        }
        loadSearchView();
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

    public void open(){
        if (mAnimate)
            mAnimator.revealSearchView(true);
    }

    public void close(){
        if (mAnimate)
            mAnimator.revealSearchView(false);
    }
}
