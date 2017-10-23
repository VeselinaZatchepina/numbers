package com.github.veselinazatchepina.numbers.data;


public class Number {

    private String mId;
    private long mNumberValue;
    private String mQueryType;
    private String mDescription;
    private String mQueryDate;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public long getNumberValue() {
        return mNumberValue;
    }

    public void setNumberValue(long numberValue) {
        mNumberValue = numberValue;
    }

    public String getQueryType() {
        return mQueryType;
    }

    public void setQueryType(String queryType) {
        mQueryType = queryType;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getQueryDate() {
        return mQueryDate;
    }

    public void setQueryDate(String queryDate) {
        mQueryDate = queryDate;
    }
}
