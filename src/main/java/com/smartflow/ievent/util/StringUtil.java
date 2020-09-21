package com.smartflow.ievent.util;


import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.spi.CalendarDataProvider;
import java.util.stream.Collectors;

public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean IsNotBlank(String str) {
        if (str != null && !"".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改日期格式
     *
     * @param datestr
     * @return
     * @throws ParseException
     */
    public static Date formatUTCDateTime(String datestr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");//注意格式化的表达式
        if (IsNotBlank(datestr)) {
            datestr = datestr.replace("Z", " UTC");//注意是空格+UTC
            Date date = sdf.parse(datestr);
            return date;
        } else {
            return null;
        }
    }

    /**
     * 将String类型的日期转成Date类型的日期
     * @param datestr
     * @return
     * @throws ParseException
     */
    public static Date formatDateTime(String datestr) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(IsNotBlank(datestr)) {
            Date date = sdf.parse(datestr);
            return date;
        }else{
            return null;
        }
    }

    /**
     * 修改日期格式
     *
     * @param datestr
     * @return
     * @throws ParseException
     */
    public static Date formatDate(String datestr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//注意格式化的表达式
        if (IsNotBlank(datestr)) {
            Date date = sdf.parse(datestr);
            return date;
        } else {
            return null;
        }
    }

    /**
     * 根据开始时间获取结束时间（结束时间加1天减1秒）
     * @param endDateTime
     * @return
     */
    public static Date getEndDateTime(Date endDateTime){
        if(endDateTime != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDateTime);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.SECOND, -1);
            endDateTime = calendar.getTime();
        }
        return endDateTime;
    }


    public static String parseEndDateToEndDateTime(String dateStr) throws ParseException {
        SimpleDateFormat dateSDF = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateTimeSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (IsNotBlank(dateStr)) {
            Date date = dateSDF.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date dateTime = calendar.getTime();
            return dateTimeSDF.format(dateTime);
        } else {
            return null;
        }
    }

    public static Map<String,Long> getDateApart(Date startDate, Date endDate){
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff;
        //获得两个时间的毫秒时间差异
        diff = endDate.getTime() - startDate.getTime();
        long day = diff / nd;//计算差多少天
        long hour = diff % nd / nh;//计算差多少小时
        long min = diff % nd % nh / nm;//计算差多少分钟
        long sec = diff % nd % nh % nm / ns;//计算差多少秒
        // 输出结果
        //System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒。");
        long TotalHours = day * 24 + hour;
        Map<String,Long> map = new HashMap<>();
        map.put("hour", TotalHours);
        map.put("minute",min);
        return map;
    }

    /**
     * 计算两个时间相差X小时X分钟
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getHourMinutesApart(Date startDate, Date endDate) {
        Map<String,Long> map = getDateApart(startDate,endDate);
        return map.get("hour") + "h" + map.get("minute") + "m";
    }

    /**
     * 根据已流逝时间显示事件颜色
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getEventColor(Date startDate, Date endDate){
        Map<String, Long> map = getDateApart(startDate, endDate);
        long passedTime = map.get("hour") * 24 + map.get("minute");
        String eventColor = null;
        if(passedTime > 30 && passedTime <= 60){
            eventColor = "blue";
        }else if(passedTime > 60 && passedTime <= 120){
            eventColor = "yellow";
        }else if(passedTime > 120 && passedTime <= 180){
            eventColor = "orange";
        }else if(passedTime > 180){
            eventColor = "red";
        }
        return eventColor;
    }

    /**
     * 计算两个时间相差几小时
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public static BigDecimal getHourApart(Date startDate, Date endDate) {
//		long nd =  1000 * 24 * 60 * 60;//每天毫秒数
//		long nh = 1000 * 60 * 60;//每小时毫秒数
        long diff = endDate.getTime() - startDate.getTime();
        //long hour = diff % nd / nh; // 计算差多少小时
        double hour = diff / 1000 / 60 / 60.0;//得到小数类型的小时
        DecimalFormat df = new DecimalFormat("##.#");
        BigDecimal dffHour = new BigDecimal(df.format(hour));
        return dffHour;
    }

    /**
     * 获取状态间隔时间
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getStateApartMinites(Date startDate, Date endDate){
        long diff = endDate.getTime() - startDate.getTime();
        long minites = diff / 1000 /60; // 计算差多少分钟
        return Integer.parseInt(String.valueOf(minites));
    }

    public static String formatDateFromYMDToYMDHMS(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            System.out.println(calendar.getTime());
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BigDecimal decimalFormatMinuteToHour(double workLengthMin) {
        DecimalFormat df = new DecimalFormat("##.#");
        BigDecimal dffHour = new BigDecimal(df.format(workLengthMin));
        return dffHour;
    }

    public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
//		BigDecimal  hour = getHourApart(sdf.parse("2019-07-05 09:00:00"), sdf.parse("2019-07-05 10:40:00"));
//		System.out.println(hour);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2019);
        c.set(Calendar.MONTH, 9);
        c.set(Calendar.DAY_OF_MONTH, 25);
        c.set(Calendar.HOUR, 12);
        c.set(Calendar.MINUTE, 20);
        c.set(Calendar.SECOND, 00);
        Date startDate = c.getTime();
        c.add(Calendar.HOUR, 1);
        c.set(Calendar.MINUTE, 30);
        Date endDate = c.getTime();
        getHourMinutesApart(startDate, endDate);
    }

    public static class User {
        private Integer Id;
        private String UserName;
        private String Account;

        public Integer getId() {
            return Id;
        }

        public void setId(Integer id) {
            Id = id;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getAccount() {
            return Account;
        }

        public void setAccount(String account) {
            Account = account;
        }

        @Override
        public String toString() {
            return "User [Id=" + Id + ", UserName=" + UserName + ", Account=" + Account + "]";
        }


    }

    public static void addUserList(List<User> userList2) {
        User user1 = new User();//删除的
        user1.setId(1);
        user1.setUserName("张三");
        user1.setAccount("aaa");
        User user2 = new User();//修改的
        user2.setId(2);
        user2.setUserName("李四");
        user2.setAccount("bbb");
        User user3 = new User();
        user3.setId(3);
        user3.setUserName("王五");
        user3.setAccount("ccc");
        List<User> userList1 = new ArrayList<>();
        userList1.add(user1);
        userList1.add(user2);
        userList1.add(user3);
        List<User> contain = new ArrayList<>();
        List<User> update = new ArrayList<>();
        List<User> add = new ArrayList<>();
        List<User> del = new ArrayList<>();
        for (User u2 : userList2) {
            for (User u1 : userList1) {
                if (u1.getId() == u2.getId()) {
                    System.out.println("包含");
                    contain.add(u1);
                    contain.add(u2);
                    update.add(u2);
                    break;
                }
            }
        }
        userList1.removeAll(contain);
        userList2.removeAll(contain);
        del.addAll(userList1);
        add.addAll(userList2);
        System.out.println("删除的" + del);
        System.out.println("新增的" + add);
        System.out.println("修改的" + update);
        userList1.removeAll(del);
        userList1.addAll(update);
        userList1.addAll(add);
        System.out.println(userList1);
    }

//	public static void main(String[] args) throws ParseException {
    /**
     User user4 = new User();
     user4.setId(2);
     user4.setUserName("李四02");
     user4.setAccount("bbb02");
     User user5 = new User();
     user5.setId(3);
     user5.setUserName("王五");
     user5.setAccount("ccc");
     User user6 = new User();//新增的
     user6.setId(4);
     user6.setUserName("赵六");
     user6.setAccount("ddd");
     List<User> userList2 = new ArrayList<>();
     userList2.add(user4);
     userList2.add(user5);
     userList2.add(user6);

     addUserList(userList2);
     */
//		System.out.println(parseEndDateToEndDateTime("2019-08-13"));

//	}
}
