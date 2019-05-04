package com.safecode.cyberzone.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期操作辅助类
 */
public final class DateUtil {
	private DateUtil() {
	}
	/** 日期格式 **/
    public interface DATE_PATTERN {
        String HHMMSS = "HHmmss";
        String HH_MM_SS = "HH:mm:ss";
        String YYYYMMDD = "yyyyMMdd";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    }

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static final String format(Object date) {
		return format(date, DATE_PATTERN.YYYY_MM_DD);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static final String format(Object date, String pattern) {
		if (date == null) {
			return null;
		}
		if (pattern == null) {
			return format(date);
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 获取日期
	 * 
	 * @return
	 */
	public static final String getDate() {
		return format(new Date());
	}

	/**
	 * 获取日期时间
	 * 
	 * @return
	 */
	public static final String getDateTime() {
		return format(new Date(), DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static final String getDateTime(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * 日期计算
	 * 
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static final Date addDate(Date date, int field, int amount) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 字符串转换为日期:不支持yyM[M]d[d]格式
	 * 
	 * @param date
	 * @return
	 */
	public static final Date stringToDate(String date) {
		if (date == null) {
			return null;
		}
		String separator = String.valueOf(date.charAt(4));
		String pattern = "yyyyMMdd";
		if (!separator.matches("\\d*")) {
			pattern = "yyyy" + separator + "MM" + separator + "dd";
			if (date.length() < 10) {
				pattern = "yyyy" + separator + "M" + separator + "d";
			}
		} else if (date.length() < 8) {
			pattern = "yyyyMd";
		}
		pattern += " HH:mm:ss.SSS";
		pattern = pattern.substring(0, Math.min(pattern.length(), date.length()));
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 间隔天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getDayBetween(Date startDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);

		long n = end.getTimeInMillis() - start.getTimeInMillis();
		return (int) (n / (60 * 60 * 24 * 1000l));
	}

	/**
	 * 间隔月
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getMonthBetween(Date startDate, Date endDate) {
		if (startDate == null || endDate == null || !startDate.before(endDate)) {
			return null;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int year1 = start.get(Calendar.YEAR);
		int year2 = end.get(Calendar.YEAR);
		int month1 = start.get(Calendar.MONTH);
		int month2 = end.get(Calendar.MONTH);
		int n = (year2 - year1) * 12;
		n = n + month2 - month1;
		return n;
	}

	/**
	 * 间隔月，多一天就多算一个月
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getMonthBetweenWithDay(Date startDate, Date endDate) {
		if (startDate == null || endDate == null || !startDate.before(endDate)) {
			return null;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int year1 = start.get(Calendar.YEAR);
		int year2 = end.get(Calendar.YEAR);
		int month1 = start.get(Calendar.MONTH);
		int month2 = end.get(Calendar.MONTH);
		int n = (year2 - year1) * 12;
		n = n + month2 - month1;
		int day1 = start.get(Calendar.DAY_OF_MONTH);
		int day2 = end.get(Calendar.DAY_OF_MONTH);
		if (day1 <= day2) {
			n++;
		}
		return n;
	}
	
	/**
	 * @Title: nodeCalculation 
	 * @Description: 根据条件返回起始时间，时间时分秒为凌晨00:00:00
     * @param timeType  时间节点类型：1：当天，2：本周，3：本月，4：本季度，5：本年，6：其它
     * @param timeNum  此参数配合时间节点类型为（6： 其他） 得情况下使用,时间节点类型不等于6时则不起作用，此参数为天数由当前日期向前推
	 * @return
	 * @author snial
	 * @date 2016年11月4日 上午10:43:19
	 */
    public static final Date nodeCalculation(int timeType, int timeNum) {
        Calendar cal =Calendar.getInstance();
        if (timeType == 1) {    //返回当天凌晨
            cal.setTime(new Date());
        } else if (timeType == 2) { //返回本周一凌晨
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
        } else if (timeType == 3) { //返回本月一号凌晨
            cal.set(Calendar.DAY_OF_MONTH, 1);
        } else if (timeType == 4) { //返回本季度起始时间一号凌晨
            cal.setTime(getCurrentQuarterStartTime());
        } else if (timeType == 5) { //返回本年起始时间凌晨
            cal.set(Calendar.MONTH, 0); 
            cal.set(Calendar.DATE, 1);
        } else if (timeType == 6) { //根据天数timeNum从今天向前推
            cal.add(Calendar.DATE, -timeNum);
        }
        return weeHours(cal.getTime(), 0);
    }
    
    /**
     * @Title: weeHours 
     * @Description: 获取日期凌晨
     * @param date 时间
     * @param flag 0 返回yyyy-MM-dd 00:00:00日期          1 返回yyyy-MM-dd 23:59:59日期
     * @return
     * @author snial
     * @date 2016年11月4日 上午10:56:30
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);
         
        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
        }
        return cal.getTime();
    }
    
    /**
     * @Title: getCurrentQuarterStartTime 
     * @Description: 当前季度的开始时间，例如2012-01-1 00:00:00 
     * @return
     * @author snial
     * @date 2016年11月4日 上午10:57:12
     */
    public static Date getCurrentQuarterStartTime() { 
        Calendar c = Calendar.getInstance(); 
        int currentMonth = c.get(Calendar.MONTH) + 1; 
        Date now = null; 
        try { 
            if (currentMonth >= 1 && currentMonth <= 3) 
                c.set(Calendar.MONTH, 0); 
            else if (currentMonth >= 4 && currentMonth <= 6) 
                c.set(Calendar.MONTH, 3); 
            else if (currentMonth >= 7 && currentMonth <= 9) 
                c.set(Calendar.MONTH, 4); 
            else if (currentMonth >= 10 && currentMonth <= 12) 
                c.set(Calendar.MONTH, 9); 
            c.set(Calendar.DATE, 1); 
            now = weeHours(c.getTime(), 0);
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return now; 
    } 

    /** 
     * 根据开始时间和结束时间返回时间段内的时间集合 
     * 
     * @param beginDate 
     * @param endDate 
     * @param formatPattern 格式 例如：yyyy-mm-dd
     * @return List 
     */  
    public static List<String> getDatesBetweenTwoDate(Date beginDate, Date endDate, String formatPattern) {  
        List<String> lDate = new ArrayList<String>();  
        lDate.add(format(beginDate, formatPattern));// 把开始时间加入集合  
        Calendar cal = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间  
        cal.setTime(beginDate);  
        boolean bContinue = true;  
        while (bContinue) {  
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            cal.add(Calendar.DAY_OF_MONTH, 1);  
            // 测试此日期是否在指定日期之后  
            if (endDate.after(cal.getTime())) {  
                lDate.add(format(cal.getTime(), formatPattern));  
            } else {  
                break;  
            }  
        }  
//        lDate.add(format(endDate, formatPattern));// 把结束时间加入集合  
        return lDate;  
    }  
    
    /** 
     * 获取最近12个月，经常用于统计图表的X轴 
     * formatPattern 返回时间格式
     */  
    public static List<String> getLast12Months(String formatPattern){  
          
        List<String> list = new ArrayList<String>();  
          
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-13); 
        for(int i=13; i>0; i--){  
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //逐次往前推1个月  
            list.add(format(cal.getTime(), formatPattern));  
            
        }  
          
        return list;  
    }  

}
