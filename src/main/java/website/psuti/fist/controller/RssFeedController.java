package website.psuti.fist.controller;

import com.rometools.rome.feed.rss.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import website.psuti.fist.constant.NewsFacultyConstant;
import website.psuti.fist.constant.PathConstant;
import website.psuti.fist.constant.PictureConstant;
import website.psuti.fist.constant.UrlForSearch;
import website.psuti.fist.model.FeedEntry;
import website.psuti.fist.model.FeedInfo;
import website.psuti.fist.model.NewsOfFaculty;
import website.psuti.fist.service.NewsFacultyService;

import javax.servlet.http.HttpServletResponse;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RssFeedController {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NewsFacultyService newsFacultyService;

    @GetMapping("/rssFeed")
    public View handleForexRequest(Model model) {
        model.addAttribute("feedInfo", getFeedInfo());
        return (View)applicationContext.getBean("rssFeedView");
    }

    private FeedInfo getFeedInfo() {
        FeedInfo feedInfo = new FeedInfo();
        feedInfo.setTitle("Факультет информационных систем и технологий ПГУТИ");
        feedInfo.setLink(UrlForSearch.getUrlSite());
        feedInfo.setDesc("Новости факульета");
        feedInfo.setLanguage("ru-ru");
        Image image = new Image();
        image.setUrl(UrlForSearch.getUrlSite() + "/main/picture/" + PictureConstant.FAVICON.getId());
        image.setTitle("Иконка сайта");
        feedInfo.setImage(image);

        List<FeedEntry> list = new ArrayList<>();
        for (NewsOfFaculty topic : newsFacultyService.getLastCountByDateFilledPicture(NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_NEWSBLOG_OUTPUT.getCount())) {
            if (topic.getIdPicture() <= 0)
            list.add(createFeedEntry(topic.getHeading(),
                    UrlForSearch.URL_NEWS_BLOG.getApi() + "/topic/id="+ topic.getId(),
                    topic.getText(),
                    Date.from(topic.getDate().atZone(ZoneId.systemDefault()).toInstant()),
                    "bogomolova-ma@psuti.ru (bogomolova)"));
            else
                list.add(createFeedEntry(topic.getHeading(),
                    UrlForSearch.URL_NEWS_BLOG.getApi() + "/topic/id="+ topic.getId(),
                    "<p style=\"text-align: center;\"><img src=\"" + UrlForSearch.getUrlSite() + "/main/picture/" + topic.getIdPicture() + "\" text-align=\"center\" height=\"500px\"></p>  <br><br>" +
                            topic.getText(),
                    Date.from(topic.getDate().atZone(ZoneId.systemDefault()).toInstant()),
                        "bogomolova-ma@psuti.ru (bogomolova)"));

        }
        feedInfo.setFeedEntries(list);
        return feedInfo;
    }

    private static FeedEntry createFeedEntry(String title, String uri,
                                             String content, Date date, String author) {
        FeedEntry fe = new FeedEntry();
        fe.setTitle(title);
        fe.setLink(UrlForSearch.getUrlSite() + uri);
        fe.setContent(content);
        fe.setDate(date);
        fe.setAuthor(author);
        return fe;
    }
}
