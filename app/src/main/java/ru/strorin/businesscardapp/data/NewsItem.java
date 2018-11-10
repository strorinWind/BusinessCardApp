package ru.strorin.businesscardapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import ru.strorin.businesscardapp.data.network.dto.NewsDTO;

public class NewsItem implements Serializable, Parcelable {

    private final String title;
    private final String imageUrl;
    private final String category;
    private final Date publishDate;
    private final String previewText;
    private final String fullText;

    public NewsItem(String title, String imageUrl, Category category, Date publishDate, String previewText, String fullText) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category.getName();
        this.publishDate = publishDate;
        this.previewText = previewText;
        this.fullText = fullText;
    }

    public NewsItem(NewsDTO news){
        this.title = news.getTitle();
        this.imageUrl = news.getImageUrl();
        this.category = news.getCategory();
        this.publishDate = news.getPublishDate();
        this.previewText = news.getPreviewText();
        this.fullText = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem newsItem = (NewsItem) o;
        return Objects.equals(title, newsItem.title) &&
                Objects.equals(imageUrl, newsItem.imageUrl) &&
                Objects.equals(category, newsItem.category) &&
                Objects.equals(publishDate, newsItem.publishDate) &&
                Objects.equals(previewText, newsItem.previewText) &&
                Objects.equals(fullText, newsItem.fullText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, imageUrl, category, publishDate, previewText, fullText);
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getPrettyDateString() {
        PrettyTime p = new PrettyTime();
        return p.format(publishDate);
    }

    public String getPreviewText() {
        return previewText;
    }

    public String getFullText() {
        return fullText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeSerializable(category);
        dest.writeSerializable(publishDate);
        dest.writeString(previewText);
        dest.writeString(fullText);
    }

    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel source) {
            String title = source.readString();
            String imageUrl = source.readString();
            Category category = (Category) source.readSerializable();
            Date publishDate = (Date) source.readSerializable();
            String previewText = source.readString();
            String fullText = source.readString();
            return new NewsItem(title, imageUrl, category, publishDate, previewText, fullText);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };
}

