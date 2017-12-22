package com.github.codehorde.validator.internal;

import com.github.codehorde.validator.constraints.EscapeString;
import org.jsoup.safety.Whitelist;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baomingfeng at 2017-12-04 18:39:12
 */
public final class Util {

    public static Whitelist getWhitelistConfig(EscapeString constraintAnn) {
        Whitelist whitelist = null;
        switch (constraintAnn.passType()) {
            case NONE_ESCAPE:
                whitelist = null;//without any escapes(default escapes: '<', '>', '&', '\'', '\"' ).
                break;
            case NONE_TAG:
                whitelist = none();
                break;
            case BASIC:
                whitelist = basic();
                break;
            case BASIC_WITH_IMAGES:
                whitelist = basicWithImages();
                break;
            case RELAXED:
                whitelist = relaxed();
                break;
            case SIMPLE_TEXT:
                whitelist = simpleText();
                break;
        }
        if (whitelist != null) {
            whitelist.addTags(constraintAnn.additionalTags());
        }
        return whitelist;
    }

    private static Whitelist none() {
        return new Whitelist();
    }

    private static Whitelist simpleText() {
        return new Whitelist()
                .addTags("b", "em", "i", "strong", "u")
                ;
    }

    private static Whitelist basic() {
        return new Whitelist()
                .addTags(
                        "a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em",
                        "i", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", "sub",
                        "sup", "u", "ul")

                .addAttributes("a", "href")
                .addAttributes("blockquote", "cite")
                .addAttributes("q", "cite")

                .addProtocols("a", "href", "ftp", "http", "https", "mailto")
                .addProtocols("blockquote", "cite", "http", "https")
                .addProtocols("cite", "cite", "http", "https")

                .addEnforcedAttribute("a", "rel", "nofollow")
                ;

    }

    private static Whitelist basicWithImages() {
        return basic()
                .addTags("img")
                .addAttributes("img", "align", "alt", "height", "src", "title", "width")
                .addProtocols("img", "src", "http", "https")
                ;
    }

    private static Whitelist relaxed() {
        return new Whitelist()
                .addTags(
                        "a", "b", "s", "blockquote", "br", "caption", "cite", "code", "col",
                        "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
                        "i", "img", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong",
                        "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
                        "ul")

                .addAttributes("a", "href", "title")//a允许有href, title属性
                .addAttributes("blockquote", "cite")
                .addAttributes("col", "span", "width")
                .addAttributes("colgroup", "span", "width")
                .addAttributes("img", "align", "alt", "height", "src", "title", "width")
                .addAttributes("ol", "start", "type")
                .addAttributes("q", "cite")
                .addAttributes("table", "summary", "width")
                .addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width")
                .addAttributes(
                        "th", "abbr", "axis", "colspan", "rowspan", "scope",
                        "width")
                .addAttributes("ul", "type")

                .addAttributes(":all", "style")//上述的tag允许有style属性

                .addProtocols("a", "href", "ftp", "http", "https", "mailto")
                .addProtocols("blockquote", "cite", "http", "https")
                .addProtocols("cite", "cite", "http", "https")
                .addProtocols("img", "src", "http", "https")
                .addProtocols("q", "cite", "http", "https")
                ;
    }

    // The replacement array (see ArrayBasedEscaperMap).
    private final static char[][] escapers;

    static {
        try {
            final Map<Character, String> replacementMap =
                    new HashMap<Character, String>();
            replacementMap.put('"', "&quot;");
            replacementMap.put('\'', "&#39;");
            replacementMap.put('&', "&amp;");
            replacementMap.put('<', "&lt;");
            replacementMap.put('>', "&gt;");
            char max = Collections.max(replacementMap.keySet());
            escapers = new char[max + 1][];
            for (Character c : replacementMap.keySet()) {
                escapers[c] = replacementMap.get(c).toCharArray();
            }
        } catch (RuntimeException re) {
            throw new ExceptionInInitializerError(re);
        }
    }

    public static boolean hasEscapeChar(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < escapers.length && escapers[c] != null) {
                return true;
            }
        }
        return false;
    }

    private Util() {
    }
}
