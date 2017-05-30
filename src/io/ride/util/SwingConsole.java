package io.ride.util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-24
 * Time: 下午6:45
 * 辅助显示类
 */
public class SwingConsole {
    /**
     * 统一的运行方法
     *  @param frame frame
     *
     */
    public static void run(JFrame frame) {
        frame.setTitle("扫雷");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        setPositionCenter(frame);
    }

    public static void setPositionCenter(JFrame frame) {
        // 设置窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }
}

