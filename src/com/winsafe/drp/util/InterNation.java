package com.winsafe.drp.util;


import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;

import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.PropertysBean;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InterNation {

    private static Logger log = Logger.getLogger(Internation.class);

    private static String commonalityConfigPath = "global.sys.SystemResource";

    public static Locale getTrueLocale(HttpServletRequest request) {
        Locale locale = request.getLocale();
        HttpSession session = request.getSession();
        Locale localeActrual = (Locale) session
                .getAttribute(Globals.LOCALE_KEY);
        ResourceBundle source = null;
        if (localeActrual != null) {
            if (!localeActrual.getLanguage().equals(locale.getLanguage())) {
                locale = localeActrual;
            }
        }
        return locale;
    }

    private static PropertysBean getPropertysBean(String value)
            throws Exception {
        StringTokenizer token = new StringTokenizer(value, "|");
        if (token.countTokens() == 1) {
            throw new Exception("6");
        }
        PropertysBean bean = new PropertysBean();
        String order = null;
        String content = null;
        int count = 0;
        while (token.hasMoreTokens()) {
            if (count == 0) {
                order = token.nextToken();
                bean.setOrder(order);
            } else {
                content = token.nextToken();
                bean.setContent(content);
            }
            count++;
        }
        return bean;
    }

    private static Vector getVector(ResourceBundle resourc, String key)
            throws Exception {
        if (key == null) {
            throw new Exception("1");

        }
        String value = null;
        try {
            value = resourc.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3");

        }
        Vector vector = new Vector();
        while (token.hasMoreTokens()) {
            vector.add(getPropertysBean(token.nextToken()));
        }
        return vector;
    }

    private static String getString(ResourceBundle resourc, String key)
            throws Exception {

        if (key == null) {
            throw new Exception("1");

        }
        String value = null;
        try {
            value = resourc.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        return value;
    }

    private static String getString(ResourceBundle resourc, String key,
            int position) throws Exception {

        if (key == null) {
            throw new Exception("1");

        }
        if (position < 0) {
            throw new Exception("4");
        }
        String value = null;
        try {
            value = resourc.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        int count = 0;
        boolean status = true;
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            count = Integer.parseInt(getPropertysBean(value).getOrder());
            value = getPropertysBean(value).getContent();
            if (count == position) {
                status = false;
                break;
            }
        }
        if (status) {
            throw new Exception("5");//位置大于总数
        }
        return value;
    }

    private static String getString(ResourceBundle resourc, String key,
            String position) throws Exception {

        if (key == null) {
            throw new Exception("1");

        }
        if (Integer.parseInt(position) < 0) {
            throw new Exception("4");
        }
        String value = null;
        try {
            value = resourc.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        int count = 0;
        boolean status = true;
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            count = Integer.parseInt(getPropertysBean(value).getOrder());
            value = getPropertysBean(value).getContent();
            if (count == Integer.parseInt(position)) {
                status = false;
                break;
            }
        }
        if (status) {
            throw new Exception("5");//位置大于总数
        }
        return value;
    }

    private static String getString(ResourceBundle resourc, String key,
            String position, boolean status1) throws Exception {

        if (key == null) {
            throw new Exception("1");

        }
        if (Integer.parseInt(position) < 0) {
            throw new Exception("4");
        }
        String value = null;
        try {
            value = resourc.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        String count = "";
        boolean status = true;
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            count = getPropertysBean(value).getOrder();
            value = getPropertysBean(value).getContent();

            if (status1) {
                if (count.equals(position)) {
                    status = false;
                    break;
                }
            } else {
                if (count.equalsIgnoreCase(position)) {
                    status = false;
                    break;
                }
            }
        }
        if (status) {
            throw new Exception("5");
        }
        return value;
    }

    private static String getSelectTag(ResourceBundle source, String key,
            String tagName, int defaultSelected, boolean all) throws Exception {
        if (key == null) {
            throw new Exception("1");

        }
        if (defaultSelected < 0) {
            throw new Exception("4");
        }
        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        boolean count = true;
        String select = null;
        if (all) {
            select = "<select name=\"" + tagName
                    + "\"><option value=\"all\">选择</option>";
        } else {
            select = "<select name=\"" + tagName + "\">";
        }
        String option = "";
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            bean = getPropertysBean(value);
            if (Integer.parseInt(bean.getOrder()) != defaultSelected) {
                option += "<option value=\"" + bean.getOrder() + "\">"
                        + bean.getContent() + "</option></br>";
                count = false;
            } else {
                option += "<option value=\"" + bean.getOrder()
                        + "\" selected >" + bean.getContent()
                        + "</option></br>";
            }
        }
        select = select + option + "</select>";
        return select;
    }

    private static String getSelectTag1(ResourceBundle source, String key,
            String tagName, int defaultSelected, boolean all) throws Exception {
        if (key == null) {
            throw new Exception("1");

        }
        if (defaultSelected < 0) {
            throw new Exception("4");
        }
        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        if (token.countTokens() - 1 < defaultSelected) {
            throw new Exception("5");//位置大于总数
        }
        boolean count = true;
        String select = null;
        if (all) {
            select = "<select disabled name=\"" + tagName
                    + "\"><option value=\"all\">选择</option>";
        } else {
            select = "<select disabled name=\"" + tagName + "\">";
        }
        String option = "";
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            bean = getPropertysBean(value);
            if (Integer.parseInt(bean.getOrder()) != defaultSelected) {
                option += "<option value=\"" + bean.getOrder() + "\">"
                        + bean.getContent() + "</option></br>";
                count = false;
            } else {
                option += "<option value=\"" + bean.getOrder()
                        + "\" selected >" + bean.getContent()
                        + "</option></br>";
            }
        }
        select = select + option + "</select>";
        return select;
    }

    private static String getSelectTag(ResourceBundle source, String key,
            String tagName, boolean all) throws Exception {
        if (key == null) {
            throw new Exception("1");

        }
        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        int count = 0;
        String select = null;
        if (all) {
            select = "<select name=\"" + tagName
                    + "\"><option value=\"all\">选择</option>";
        } else {
            select = "<select name=\"" + tagName + "\">";
        }
        String option = "";
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            bean = getPropertysBean(value);
            option += "<option value=\"" + bean.getOrder() + "\">"
                    + bean.getContent() + "</option></br>";
            count++;
        }
        select = select + option + "</select>";
        return select;
    }

    private static String getSelectTagNull(ResourceBundle source, String key,
            String tagName, String tagValue, boolean all) throws Exception {
        if (key == null) {
            throw new Exception("1");

        }
        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        int count = 0;
        String select = null;
        if (all) {
            select = "<select name=\"" + tagName + "\"><option value=\"\">" +

            tagValue + "</option>";
        } else {
            select = "<select name=\"" + tagName + "\">";
        }
        String option = "";
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            bean = getPropertysBean(value);
            option += "<option value=\"" + bean.getOrder() + "\">"
                    + bean.getContent() + "</option></br>";
            count++;
        }
        select = select + option + "</select>";
        return select;
    }

    private static String getSelectTagNull1(ResourceBundle source, String key,
            String tagName, String tagValue, String defaultValue, boolean all)
            throws

            Exception {
        if (key == null) {
            throw new Exception("1");

        }
        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        int count = 0;
        String select = null;
        if (all) {
            select = "<select name=\"" + tagName + "\"><option value=\"\">" +

            tagValue + "</option>";
        } else {
            select = "<select name=\"" + tagName + "\">";
        }
        String option = "";
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            bean = getPropertysBean(value);
            if (bean.getOrder().equals(defaultValue)) {
                option += "<option selected value=\"" + bean.getOrder() + "\">"
                        + bean.getContent() + "</option></br>";
            } else {
                option += "<option value=\"" + bean.getOrder() + "\">"
                        + bean.getContent() + "</option></br>";
            }
            count++;
        }
        select = select + option + "</select>";
        return select;
    }

    private static String getSelectTagSelect(ResourceBundle source, String key,
            String tagName, String tagValue, int[] kes) throws Exception {
        if (key == null || kes == null || kes.length == 0) {
            throw new Exception("1");

        }
        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        int count = 0;
        String select = null;
        select = "<select name=\"" + tagName + "\"><option value=\"\">"
                + tagValue +

                "</option>";
        String option = "";
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            bean = getPropertysBean(value);
            int countTemp = Integer.parseInt(bean.getOrder());

            for (int i = 0; i < kes.length; i++) {
                if (countTemp == kes[i]) {
                    option += "<option value=\"" + bean.getOrder() + "\">"
                            + bean.getContent() + "</option></br>";
                }
            }
            count++;
        }
        select = select + option + "</select>";
        return select;
    }

    private static String getSelectTagSelect(ResourceBundle source, String key,
            String tagName, int[] kes) throws Exception {
        if (key == null || kes == null || kes.length == 0) {
            throw new Exception("1");

        }
        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        int count = 0;
        String select = null;
        select = "<select name=\"" + tagName + "\">";
        String option = "";
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            bean = getPropertysBean(value);
            int countTemp = Integer.parseInt(bean.getOrder());

            for (int i = 0; i < kes.length; i++) {
                if (countTemp == kes[i]) {
                    option += "<option value=\"" + bean.getOrder() + "\">"
                            + bean.getContent() + "</option></br>";
                }
            }
            count++;
        }
        select = select + option + "</select>";
        return select;
    }

    private static String getSelectTagSelectDefault(ResourceBundle source,
            String key, String tagName, String tagValue, int[] kes,
            int defaultId) throws Exception

    {
        if (key == null || kes == null || kes.length == 0) {
            throw new Exception("1");

        }
        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        int count = 0;
        String select = null;
        select = "<select name=\"" + tagName + "\"><option value=\"\">"
                + tagValue +

                "</option>";
        String option = "";
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            bean = getPropertysBean(value);
            int countTemp = Integer.parseInt(bean.getOrder());
            for (int i = 0; i < kes.length; i++) {
                if (countTemp == kes[i]) {
                    if (countTemp == defaultId) {
                        option += "<option  selected value=\"" +

                        bean.getOrder() + "\">" + bean.getContent()
                                + "</option></br>";
                    } else {
                        option += "<option  value=\"" + bean.getOrder()

                        + "\">" + bean.getContent() + "</option></br>";
                    }

                }
            }
            count++;
        }
        select = select + option + "</select>";
        return select;
    }

    public static Vector getVectorByKey(String key, HttpServletRequest request,
            String

            configName) {
        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }

        ResourceBundle source = null;
        ResourceBundle errorSource = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {

            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        Vector vector = null;
        try {
            vector = getVector(source, key);
        } catch (Exception ex) {
            if (ex.getMessage().equals("6")) {
                request.setAttribute("configerror", errorSource
                        .getString("error.config.6"));
            } else if (ex.getMessage().equals("1")) {
                request.setAttribute("configerror", errorSource
                        .getString("error.config.1"));
            } else if (ex.getMessage().equals("2")) {
                request.setAttribute("configerror", errorSource
                        .getString("error.config.2"));
            } else if (ex.getMessage().equals("3")) {
                request.setAttribute("configerror", errorSource
                        .getString("error.config.3"));
            }
        }
        return vector;
    }

    public static String getStringByKey(String key, HttpServletRequest request,
            String

            configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        String value = null;

        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        try {
            value = getString(source, key);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getImageByKey(String key, HttpServletRequest request,
            String

            configName) {

        if (request == null) {
            return null;
        }

        String value = null;

        Locale locale = getTrueLocale(request);
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        try {
            value = getString(source, key);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getStringByKeyDefault(String key,
            HttpServletRequest request, String

            defaultString, String configName) {
        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }

        String value = null;

        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }

        try {
            value = getString(source, key);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return defaultString;
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getStringByKeyPosition(String key,
            HttpServletRequest request, int position, String configName) {
        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }

        String value = null;

        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        try {
            value = getString(source, key, position);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getStringByKeyPosition(String key, Locale locale,
            int position, String configName) {

        if (locale == null) {
            locale = Locale.CHINESE;
        }
        ResourceBundle source = null;
        ResourceBundle errorSource = null;
        String value = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {

            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        try {
            value = getString(source, key, position);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getStringByKeyPosition(String key, Locale locale,
            String position, String configName) {
        if (locale == null) {
            locale = Locale.CHINESE;
        }
        ResourceBundle source = null;
        ResourceBundle errorSource = null;
        String value = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        try {
            value = getString(source, key, position);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getStringByKeyPosition(String key, Locale locale,
            String position, boolean status1, String configName) {
        if (locale == null) {
            locale = Locale.CHINESE;
        }
        ResourceBundle source = null;
        ResourceBundle errorSource = null;
        String value = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        try {
            value = getString(source, key, position, status1);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getStringByKeyPosition(String key,
            HttpServletRequest request, String position, boolean status1,
            String configName) {
        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }

        String value = null;

        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        try {
            value = getString(source, key, position, status1);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getStringByKeyPositionNull(String key,
            HttpServletRequest request, int position, String configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }

        ResourceBundle source = null;
        ResourceBundle errorSource = null;
        String value = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {

            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        try {
            value = getString(source, key, position);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getStringByKeyPositionNull(String key,
            HttpServletRequest request, String position, String configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }

        ResourceBundle source = null;
        ResourceBundle errorSource = null;
        String value = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {

            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        try {
            value = getString(source, key, position, false);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getStringByKeyPositionDefault(String key,
            HttpServletRequest

            request, int position, String defaultString, String configName) {
        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        String value = null;
        ResourceBundle errorSource = null;
        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        try {
            value = getString(source, key, position);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return defaultString;
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return defaultString;
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return value;
    }

    public static String getSelectTagByKey(String key,
            HttpServletRequest request, String tagName, int defaultSelected,
            boolean all, String configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        String select = null;
        try {
            select = getSelectTag(source, key, tagName, defaultSelected, all);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return select;
    }

    public static String getSelectTagByKey1(String key,
            HttpServletRequest request, String tagName, int defaultSelected,
            boolean all, String configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        String select = null;
        try {
            select = getSelectTag1(source, key, tagName, defaultSelected, all);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return select;
    }

    public static String getSelectTagByKeyAll(String key,
            HttpServletRequest request, String tagName, boolean all,
            String configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        String select = null;
        try {
            select = getSelectTag(source, key, tagName, all);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return select;
    }

    public static String getSelectTagByKeyAllNull(String key,
            HttpServletRequest request, String tagName, String tagValue,
            boolean all, String configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        String select = null;
        try {
            select = getSelectTagNull(source, key, tagName, tagValue, all);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return select;
    }

    public static String getSelectTagByKeyAllNull1(String key,
            HttpServletRequest request, String tagName, String tagValue,
            String defaultValue, boolean all, String

            configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        String select = null;
        try {
            select = getSelectTagNull1(source, key,

            tagName, tagValue, defaultValue, all);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return select;
    }

    public static String getSelectTagByKeySelect(String key,
            HttpServletRequest request, String tagName, String tagValue,
            int keys[], String configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        String select = null;
        try {
            select = getSelectTagSelect(source, key, tagName, tagValue, keys);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return select;
    }

    public static String getSelectTagByKeySelect(String key,
            HttpServletRequest request, String tagName, int keys[],
            String configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        String select = null;
        try {
            select = getSelectTagSelect(source, key, tagName, keys);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return select;
    }

    public static String getSelectTagByKeySelectDefault(String key,
            HttpServletRequest

            request, String tagName, String tagValue, int keys[],
            int defaultId, String

            configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        String select = null;
        try {
            select = getSelectTagSelectDefault(source, key,

            tagName, tagValue, keys, defaultId);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return select;
    }

    public static String getSelectTagByKeyAll(String key,
            HttpServletRequest request, String tagName, String tagValue,
            String defaultValue, String configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        String select = null;
        try {
            select = getSelectTag(source, key, tagName, tagValue, defaultValue);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return select;
    }

    public static String getSelectTagByKeyAll(String key,
            HttpServletRequest request, String tagName, String defaultValue,
            String configName) {

        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") || locale
                .getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle
                        .getBundle(commonalityConfigPath, locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,
                    locale);
        }
        String select = null;
        try {
            select = getSelectTag(source, key, tagName, defaultValue);
        } catch (Exception e) {
            if (e.getMessage().equals("6")) {
                return errorSource.getString("error.config.6");
            } else if (e.getMessage().equals("1")) {
                return errorSource.getString("error.config.1");
            } else if (e.getMessage().equals("2")) {
                return errorSource.getString("error.config.2");
            } else if (e.getMessage().equals("3")) {
                return errorSource.getString("error.config.3");
            } else if (e.getMessage().equals("5")) {
                return errorSource.getString("error.config.5");
            } else if (e.getMessage().equals("4")) {
                return errorSource.getString("error.config.4");
            }
        }
        return select;
    }

    private static String getSelectTag(ResourceBundle source, String key,
            String tagName, String defaultValue) throws Exception {
        if (key == null) {
            throw new Exception("1");

        }
        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }

        String select = null;

        select = "<select name=\"" + tagName + "\">";

        String option = "";
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            bean = getPropertysBean(value);
            if (defaultValue != null) {
                if (bean.getOrder().equals(defaultValue)) {
                    option += "<option value=\"" + bean.getOrder()
                            + "\" selected >" + bean.getContent()
                            + "</option></br>";

                } else {
                    option += "<option value=\"" + bean.getOrder() + "\">"
                            + bean.getContent() + "</option></br>";

                }
            } else {
                option += "<option value=\"" + bean.getOrder() + "\">"
                        + bean.getContent() + "</option></br>";
            }
        }
        select = select + option + "</select>";
        return select;
    }

    private static String getSelectTag(ResourceBundle source, String key,
            String tagName, String tagValue, String defaultValue)
            throws Exception {
        if (key == null) {
            throw new Exception("1");

        }
        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }

        String select = null;

        select = "<select name=\"" + tagName + "\"><option value=\"\">"
                + tagValue +

                "</option>";

        String option = "";
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            bean = getPropertysBean(value);
            if (defaultValue != null) {
                if (bean.getOrder().equals(defaultValue)) {
                    option += "<option value=\"" + bean.getOrder()
                            + "\" selected >" + bean.getContent()
                            + "</option></br>";

                } else {
                    option += "<option value=\"" + bean.getOrder() + "\">"
                            + bean.getContent() + "</option></br>";

                }
            } else {
                option += "<option value=\"" + bean.getOrder() + "\">"
                        + bean.getContent() + "</option></br>";
            }
        }
        select = select + option + "</select>";
        return select;
    }

    private static int getIntByStringKey(ResourceBundle source, String key,
            String

            positionValue) throws Exception {
        if (key == null) {
            throw new Exception("1");

        }

        String value = null;
        try {
            value = source.getString(key);
        } catch (Exception e) {
            throw new Exception("2");

        }
        PropertysBean bean = null;
        StringTokenizer token = new StringTokenizer(value, ",");
        if (token.countTokens() == 1) {
            throw new Exception("3"); 

        }
        int count = 0;
        String valueTemp = null;
        boolean status = true;
        while (token.hasMoreTokens()) {
            value = token.nextToken();
            count = Integer.parseInt(getPropertysBean(value).getOrder());
            value = getPropertysBean(value).getContent();
            if (value.equals(positionValue)) {
                status = false;
                break;
            }
        }
        if (status) {
            throw new Exception("5");//位置大于总数
        }
        return count;
    }

    public static int getStringByIntKey(String key, HttpServletRequest request,
            String

            value, String configName) {
        Locale locale = null;
        if (request == null) {
            locale = Locale.CHINESE;
        } else {
            locale = getTrueLocale(request);
        }
        int position = 0;
        ResourceBundle errorSource = null;

        ResourceBundle source = null;
        if (!(locale.getLanguage().toLowerCase().equals("zh")
                || locale.getLanguage().toLowerCase().equals("tw") ||

        locale.getLanguage().toLowerCase().equals("en"))) {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, Locale.ENGLISH);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                Locale.ENGLISH);

            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            Locale.ENGLISH);

        } else {
            if (configName != null) {
                source = ResourceBundle.getBundle(configName, locale);
            } else {
                source = ResourceBundle.getBundle(commonalityConfigPath,

                locale);
            }
            errorSource = ResourceBundle.getBundle(commonalityConfigPath,

            locale);
        }

        try {
            position = getIntByStringKey(source, key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return position;
    }
    
       
    
}
