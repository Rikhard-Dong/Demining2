package io.ride.main.dialog;

import io.ride.main.Difficulty;
import io.ride.util.LimitedDocument;
import io.ride.util.RecordUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-26
 * Time: 下午11:57
 * 在标准难度下, 胜利后提示输入用户昵称
 */
public class VictoryDialog extends JDialog implements ActionListener {

    private int timeCost;
    private JButton confirmButton;
    private JTextField nameTextField;
    private Difficulty diff;


    public VictoryDialog(Frame owner, String title, int timeCost, Difficulty diff) {
        super(owner, title);
        setLocationRelativeTo(owner);
        setResizable(false);
        this.diff = diff;
        // 创建布局
        GridLayout gridLayout1 = new GridLayout(2, 1);
        GridLayout gridLayout2 = new GridLayout(2, 1);
        GridLayout gridLayout3 = new GridLayout(1, 3);
        BorderLayout borderLayout = new BorderLayout();

        borderLayout.setVgap(10);
        borderLayout.setHgap(20);
        JPanel panel = new JPanel(borderLayout);
        JPanel panel1 = new JPanel(gridLayout1);
        JPanel panel3 = new JPanel(gridLayout3);
        JPanel panel2 = new JPanel(gridLayout2);

        //panel.setPreferredSize(new Dimension(100, 200));
        this.timeCost = timeCost;

        // 创建组件
        JLabel label = new JLabel("<html><body><p>&nbsp;&nbsp;恭喜您完成游戏, 用时:" + this.timeCost +
                "&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请输入您的昵称&nbsp;&nbsp;&nbsp;</p>");
        nameTextField = new JTextField(8);
        confirmButton = new JButton("确定");
        // 设置字体
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        nameTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        confirmButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        // 设置输入限制
        LimitedDocument limitedDocument = new LimitedDocument(10);
        nameTextField.setDocument(limitedDocument);
        // 添加监听
        confirmButton.addActionListener(this);
        // 添加到组件
        panel1.add(new JPanel());
        panel1.add(label);
        panel3.add(new JPanel());
        panel3.add(confirmButton);
        panel3.add(new JPanel());
        panel2.add(panel3);
        panel2.add(new JPanel());
        panel.add(panel1, BorderLayout.NORTH);
        panel.add(nameTextField, BorderLayout.CENTER);
        panel.add(panel2, BorderLayout.SOUTH);
        panel.add(new Panel(), BorderLayout.WEST);
        panel.add(new Panel(), BorderLayout.EAST);

        add(panel);
        pack();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == confirmButton) {
            String name = nameTextField.getText();
            if (Difficulty.isNotCustomizeDiff(diff)) {
                // 将玩家昵称, 花费时间和日期写入到文件中
                RecordUtil.writeRecord(RecordUtil.getFilename(diff), timeCost, name);
            }
            nameTextField.setText("");
            setVisible(false);
        }
    }

}
