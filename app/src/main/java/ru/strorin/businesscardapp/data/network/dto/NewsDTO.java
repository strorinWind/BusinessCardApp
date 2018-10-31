package ru.strorin.businesscardapp.data.network.dto;

import com.google.gson.annotations.SerializedName;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class NewsDTO implements Serializable {

    @SerializedName("subsection")
    private String subsection;

    @SerializedName("title")
    private String title;

    @SerializedName("abstract")
    private String previewText;

    @SerializedName("published_date")
    private Date publishedDate;

    @SerializedName("url")
    private String url;

    @SerializedName("multimedia")
    private List<MultimediaDTO> multimedia;

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        if (multimedia.isEmpty()) {
            return null;
        }
        return multimedia.get(0).getUrl();
    }

    public String getCategory() {
        return subsection;
    }

    public Date getPublishDate() {
        return publishedDate;
    }

    public String getPrettyDateString() {
        PrettyTime p = new PrettyTime();
        return p.format(publishedDate);
    }

    public String getPreviewText() {
        return previewText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsDTO newsItem = (NewsDTO) o;
        return Objects.equals(title, newsItem.title) &&
                Objects.equals(subsection, newsItem.subsection) &&
                Objects.equals(publishedDate, newsItem.publishedDate) &&
                Objects.equals(previewText, newsItem.previewText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, subsection, publishedDate, previewText);
    }
}
