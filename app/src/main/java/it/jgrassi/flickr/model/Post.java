package it.jgrassi.flickr.model;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by j.grassi on 24/11/2017.
 */

public class Post implements Serializable {
    public String title;
    public String link;
    public Image media;
    public Date date_taken;
    public String description;
    public Date published;
    public String author;
    public String author_id;
    public String tags;

    private static Pattern pattern_author = Pattern.compile("\"(.*)\"");
    private static String pattern_time = "dd/MM/yyyy hh:mm";

    public String getAuthorName(){
        Matcher matcher = pattern_author.matcher(author);
        matcher.find();
        return matcher.group(1);
    }

    public String getPublishedFormatted(){
        if(published != null && !published.equals(""))
            return new DateTime(published).toString(pattern_time);
        else
            return "-";
    }

    public String getTakenFormatted(){
        if(date_taken != null && !date_taken.equals(""))
            return new DateTime(date_taken).toString(pattern_time);
        else
            return "-";
    }
}
