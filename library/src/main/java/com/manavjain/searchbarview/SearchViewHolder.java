package com.manavjain.searchbarview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by YourFather on 31-01-2018.
 */

class SearchViewHolder {
    private EditText mSearchEditText;
    private TextView mCancelTextView;
    private ImageView mClearButton;
    private ConstraintLayout mRootLayout;

    public SearchViewHolder(View parent) {
        mSearchEditText = parent.findViewById(R.id.search_view_edit_text);
        mCancelTextView = parent.findViewById(R.id.search_view_cancel);
        mClearButton = parent.findViewById(R.id.search_clear_button);
        mRootLayout = parent.findViewById(R.id.search_view_constraint_layout);
    }

    public EditText getSearchEditText() {
        return mSearchEditText;
    }

    public TextView getCancelTextView() {
        return mCancelTextView;
    }

    public ImageView getClearButton() {
        return mClearButton;
    }

    public ConstraintLayout getRootLayout() {
        return mRootLayout;
    }

    public void setEditTextFocus(boolean b, Context context) {
        mSearchEditText.setFocusable(b);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (b) {
            imm.showSoftInput(mSearchEditText, InputMethodManager.SHOW_IMPLICIT);
            mSearchEditText.requestFocusFromTouch();
        }else {
            imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }
}
