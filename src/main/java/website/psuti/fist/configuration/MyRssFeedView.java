package website.psuti.fist.configuration;

import com.rometools.rome.feed.rss.Description;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Item;
import org.springframework.stereotype.Component;
import website.psuti.fist.model.FeedEntry;
import website.psuti.fist.model.FeedInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("rssFeedView")
public class MyRssFeedView extends AbstractRssFeedView {

    @Override
    protected void buildFeedMetadata(Map<String, Object> model,
                                     Channel feedChannel,
                                     HttpServletRequest request) {

        FeedInfo feedInfo = (FeedInfo) model.get("feedInfo");
        feedChannel.setTitle(feedInfo.getTitle());
        feedChannel.setLink(feedInfo.getLink());
        feedChannel.setDescription(feedInfo.getDesc());
        feedChannel.setLanguage(feedInfo.getLanguage());
        feedChannel.setEncoding("utf-8");
        feedChannel.setImage(feedInfo.getImage());
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model,
                                        HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse)
            throws Exception {
        this.setContentType("application/rss+xml; charset=utf-8");
        List<Item> items = new ArrayList<>();
        FeedInfo feedInfo = (FeedInfo) model.get("feedInfo");

        for (FeedEntry feedEntry : feedInfo.getFeedEntries()) {
            Item item = new Item();
            item.setTitle(feedEntry.getTitle());
            item.setLink(feedEntry.getLink());
            item.setPubDate(feedEntry.getDate());

            Description description = new Description();
            description.setValue(feedEntry.getContent());
            item.setDescription(description);

            item.setAuthor(feedEntry.getAuthor());

            items.add(item);
        }
        return items;
    }

}
