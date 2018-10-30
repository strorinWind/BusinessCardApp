package ru.strorin.businesscardapp.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class MultimediaDTO {

    @SerializedName("url")
    private String url;

    @SerializedName("type")
    private String type;

    public String getUrl(){
        return url;
    }
}
