package com.example.babycare.Content;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mDescription;
    private String link;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl,String description,String mlink) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        link = mlink;
        mName = name;
        mImageUrl = imageUrl;
        mDescription = description;

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "mName='" + mName + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}