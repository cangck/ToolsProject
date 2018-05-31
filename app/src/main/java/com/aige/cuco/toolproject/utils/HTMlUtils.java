package com.aige.cuco.toolproject.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wusourece on 2017/10/31.
 *   html 的正则表达式
 */

public class HTMlUtils  {

    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";

    /**
     * html中所有的img标签
     */
    static String REG = "<img.*?>";

    // 定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    static String regEx_special = "\\&[a-zA-Z]{1,10};";



    public static String htmlSwitch(String htmlStr) {

        Map cidMap = new HashMap<String, String>();
        List fckImages = new ArrayList<String>();

        // 过滤script标签
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        // 过滤style标签
        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        // 过滤html标签
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        // 过滤空格回车标签
        Pattern p_space = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");

        Pattern n_space = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
        Matcher n_matcher = n_space.matcher(htmlStr);
        htmlStr = n_matcher.replaceAll("");

        Pattern img_space = Pattern
                .compile("(?s)<img.*?(src=\"(.*?((common/FCKeditor.*?)|(cms/simpleDownload\\?fileId=([0-9a-zA-Z]*))))\")");
        Matcher img_matcher = img_space.matcher(htmlStr);
        while (img_matcher.find()) {
            String img = img_matcher.group(0);
            String src = img_matcher.group(1);
            String url = img_matcher.group(2);
            String g3_fckUrl = img_matcher.group(3);// 除去上下文的服务器web下相对路径
            String fileId = img_matcher.group(6);// ([0-9a-zA-Z]*)
            if (fileId != null) {
                cidMap.put(fileId, "true");
                String newUrl = "cid:" + fileId;
                img = img.replace(url, newUrl);
            } else {
                String cid = UUID.randomUUID().toString();
                String newUrl = "cid:" + cid;
                img = img.replace(url, newUrl);
                fckImages.add(g3_fckUrl);
            }
            Log.e("===img",img);
        }
            return htmlStr.trim(); // 返回文本字符串

        }

    public static List getImgStr(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        List pics = new ArrayList();
        String regEx_img = "]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

}
