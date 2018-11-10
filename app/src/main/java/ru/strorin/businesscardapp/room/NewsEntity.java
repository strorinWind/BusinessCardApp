package ru.strorin.businesscardapp.room;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news")
public class NewsEntity {

    private NewsEntity(){
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "subsection")
    private String subsection;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "published_date")
    private Date publishedDate;

    @ColumnInfo(name = "image_url")
    private String imageUrl;
}

