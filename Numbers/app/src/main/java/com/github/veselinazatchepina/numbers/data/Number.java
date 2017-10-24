package com.github.veselinazatchepina.numbers.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Number {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("found")
    @Expose
    private String found;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    @Override
    public String toString() {
        return "ClassPojo [text = " + text + ", number = " + number + ", type = " + type + ", found = " + found + "]";
    }
}