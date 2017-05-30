package io.ride.util;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-24
 * Time: 下午6:27
 * 格式化时间类
 */
public class TimeFormat {
    private static final int TIME_LIMIT = 60 * 60;
    /**
     * 将传入的游戏时间转换为分秒制, 如果游戏进行时间超一个小时, 则一直显示为59:59
     *
     * @param seconds 游戏进行的时间
     * @return 将传入的秒数转换为分秒制
     */
    public static String getFormatTime(int seconds) {
        if (seconds >= TIME_LIMIT) {
            return "59:59";
        }
        int second = seconds % 60;
        int minute = seconds / 60;
        return String.format("%02d:%02d", minute, second);
    }

}
