package com.manavjain.searchbarview;

import android.content.Context;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by YourFather on 31-01-2018.
 */

class SearchAnimator {
    private static final long SEARCH_ANIMATION_DURATION = 150;

    private Context mContext;

    // ConstraintSets for animation
    private ConstraintSet mSearchSet, mAnimatedSearchSet;

    private boolean alreadyRevealed;

    private SearchViewHolder mSearchViewHolder;

    OnAnimationCompleteListener mListener;

    public SearchAnimator(Context context, SearchViewHolder searchViewHolder) {
        mContext = context;
        mSearchViewHolder = searchViewHolder;
        init();
    }

    private void init() {
        mSearchSet = new ConstraintSet();
        mSearchSet.clone(mContext, R.layout.search_view);
        mAnimatedSearchSet = new ConstraintSet();
        mAnimatedSearchSet.clone(mContext, R.layout.search_view_revealed);

        mSearchViewHolder.getSearchEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void revealSearchView(boolean reveal) {

        if (reveal == alreadyRevealed) {
            mSearchViewHolder.getSearchEditText().requestFocusFromTouch();
            return;
        }

        AutoTransition transition = new AutoTransition();
        transition.setDuration(SEARCH_ANIMATION_DURATION);
        TransitionManager.beginDelayedTransition(mSearchViewHolder.getRootLayout(), transition);

        mSearchViewHolder.getSearchEditText().setFocusable(reveal);

        ConstraintSet animationSet;

        if (reveal) {
            animationSet = mAnimatedSearchSet;

            mSearchViewHolder.getSearchEditText().requestFocusFromTouch();

            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mSearchViewHolder.getSearchEditText(), InputMethodManager.SHOW_IMPLICIT);

            if (mListener != null)
                mListener.onOpen();
        } else {
            animationSet = mSearchSet;

            if (!mSearchViewHolder.getSearchEditText().getText().toString().isEmpty())
                mSearchViewHolder.getSearchEditText().setText("");

            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mSearchViewHolder.getSearchEditText().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

            if (mListener != null)
                mListener.onClose();
        }

        alreadyRevealed = !alreadyRevealed;

        animationSet.applyTo(mSearchViewHolder.getRootLayout());
    }

    private void showClearButton(boolean show) {
        Animation anim;
        if (show) {
            anim = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
            mSearchViewHolder.getClearButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchViewHolder.getSearchEditText().setText("");
                    mSearchViewHolder.getSearchEditText().requestFocusFromTouch();
                }
            });
        } else {
            anim = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
            mSearchViewHolder.getClearButton().setOnClickListener(null);
        }
        mSearchViewHolder.getClearButton().setVisibility(View.VISIBLE);
        mSearchViewHolder.getClearButton().startAnimation(anim);
    }

    public void setOnAnimationCompleteListener(OnAnimationCompleteListener listener){
        mListener = listener;
    }

    public interface OnAnimationCompleteListener{
        void onOpen();
        void onClose();
    }
}
