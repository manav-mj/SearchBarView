package com.manavjain.searchbarview;

import android.support.constraint.ConstraintLayout;
import android.view.View;
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
}
