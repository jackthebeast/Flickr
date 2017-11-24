package it.jgrassi.flickr.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by j.grassi on 24/11/2017.
 */

public class FeedResponse implements Serializable {
    public String title;
    public String link;
    public String description;
    public String modified;
    public String generator;
    public List<Post> items;
}
