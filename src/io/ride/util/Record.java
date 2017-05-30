package io.ride.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-27
 * Time: 下午4:22
 * 记录类
 */
public class Record implements Comparable {
    private String name;        // 玩家昵称
    private int timeCost;       // 花费时间
    private String date;        // 记录日期

    /**
     * @param name     玩家昵称
     * @param timeCost 花费时间
     */
    Record(String name, int timeCost) {
        this.name = name;
        this.timeCost = timeCost;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNow = new Date();
        this.date = format.format(dateNow);
    }

    /**
     * @param name     玩家昵称
     * @param timeCost 花费时间
     * @param date     记录日期
     */
    Record(String name, int timeCost, String date) {
        this.name = name;
        this.timeCost = timeCost;
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%-12s%-20s%s 秒 \n", date, name, timeCost);
    }

    @Override
    public int compareTo(Object o) {
        return timeCost - ((Record) o).getTimeCost();
    }

    private int getTimeCost() {
        return timeCost;
    }

}
