/**
 *
 */
package org.poem.hadoop.common;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yi.hu
 *
 */
public class DateTimeUtil {

    private static Calendar calendar = Calendar.getInstance();

    /**
     * 将一个日期对象格式化为一个字符串。格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     *            要处理的日期
     * @return 处理后的日期（字符串）
     */
    private static String parseDateToString(Date date) {
        if (null == date) {
            return "-1";
        }
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 将一个日期对象格式化为一个字符串。字符串格式由调用方法指定
     *
     * @param date
     *            要处理的日期
     * @param pattern
     *            日期格式的模式字符串
     * @return 处理后的日期（字符串）
     *
     *
     */
    public static String parseDateToString(final Date date, final String pattern) {
        if (null == date) {
            return "-1";
        }
        if (StringUtils.isEmpty(pattern)) {
            return parseDateToString(date);
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 将一个字符串解析为一个日期对象。字符串格式为：yyyy-MM-dd
     *
     * @param dateStr
     *            要解析的日期字符串
     * @return 日期对象
     *
     */
    private static Date parseStringToDate(final String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将一个字符串解析为一个日期对象。字符串格式由调用方法指定
     *
     * @param dateStr
     *            要解析的日期字符串
     * @param pattern
     *            日期格式的模式字符串
     * @return 日期对象
     *
     *         不能被解析的日期格式的模式字符串
     */
    public static Date parseStringToDate(final String dateStr,
                                         final String pattern) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        if (StringUtils.isEmpty(pattern)) {
            return parseStringToDate(dateStr);
        }
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 取指定日期的下一天
     *
     * @param dateStr
     *            指定的日期
     * @return 指定日期的后一天
     *
     */
    public static String getNextDay(final String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return "-1";
        }
        if (!isDateStr(dateStr, "yyyy-MM-dd")) {
            return "-1";
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 取指定日期的下一天
     *
     * @param date
     *            指定的日期
     * @return 指定日期的后一天
     *
     */
    public static Date getNextDay(final Date date) {
        if (date == null) {
            return new Date();
        }
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

    /**
     * 取指定日期的前一天
     *
     * @param dateStr
     *            指定的日期
     * @return 指定日期的前一天
     *
     */
    public static String getBeforeDay(final String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return "-1";
        }
        if (!isDateStr(dateStr, "yyyy-MM-dd")) {
            return "-1";
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }


    /**
     * 根据生日计算年龄（到当前日期）
     *
     * @param birthday
     *            出生日期
     * @return 年龄
     */
    public static int getAge(final Date birthday) {
        if (null == birthday) {
            return -1;
        }
        calendar = Calendar.getInstance();
        if (calendar.before(birthday)) {
            // 生日在当前如期的后面
            return -1;
        }
        // 获得当前的时间
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(birthday);
        // 获得生日的时间
        int birthdayYear = calendar.get(Calendar.YEAR);
        int birthdayMonth = calendar.get(Calendar.MONTH) + 1;
        int birthdayDay = calendar.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - birthdayYear;
        if (currentMonth <= birthdayMonth) {
            if (currentMonth == birthdayMonth) {
                if (currentDay < birthdayDay) {
                    age--;
                }
            }
        }
        return age;
    }

    /**
     * 根据生日计算年龄（到指定日期）
     *
     * @param birthday
     * @param date
     * @return
     */
    public static int getAge(final Date birthday, final Date date) {
        calendar = Calendar.getInstance();
        if (null == birthday || null == date) {
            return -1;
        }
        if (date.before(birthday)) {
            return -1;
        }
        // 获得指定时间
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // 获得生日时间
        calendar.setTime(birthday);
        int birthdayYear = calendar.get(Calendar.YEAR);
        int birthdayMonth = calendar.get(Calendar.MONTH) + 1;
        int birthdayDay = calendar.get(Calendar.DAY_OF_MONTH);
        int age = year - birthdayYear;
        if (month <= birthdayMonth) {
            if (month == birthdayMonth) {
                if (day < birthdayDay) {
                    age--;
                }
            }
        }
        return age;
    }

    /**
     * 判断指定字符串是否符合日期的格式
     *
     * @param dateStr
     * @return DateTimeUtil.isDateStr(" 1990 - 01 - 32 00 : 00 : 00 ") = false
     *         DateTimeUtil.isDateStr("1990-01 00:00") = false
     *         DateTimeUtil.isDateStr("1990-1-1 00:00:00") = true
     *         DateTimeUtil.isDateStr("1990-01-3") = true
     *         DateTimeUtil.isDateStr("1990-21-31 00:00:00") = false
     *         DateTimeUtil.isDateStr("1990年01月3日 00时00分00秒") = false
     */
    private static boolean isDateStr(final String dateStr, final String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setLenient(false);
        try {
            format.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private static String addDate(Date date, int unit, int amount, String format) {
        return new SimpleDateFormat(format).format(_addDate(date, unit, amount).getTime());
    }

    private static String addDate(Date date, int unit, int amount) {
        return addDate(date, unit, amount, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date _addDate(Date date, int unit, int amount) {
        if (date == null) {
            date = new Date();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit, amount);
        return calendar.getTime();
    }

    private static Boolean isDateString(String dateString) {
        if (dateString == null) {
            return false;
        }
        /**
         * 判断日期格式和范围
         */
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(dateString);

        boolean dateType = mat.matches();

        return dateType;

/*        if (dateString.length()==10){
            dateString = dateString.replace("/","-");
            return isDateStr(dateString,"yyyy-MM-dd");
        }
        return false ;*/
    }

    private static Boolean isDateTimeString(String dateString) {
        if (dateString == null) return false;

        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8])))))) " + "([01]?[0-9]|2[0-3]):([0-5]?[0-9])(:([0-5]?[0-9]))?";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(dateString);

        boolean dateType = mat.matches();

        return dateType;

//        if (dateString.length()==14||dateString.length()==15||dateString.length()==16){
//            dateString = dateString.replace("/","-");
//            return isDateStr(dateString,"yyyy-MM-dd HH:mm");
//        }
//        if (dateString.length()==17||dateString.length()==18||dateString.length()==19){
//            dateString = dateString.replace("/","-");
//            return isDateStr(dateString,"yyyy-MM-dd HH:mm:ss ");
//        }
//        return false ;
    }

    public static String getDeadline(Date date) {
        return addDate(date, Calendar.HOUR, +2);
    }

    static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}
