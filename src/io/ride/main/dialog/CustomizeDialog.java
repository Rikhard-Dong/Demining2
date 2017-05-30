package io.ride.main.dialog;

import io.ride.main.Difficulty;
import io.ride.main.MineSweep;
import io.ride.util.LimitedDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-26
 * Time: 下午10:03
 * 自定义难度对话框
 */
public class CustomizeDialog extends JDialog implements ActionListener {
    private JFrame parent;
    private JTextField rowsTextField;
    private JTextField colsTextField;
    private JTextField mineNumTextField;
    private Button confirmButton;
    private Button cancelButton;

    private int x;
    private int y;
    private int mineNum;

    public CustomizeDialog(Frame owner, String title) {
        super(owner, title);
        setLocationRelativeTo(owner);
        // 父窗体
        parent = (JFrame) owner;
        setPreferredSize(new Dimension(160, 120));
        setResizable(false);

        // 设置布局
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(20);
        borderLayout.setVgap(20);
        setLayout(borderLayout);
        GridLayout gridLayout = new GridLayout(4, 2);
        gridLayout.setHgap(15);
        gridLayout.setVgap(5);

        JPanel panel = new JPanel(gridLayout);

        // 创建组件
        JLabel rowsLabel = new JLabel("  行数");
        JLabel colsLabel = new JLabel("  列数");
        JLabel mineNumLabel = new JLabel("  雷数");
        rowsTextField = new JTextField();
        colsTextField = new JTextField(8);
        mineNumTextField = new JTextField(8);
        confirmButton = new Button("确定");
        cancelButton = new Button("取消");
        // 输入限制
        LimitedDocument limitedDocument1 = new LimitedDocument(2);
        LimitedDocument limitedDocument2 = new LimitedDocument(2);
        LimitedDocument limitedDocument3 = new LimitedDocument(4);
        limitedDocument1.setAllowCharAsString("0123456789");
        limitedDocument2.setAllowCharAsString("0123456789");
        limitedDocument3.setAllowCharAsString("0123456789");
        rowsTextField.setDocument(limitedDocument1);
        colsTextField.setDocument(limitedDocument2);
        mineNumTextField.setDocument(limitedDocument3);
        // 设置字体
        rowsLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        colsLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        mineNumLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        rowsTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        colsTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        mineNumTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        confirmButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        cancelButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        // 设置大小
        rowsTextField.setPreferredSize(new Dimension(300, 20));
        // 添加按钮的监听事件
        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);
        // 添加到对话框
        panel.add(rowsLabel);
        panel.add(rowsTextField);
        panel.add(colsLabel);
        panel.add(colsTextField);
        panel.add(mineNumLabel);
        panel.add(mineNumTextField);
        panel.add(confirmButton);
        panel.add(cancelButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
    }

    private void getValue() {
        x = Integer.parseInt(rowsTextField.getText());
        y = Integer.parseInt(colsTextField.getText());
        mineNum = Integer.parseInt(mineNumTextField.getText());
        if (x > 50) {
            x = 50;
        }
        if (x < 5) {
            x = 5;
        }
        if (y > 50) {
            x = 50;
        }
        if (y < 5) {
            y = 5;
        }
        // 保证雷最多只有75%
        if (mineNum > x * y - x * y * 0.25) {
            mineNum = (int) (x * y - x * y * 0.25);
        }
        // 保证雷最少有8%
        if (mineNum < x * y * 0.08) {
            mineNum = (int) (x * y * 0.08);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == confirmButton) {     // 确认按钮
            getValue();
            ((MineSweep) parent).beginGame(new Difficulty(x, y, mineNum));

            rowsTextField.setText("");
            colsTextField.setText("");
            mineNumTextField.setText("");
            setVisible(false);
        } else if (obj == cancelButton) {   // 取消按钮
            rowsTextField.setText("");
            colsTextField.setText("");
            mineNumTextField.setText("");
            setVisible(false);
        }
    }
}
