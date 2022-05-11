package com.example.cogentcodebucketassignment.model.responses;

import java.util.List;

//DATA CLASS
public class NewsTopHeadlineResponse {
    //DATA MEMBERS
    String status;
    int totalResults;
    List<NewsArticle> articles;

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }

    public static class NewsArticle {
        //DATA MEMBERS
        Source source;
        String author, title, description, url, urlToImage, publishedAt, content;

        public Source getSource() {
            return source;
        }

        public String getAuthor() {
            return author;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getContent() {
            return content;
        }

        public static class Source {
            //DATA MEMBERS
            String id, name;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
