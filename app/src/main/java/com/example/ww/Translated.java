package com.example.ww;

import java.util.ArrayList;

public class Translated {

    private float code;
    private String lang;
    ArrayList< Object > text = new ArrayList < Object > ();


    // Getter Methods

    public float getCode() {
        return code;
    }

    public String getLang() {
        return lang;
    }

    public ArrayList<Object> getText() {
        return text;
    }

    // Setter Methods

    public void setCode(float code) {
        this.code = code;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
