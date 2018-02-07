package com.tdin360.zjw.marathon;

import android.widget.EditText;

/**
 * Created by jeffery on 2018/2/7.
 */

public class PeopleClass {
    private EditText nameEt;
    private EditText nameIc;

    public EditText getNameEt() {
        return nameEt;
    }

    public void setNameEt(EditText nameEt) {
        this.nameEt = nameEt;
    }

    public EditText getNameIc() {
        return nameIc;
    }

    public void setNameIc(EditText nameIc) {
        this.nameIc = nameIc;
    }

    public PeopleClass(EditText nameEt, EditText getNameIc) {
        this.nameEt = nameEt;
        this.nameIc = getNameIc;
    }
}
