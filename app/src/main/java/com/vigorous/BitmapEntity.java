package com.vigorous;

/**
 * Created by admin .
 */

public class BitmapEntity {
    public String _id;
    public String _createdAt;
    public String _desc;
    public String _publishedAt;
    public String _source;
    public String _type;
    public String _url;
    public String _used;
    public String _who;

    @Override
    public String toString() {
        return "url: " +  _url;
    }
}
