package website.psuti.fist.model;

import java.util.ArrayList;
import java.util.List;

public class FeedInfo {
    private String title;
    private String link;
    private String desc;
    private String language;
    private List<FeedEntry> feedEntries;

    public FeedInfo() {
        feedEntries = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<FeedEntry> getFeedEntries() {
        return feedEntries;
    }

    public void setFeedEntries(List<FeedEntry> feedEntries) {
        this.feedEntries = feedEntries;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
