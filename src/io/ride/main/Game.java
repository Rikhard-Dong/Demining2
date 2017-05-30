package io.ride.main;

import io.ride.main.dialog.VictoryDialog;
import io.ride.util.PlaySoundsUtil;
import io.ride.util.TimeFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-24
 * Time: 下午8:31
 */
class Game extends JPanel {
    private static final int BLOCK_SIZE = 25;       // 默认块的大小

    private Difficulty diff = null;                 // 难度
    private int timeCost = 0;                       // 花费时间
    private int found = 0;                          // 当前找到的雷数
    private boolean isClick = false;                // 判断是否首次点击, 如果首次点击到雷, 做特殊处理
    private boolean isGameOver = false;             // 判断游戏是否结束, 用于退出递归

    private JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JLabel foundAndSumLabel;
    private JLabel timeCostLabel;

    private Cell[][] cells;

    Difficulty getDiff() {
        return diff;
    }

    // 记录时间
    private Timer timer = new Timer(1000, e -> {
        timeCost++;

        setTimeCostLabel();
        repaint();
    });

    private Timer paintTimer = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            setVisible(true);
            paintTimer.stop();
        }
    });

    // 游戏图标
    private static Icon[] icons;

    static {
        // 加载资源图标
        icons = new Icon[15];
        for (int i = 0; i < icons.length; i++) {
            // icons[i] = new ImageIcon(iconUrls[i]);
            icons[i] = new ImageIcon("src/res/icon" + i + ".png");
            ((ImageIcon) icons[i]).setImage(((ImageIcon) icons[i]).getImage().getScaledInstance(25, 25,
                    Image.SCALE_DEFAULT));
        }
    }

    Game(Difficulty diff) {
        this.diff = diff;
        setLayout(new BorderLayout());
        timeCost = 0;
        found = 0;
        isClick = false;
        init();
    }

    /**
     * 初始化类
     */
    private void init() {
        repaint();
        // 创建组件
        centerPanel.setSize(diff.y * 25, diff.x * 25);
        foundAndSumLabel = new JLabel();
        timeCostLabel = new JLabel();
        JButton restartButton = new JButton();
        // 设置标签文字
        setFoundAndSumLabel();
        setTimeCostLabel();
        // 设置重新开始按钮
        restartButton.setPreferredSize(new Dimension(BLOCK_SIZE, BLOCK_SIZE));
        restartButton.setBackground(Color.LIGHT_GRAY);
        restartButton.setIcon(icons[14]);
        // 设置字体
        foundAndSumLabel.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        timeCostLabel.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        restartButton.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        // 添加组件
        FlowLayout infoLayout = new FlowLayout();
        infoLayout.setHgap(25);
        JPanel infoPanel = new JPanel(infoLayout);
        infoPanel.setSize(230, 0);
        infoPanel.add(foundAndSumLabel, BorderLayout.CENTER);
        infoPanel.add(restartButton, BorderLayout.CENTER);
        infoPanel.add(timeCostLabel, BorderLayout.CENTER);
        // 添加按钮的监听事件
        restartButton.addActionListener((ActionEvent e) -> {
            initGame();
            new Thread(() -> PlaySoundsUtil.playSounds(PlaySoundsUtil.SOUND_CLICK1)).start();
        });

        add(centerPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.NORTH);
        // 初始化地图
        mapGenerator();
        paintTimer.start();
    }

    /**
     * 初始化地图
     */
    private void mapGenerator() {
        int x = diff.x;
        int y = diff.y;
        JPanel map = new JPanel(new GridLayout(x, 0));
        map.setPreferredSize(new Dimension(y * BLOCK_SIZE, x * BLOCK_SIZE));
        centerPanel.add(map, BorderLayout.CENTER);

        cells = new Cell[x][];
        for (int i = 0; i < x; i++) {
            cells[i] = new Cell[y];
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(0, i, j);
                cells[i][j].setPreferredSize(new Dimension(BLOCK_SIZE, BLOCK_SIZE));
                cells[i][j].setIcon(icons[0]);
                map.add(cells[i][j]);
            }
        }
        for (Cell[] cell : cells) {
            for (Cell aCell : cell) aCell.setNeighbours(cells);
        }
        initGame();
    }

    /**
     * 初始游戏
     */
    private void initGame() {
        isGameOver = false;
        isClick = false;
        timeCost = 0;
        found = 0;
        timer.stop();
        setFoundAndSumLabel();
        setTimeCostLabel();

        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                aCell.setIcon(icons[0]);
                aCell.underGround = 0;
                aCell.status = Cell.OFF;
            }
        }
        for (int i = 0; i < diff.mineNum; i++) {
            randMine();
        }
        updateMap();
        // win();
        //Test();
    }

    /**
     * 随机生成一个雷
     */
    private void randMine() {
        int x = diff.x;
        int y = diff.y;
        int randX;
        int randY;
        Random random = new Random(System.currentTimeMillis());
        do {
            randX = random.nextInt(x);
            randY = random.nextInt(y);
        } while (cells[randX][randY].underGround == -1);
        cells[randX][randY].underGround = -1;
    }

    /**
     * 生成完所有雷后更新地图
     */
    private void updateMap() {
        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                if (aCell.underGround == -1) {
                    continue;
                }
                int num = 0;
                for (int i = 0; i <= 2; i++) {
                    for (int j = 0; j <= 2; j++) {
                        if (aCell.neighbours[i][j] != null && aCell.neighbours[i][j].underGround == -1) {
                            num++;
                        }
                    }
                }
                aCell.underGround = num;
            }
        }
    }

    /**
     * 游戏结束
     */
    private void gameOver() {
        new Thread(() -> PlaySoundsUtil.playSounds(PlaySoundsUtil.SOUND_BOOM)).start();
        isGameOver = true;
        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                if (aCell.underGround == -1) {
                    aCell.setIcon(icons[12]);
                    aCell.status = Cell.MINE;
                }
                if (aCell.status == Cell.MARKED) {
                    aCell.setIcon(icons[13]);
                }
                if (aCell.status == Cell.OFF && aCell.underGround != -1) {
                    if (aCell.underGround == 0) {
                        aCell.setIcon(icons[11]);
                    } else {
                        aCell.setIcon(icons[aCell.underGround]);
                    }
                    aCell.status = Cell.NUMBER;
                }
            }

        }

        timer.stop();
        JOptionPane.showMessageDialog(this, "游戏结束", "很遗憾", JOptionPane.WARNING_MESSAGE);
        // initGame();
    }

    /**
     * 游戏胜利
     */
    private void win() {
        new Thread(() -> PlaySoundsUtil.playSounds(PlaySoundsUtil.SOUND_SUCCESS)).start();
        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                if (aCell.underGround == -1) {
                    aCell.setIcon(icons[10]);
                }
            }
        }
        timer.stop();
        if (Difficulty.isNotCustomizeDiff(diff)) {
            VictoryDialog victoryDialog = new VictoryDialog(null, "胜利", timeCost, diff);
            victoryDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "恭喜! 游戏获胜", "游戏获胜",
                    JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /**
     * 设置发现发现雷的标签文字
     */
    private void setFoundAndSumLabel() {
        foundAndSumLabel.setText("剩余:" + (diff.mineNum - found));
    }

    /**
     * 设置游戏花时的标签文字
     */
    private void setTimeCostLabel() {
        timeCostLabel.setText(TimeFormat.getFormatTime(timeCost));
    }

    /**
     * 根据不同难度返回自适应的窗口大小
     *
     * @return 返回当前难度的串口大小
     */
    Dimension getDimension() {
        return new Dimension(diff.y * BLOCK_SIZE + 20, diff.x * BLOCK_SIZE + 92);
    }

    class Cell extends JButton {
        private static final int MINE = -1;     // 雷
        private static final int OFF = 0;       // 未点击
        private static final int NUMBER = 1;    // 数字
        private static final int MARKED = 2;    // 已标记

        private int status = OFF;                       // 当前状态
        private int underGround;                        // -1代表雷, 0-8代表附近有多少雷
        private int posX, posY;                         // 位置
        private Cell[][] neighbours = new Cell[3][3];   // 邻居, neighbours[1][1]代表自己, 为null
        private int clickedTimes = 0;

        // 实现双击事件, 单击后200毫秒内再次单击视为双击
        private Timer doubleClickTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clickedTimes == 1) {
                    leftClick();
                } else if (clickedTimes == 2) {
                    doubleClick();
                }
                clickedTimes = 0;
                doubleClickTimer.stop();
            }
        });

        // 鼠标监听事件
        private MouseListener cellAction = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (status == OFF) {
                    new Thread(() -> PlaySoundsUtil.playSounds(PlaySoundsUtil.SOUND_CLICK2)).start();
                    setIcon(icons[0]);
                }

                if (timeCost == 0) {
                    timer.start();
                }

                if (e.getButton() == 1) {
                    clickedTimes++;
                    if (clickedTimes == 1) {
                        doubleClickTimer.start();
                    }
                }
                if (e.getButton() == 2 || e.getButton() == 3) {
                    rightClick();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };

        Cell(int underGround, int posX, int posY) {
            this.underGround = underGround;
            this.posX = posX;
            this.posY = posY;

            addMouseListener(cellAction);
        }

        /**
         * 左键点击
         *
         * @return 如果游戏胜利返回1, 否则返回0
         */
        private int leftClick() {
            // 对于非OFF状态的, 左键点击无效
            if (status != OFF || isGameOver) {
                return 0;
            }

            status = NUMBER;
            switch (underGround) {
                case 0:         // 如果是空单元, 则对邻居进行左键点击
                    setIcon(icons[11]);
                    for (int i = 0; i <= 2; i++) {
                        for (int j = 0; j <= 2; j++) {
                            if (neighbours[i][j] != null && neighbours[i][j].status == OFF) {
                                neighbours[i][j].leftClick();
                            }
                        }
                    }
                    isClick = true;
                    break;
                case -1:        // 点击到雷, 游戏结束
                    if (!isClick) {
                        System.out.println("第一次就点到了雷");
                        randMine();
                        underGround = 0;
                        updateMap();
                        if (underGround == 0) {
                            setIcon(icons[11]);
                        } else {
                            setIcon(icons[underGround]);
                        }
                        isClick = true;
                    } else {
                        gameOver();
                    }
                    break;
                default:        // 显示单元下的数字
                    isClick = true;
                    setIcon(icons[underGround]);
                    break;
            }
            boolean flag = false;
            // 判断游戏是否胜利
            for (Cell[] cell : cells) {
                for (Cell aCell : cell) {
                    if (aCell.status == OFF && aCell.underGround != -1) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            if (!flag && !isGameOver) {
                win();
                return 1;
            }

            return 0;
        }

        /**
         * 右击标记
         */
        private void rightClick() {
            // 如果当前状态是OFF, 则改为MARKED, 如果当前状态是MARKED, 则改为OFF
            if (status == OFF) {
                status = MARKED;
                setIcon(icons[10]);
                found++;
            } else if (status == MARKED) {
                status = OFF;
                setIcon(icons[0]);
                found--;
            }
            setFoundAndSumLabel();
        }

        /**
         * 双击清理
         */
        private void doubleClick() {
            int num = 0;
            // 得到周围八个方格内的标记数量
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (neighbours[i][j] != null && neighbours[i][j].status == MARKED) {
                        num++;
                    }
                }
            }
            // 标记数量与当前放个下的数字相同
            if (num == underGround && num != 0) {
                for (int i = 0; i <= 2; i++) {
                    for (int j = 0; j <= 2; j++) {
                        if (neighbours[i][j] == null) {
                            continue;
                        }
                        // 如果标记错误, 则直接游戏错误
                        if (neighbours[i][j].status == MARKED && neighbours[i][j].underGround != -1 && !isGameOver) {
                            gameOver();
                            return;
                        }
                        // 如果邻居的状态是可以点击的话,则该邻居左键点击
                        if (neighbours[i][j].status == OFF) {
                            if (neighbours[i][j].leftClick() == 1) {
                                return;
                            }
                        }
                    }
                }
            }
        }

        /**
         * 设置邻居
         *
         * @param cells 地图
         */
        void setNeighbours(Cell[][] cells) {
            // 访问当前单元格的周围八个位置, 如果该位置不存在则为null
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int nx = posX + i;
                    int ny = posY + j;
                    if (!(i == 0 && j == 0)) {
                        if (nx >= 0 && ny >= 0 && nx < diff.x && ny < diff.y) {
                            neighbours[i + 1][j + 1] = cells[nx][ny];
                        }
                    }
                }
            }
        }

    }

}
