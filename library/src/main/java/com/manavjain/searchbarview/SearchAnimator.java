package com.manavjain.searchbarview;

import android.content.Context;
import android.support.constraint.ConstraintSet;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

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
                if (charSequence.toString().isEmpty()){
                    showClearButton(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No use
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()){
                    showClearButton(false);
                }

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


        ConstraintSet animationSet;

        if (reveal) {
            animationSet = mAnimatedSearchSet;

            if (mListener != null)
                mListener.onOpen();
        } else {
            animationSet = mSearchSet;

            if (!mSearchViewHolder.getSearchEditText().getText().toString().isEmpty())
                mSearchViewHolder.getSearchEditText().setText("");

            if (mListener != null)
                mListener.onClose();
        }

        alreadyRevealed = !alreadyRevealed;

        mSearchViewHolder.setEditTextFocus(reveal, mContext);
        animationSet.applyTo(mSearchViewHolder.getRootLayout());
    }

    private void showClearButton(boolean show) {
        Animation anim;
        if (show) {
            anim = AnimationUtils.loadAnimation(mContext, R.anim.open_clear_button);
            mSearchViewHolder.getClearButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchViewHolder.getSearchEditText().setText("");
                    mSearchViewHolder.getSearchEditText().requestFocusFromTouch();
                }
            });
        } else {
            anim = AnimationUtils.loadAnimation(mContext, R.anim.close_clear_button);
            mSearchViewHolder.getClearButton().setOnClickListener(null);
        }
        anim.setDuration(150);
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
