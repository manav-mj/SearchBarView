package com.manavjain.searchbarview;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by YourFather on 31-01-2018.
 */

public class FirstAndLastCharacterWatcher implements TextWatcher {

    boolean isFirstChar = true;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (isFirstChar){
//            characterListener
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    interface CharacterListener{
//        void firstChar
    }
}
