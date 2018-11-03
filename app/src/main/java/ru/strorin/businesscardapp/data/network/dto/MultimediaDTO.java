package ru.strorin.businesscardapp.data.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MultimediaDTO implements Serializable{

    @SerializedName("url")
    private String url;

    @SerializedName("type")
    private String type;

    public String getUrl(){
        return url;
    }
}
