package io.ride.util;

import io.ride.main.Difficulty;

import java.io.*;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-27
 * Time: 下午3:56
 * 读取, 保存和清空记录
 */
public class RecordUtil {


    /**
     * 返回不同标准难度的记录文件路径
     * @param diff  标准难度
     * @return  不同标准难度对应的难度
     */
    public static String getFilename(Difficulty diff) {
        if (diff == Difficulty.SIMPLE) {
            return "src/res/Record/simpleRecord.txt";
        }
        if (diff == Difficulty.MIDDLE) {
            return "src/res/Record/middleRecord.txt";
        }
        if (diff == Difficulty.HARD) {
            return "src/res/Record/hardRecord.txt";
        }

        return null;

    }

    /**
     * 读取该标准难度的内容
     * @param filename  要要读取的文件
     * @return  读取后的结果
     */
    public static TreeSet<Record> readRecords(String filename) {
        TreeSet<Record> records = new TreeSet<>();
        BufferedReader reader = null;
        String name;
        String date;
        int timeCost;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String string;
            while ((string = reader.readLine()) != null) {
                Scanner scanner = new Scanner(string);
                date = scanner.next();
                name = scanner.next();
                timeCost = scanner.nextInt();
                records.add(new Record(name, timeCost, date));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return records;
    }

    /**
     * 将该记录写入文件中
     * @param filename  文件名
     * @param timeCost  花费时间
     * @param name      玩家昵称
     */
    public static void writeRecord(String filename, int timeCost, String name) {
        TreeSet<Record> records = readRecords(filename);
        records.add(new Record(name, timeCost));
        int len = records.size() > 10 ? 10 : records.size();
        int cnt = 0;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            for (Record r : records) {
                writer.write(r.toString());
                cnt++;
                if (cnt == len) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 清空记录
     * @param filename  文件名
     */
    public static void clearRecord(String filename) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
