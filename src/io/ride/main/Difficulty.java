package io.ride.main;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-24
 * Time: 下午6:26
 * 难度类, 定义了三个三个标准难度, 也可以自定义难度
 */
public class Difficulty {
    int x;
    int y;
    int mineNum;

    public static final Difficulty SIMPLE = new Difficulty(8, 8, 9);        // 简单难度
    public static final Difficulty MIDDLE = new Difficulty(16, 16, 40);     // 中等难度
    public static final Difficulty HARD = new Difficulty(16, 40, 99);       // 困难难度

    /**
     * 难度, 保证各参数合法
     *
     * @param x       行
     * @param y       列
     * @param mineNum 雷数
     */
    public Difficulty(int x, int y, int mineNum) {
        this.x = x;
        this.y = y;
        this.mineNum = mineNum;
    }

    /**
     * 判断是否标准难度还是自定义难度
     * @param diff  难度
     * @return  标准难度返回true, 自定义难度返回false
     */
    public static boolean isNotCustomizeDiff(Difficulty diff) {
        return diff == SIMPLE || diff == MIDDLE || diff == HARD;
    }
}
