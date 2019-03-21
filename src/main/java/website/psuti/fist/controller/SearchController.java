package website.psuti.fist.controller;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.model.SearchObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Controller
public class SearchController {
    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    private static final Pattern TITLE = Pattern.compile("\\<title\\>(.*)\\<\\/title\\>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    private HashMap<String, String> search(String url, String word) {
        HashMap<String, String> result = new LinkedHashMap<>();
        try {
            InputStreamReader in = new InputStreamReader(new URL(url).openStream(), Charset.forName("utf-8"));
            StringBuilder input = new StringBuilder();
            StringBuilder inputForOutput = new StringBuilder();
            String text;
            String title = "Ссылка";
            int ch;
            while ((ch = in.read()) != -1) {
                input.append(String.valueOf((char) ch).toLowerCase());
                inputForOutput.append((char) ch);
            }
            Matcher matcherTitle = TITLE.matcher(inputForOutput);
            if (matcherTitle.find()) {
                title = matcherTitle.group(1);
            }
            input = new StringBuilder(Jsoup.parse(input.toString()).text());
            inputForOutput = new StringBuilder(Jsoup.parse(inputForOutput.toString()).text());
            Pattern pattern = Pattern.compile(word);
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                text ="..." + inputForOutput.substring(matcher.start() - 200, matcher.end() + 200) + "...";
                result.put(text, title );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping("/search")
    public String s(Model model) {
        model.addAllAttributes(modelAndViewConfiguration.initModelAndView().getModel());
        List<SearchObject> searchObjects = new ArrayList<>();
        String[] argi = { "http://localhost:8081/faculty/cathedras"};
        for (int i = 0; i < argi.length; i++) {
            HashMap<String, String> result = search(argi[i], "заведующий кафедр".toLowerCase());
            for (Map.Entry entry : result.entrySet()) {
                searchObjects.add(new SearchObject(entry.getValue().toString(), argi[i], entry.getKey().toString()));
            }
        }
        model.addAttribute("resultSearch", searchObjects);
        return "search";
    }
}
