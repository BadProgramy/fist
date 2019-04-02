package website.psuti.fist.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.UrlForSearch;
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
    private final int RANDE_PLUS_MINUS_OUTPUT_TEXT = 200;//диапазон символов на вывод резульата
    private final int COUNT_OUTPUT_RESULT_SEARCH = 15; //количесвто выводов поиска
    private final int MIN_COUNT_CHARACTER_INPUT_WORD = 2;//минимальныое количество символов для поиска

    private HashMap<String, String> parse(String url, String word) {
        HashMap<String, String> result = new LinkedHashMap<>();
        try {
            InputStreamReader in = new InputStreamReader(new URL(url).openStream(), Charset.forName("utf-8"));
            StringBuilder input = new StringBuilder();
            StringBuilder inputForOutput = new StringBuilder();
            String text = "";
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
            input = new StringBuilder(StripHtml(Jsoup.parseBodyFragment(input.toString())));
            inputForOutput = new StringBuilder(StripHtml(Jsoup.parseBodyFragment(input.toString())));
            Pattern pattern = Pattern.compile(word);
            Matcher matcher = pattern.matcher(input);
            if (!title.toLowerCase().contains(word) || title.toLowerCase().replace(word, "").length() > 4) // если загаловок совпадает с входным словом поиска
            {
                if (matcher.find()) {
                    if (matcher.start() > RANDE_PLUS_MINUS_OUTPUT_TEXT && matcher.end() + RANDE_PLUS_MINUS_OUTPUT_TEXT < inputForOutput.toString().toCharArray().length) {
                        text = "..." + inputForOutput.substring(matcher.start() - RANDE_PLUS_MINUS_OUTPUT_TEXT, matcher.end() + RANDE_PLUS_MINUS_OUTPUT_TEXT) + "...";
                        result.put(text, title);
                    } else if (matcher.start() > RANDE_PLUS_MINUS_OUTPUT_TEXT) {
                        text = "..." + inputForOutput.substring(matcher.start() - RANDE_PLUS_MINUS_OUTPUT_TEXT, inputForOutput.length() - 1);
                        result.put(text, title);
                    } else if (matcher.end() + RANDE_PLUS_MINUS_OUTPUT_TEXT < inputForOutput.toString().toCharArray().length) {
                        text = inputForOutput.substring(0, matcher.end() + RANDE_PLUS_MINUS_OUTPUT_TEXT) + "...";
                        result.put(text, title);
                    } else {
                        text = inputForOutput.substring(0, inputForOutput.length() - 1);
                        result.put(text, title);
                    }
                    //if (result.size() == COUNT_OUTPUT_RESULT_SEARCH) return result;
                }
            } else {
                result.put(text, title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String StripHtml(Document thing){
        String[] tags = {"footer", "header", "head"};
        for (String tag : tags) {
            for (Element elem : thing.getElementsByTag(tag)) {
                elem.remove();
            }
        }
        return thing.body().text();
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, @RequestParam("searchword") String word) {
        model.addAllAttributes(modelAndViewConfiguration.initModelAndView().getModel());
        List<SearchObject> searchObjects = new ArrayList<>();
        HashMap<String, String> result = new HashMap<>();
        if (word.length() > MIN_COUNT_CHARACTER_INPUT_WORD) { //Начинаем поиск, если пользователь ввел более 2х букв
            String[] argi = UrlForSearch.getListURL();//получаем все ссылки, где будет вести поиск
            for (int i = 0; i < argi.length; i++) {
                result.clear();
                result = parse(argi[i], word.toLowerCase());
                for (Map.Entry entry : result.entrySet()) {
                    searchObjects.add(new SearchObject(entry.getValue().toString(), argi[i], entry.getKey().toString()));
                    if (searchObjects.size() >= COUNT_OUTPUT_RESULT_SEARCH) { // максимум результат вывода
                        model.addAttribute("resultSearch", sorted(searchObjects));
                        return "search";
                    }
                }
            }
        }
        model.addAttribute("resultSearch",  sorted(searchObjects));
        return "search";
    }

    private List<SearchObject> sorted(List<SearchObject> searchObjects){
        List<SearchObject> sortedList = new ArrayList<>();
        for (SearchObject searchObject : searchObjects) {
            if (searchObject.getText().equals("")) sortedList.add(0, searchObject);
            else sortedList.add(searchObject);
        }
        return sortedList;
    }
}
