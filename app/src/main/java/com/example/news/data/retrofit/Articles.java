package com.example.news.data.retrofit;

import com.google.gson.annotations.SerializedName;

public class Articles {
    @SerializedName("totalResults")
    public int totalResults;

    @SerializedName("articles")
    public Article[] article;

    public static class Article {

        @SerializedName("source")
        public Source source;

        @SerializedName("author")
        public String author;

        @SerializedName("title")
        public String title;

        @SerializedName("description")
        public String description;

        @SerializedName("url")
        public String url;

        @SerializedName("urlToImage")
        public String urlToImage;

        @SerializedName("publishedAt")
        public String publishedAt;

        @SerializedName("content")
        public String content;

    }

    public static class Source {

        @SerializedName("name")
        public String name;
    }
}