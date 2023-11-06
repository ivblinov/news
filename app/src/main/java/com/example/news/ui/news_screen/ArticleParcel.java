package com.example.news.ui.news_screen;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import kotlinx.parcelize.Parcelize;

@Parcelize
public class ArticleParcel implements Parcelable {

    public String urlToImage;
    public String title;
    public String publishedAt;
    public String content;
    public String name;

    public ArticleParcel(
            String urlToImage,
            String title,
            String publishedAt,
            String content,
            String name,
            String url
    ) {
        this.urlToImage = urlToImage;
        this.title = title;
        this.publishedAt = publishedAt;
        this.content = content;
        this.name = name;
        this.url = url;
    }

    protected ArticleParcel(Parcel in) {
        urlToImage = in.readString();
        title = in.readString();
        publishedAt = in.readString();
        content = in.readString();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<ArticleParcel> CREATOR = new Creator<ArticleParcel>() {
        @Override
        public ArticleParcel createFromParcel(Parcel in) {
            return new ArticleParcel(in);
        }

        @Override
        public ArticleParcel[] newArray(int size) {
            return new ArticleParcel[size];
        }
    };

    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(urlToImage);
        dest.writeString(title);
        dest.writeString(publishedAt);
        dest.writeString(content);
        dest.writeString(name);
        dest.writeString(url);
    }
}
