package com.example.asssignment;

public class Post {
    private String title , image;

    public Post(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
