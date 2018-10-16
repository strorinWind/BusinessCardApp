package ru.strorin.businesscardapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.util.Date;

public class NewsItem implements Serializable, Parcelable {

    private final String title;
    private final String imageUrl;
    private final Category category;
    private final Date publishDate;
    private final String previewText;
    private final String fullText;

    public NewsItem(String title, String imageUrl, Category category, Date publishDate, String previewText, String fullText) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
        this.publishDate = publishDate;
        this.previewText = previewText;
        this.fullText = fullText;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!NewsItem.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        NewsItem item = (NewsItem)obj;
        if (!title.equals(item.getTitle())) return false;
        if (!imageUrl.equals(item.getImageUrl())) return false;
        if (!category.equals(item.getCategory())) return false;
        if (!publishDate.equals(item.getPublishDate())) return false;
        if (!previewText.equals(item.getPreviewText())) return false;
        if (!fullText.equals(item.getFullText())) return false;

        return true;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Category getCategory() {
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
