package io.ride.main;

import io.ride.main.dialog.CustomizeDialog;
import io.ride.main.dialog.RankingListDialog;
import io.ride.util.SwingConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-24
 * Time: 下午6:39
 * 主窗体
 */
public class MineSweep extends JFrame {
    private JMenuItem newGameItem, simpleNewGameItem, middleNewGameItem, hardNewGameItem, customizeNewGameItem, exitGameItem;
    private JMenuItem simpleRankingItem, middleRankingItem, hardRankingItem;
    private JMenuItem helpItem, aboutItem;

    private CardLayout cardLayout = new CardLayout();

    private Game game;

    MineSweep() {
        setLayout(cardLayout);
        setBackground(Color.WHITE);
        setResizable(false);        // 不可改变大小
        initMenuBar();
        addListener();
        beginGame(Difficulty.SIMPLE);
    }

    /**
     * 初始化菜单栏
     */
    private void initMenuBar() {
        // 菜单条
        JMenuBar menuBar = new JMenuBar();

        // 菜单
        JMenu gameMenu = new JMenu("游戏(G)");
        JMenu rankingMenu = new JMenu("排名(R)");
        JMenu helpMenu = new JMenu("帮助(H)");
        // 设置菜单快捷键ALT+key
        gameMenu.setMnemonic('G');
        rankingMenu.setMnemonic('R');
        helpMenu.setMnemonic('H');
        // 设置字体
        gameMenu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        rankingMenu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        helpMenu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        // 游戏菜单列表
        newGameItem = new JMenuItem("新游戏");
        simpleNewGameItem = new JMenuItem("初级");
        middleNewGameItem = new JMenuItem("中级");
        hardNewGameItem = new JMenuItem("高级");
        customizeNewGameItem = new JMenuItem("自定义");
        exitGameItem = new JMenuItem("退出");
        // 将游戏列表添加到游戏菜单
        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        gameMenu.add(simpleNewGameItem);
        gameMenu.add(middleNewGameItem);
        gameMenu.add(hardNewGameItem);
        gameMenu.add(customizeNewGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitGameItem);
        // 设置快捷键
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        simpleNewGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        middleNewGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        hardNewGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        customizeNewGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        exitGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        // 设置字体
        newGameItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        simpleNewGameItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        middleNewGameItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        hardNewGameItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        customizeNewGameItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        exitGameItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        // 游戏排行菜单列表
        simpleRankingItem = new JMenuItem("初级排行榜");
        middleRankingItem = new JMenuItem("中级排行榜");
        hardRankingItem = new JMenuItem("高级排行榜");
        // 加入菜单
        rankingMenu.add(simpleRankingItem);
        rankingMenu.add(middleRankingItem);
        rankingMenu.add(hardRankingItem);
        // 设置快捷键
        simpleRankingItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        middleRankingItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        hardRankingItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        // 设置字体
        simpleRankingItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        middleRankingItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        hardRankingItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        // 游戏帮助菜单列表
        helpItem = new JMenuItem("玩法");
        aboutItem = new JMenuItem("关于");
        // 加入菜单
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);
        // 设置快捷键
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        // 设置字体
        helpItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        aboutItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        menuBar.add(gameMenu);
        menuBar.add(rankingMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    /**
     * 为菜单栏添加事件监听, 所有监听事件都采用了lambda, 需要java 1.8的支持
     */
    private void addListener() {
        // 游戏菜单
        newGameItem.addActionListener(e -> new Thread(() -> {
            beginGame(game.getDiff());
            SwingConsole.setPositionCenter(this);
            repaint();
        }).start());
        simpleNewGameItem.addActionListener(e -> new Thread(() -> {
            beginGame(Difficulty.SIMPLE);
            SwingConsole.setPositionCenter(this);
            repaint();
        }).start());
        middleNewGameItem.addActionListener(e -> new Thread(() -> {
            beginGame(Difficulty.MIDDLE);
            SwingConsole.setPositionCenter(this);
            repaint();
        }).start());
        hardNewGameItem.addActionListener(e -> new Thread(() -> {
            beginGame(Difficulty.HARD);
            SwingConsole.setPositionCenter(this);
            repaint();
        }).start());
        customizeNewGameItem.addActionListener(e -> {
            CustomizeDialog customizeDialog = new CustomizeDialog(this, "自定义游戏");
            customizeDialog.setVisible(true);
        });
        exitGameItem.addActionListener(e -> System.exit(0));
        // 游戏排行菜单
        simpleRankingItem.addActionListener(e -> new RankingListDialog(this, Difficulty.SIMPLE));
        middleRankingItem.addActionListener(e -> new RankingListDialog(this, Difficulty.MIDDLE));
        hardRankingItem.addActionListener(e -> new RankingListDialog(this, Difficulty.HARD));
        // 帮助菜单
        helpItem.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "小技巧:\n    1.第一次点击一定不是雷\n    2.左键点开,右键标记\n    " +
                        "3.数字n代表附近上下左右及斜角有n个雷\n    4.在数字上双击清算",
                "帮助", JOptionPane.INFORMATION_MESSAGE));
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "制作:\n    ride(董凌浩)",
                "关于", JOptionPane.INFORMATION_MESSAGE));

    }

    /**
     * 根据难度启动游戏
     *
     * @param diff 游戏难度, 可以是自定义难度
     */
    public void beginGame(Difficulty diff) {
        if (game != null) {
            remove(game);
        }
        game = new Game(diff);
        add(game);
        setSize(game.getDimension());
        cardLayout.next(getContentPane());
    }

}
