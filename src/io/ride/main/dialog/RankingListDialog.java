package io.ride.main.dialog;

import io.ride.main.Difficulty;
import io.ride.util.Record;
import io.ride.util.RecordUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-27
 * Time: 下午6:25
 * 排行对话框
 */
public class RankingListDialog extends JDialog implements ActionListener {
    private Difficulty diff;
    private JTextArea textArea;
    private JButton confirmButton;
    private JButton clearButton;


    public RankingListDialog(Frame owner, Difficulty diff) {
        super(owner);
        this.diff = diff;
        // 设置对话框标题
        String title = null;
        if (diff == Difficulty.SIMPLE) {
            title = "初级难度记录";
        } else if (diff == Difficulty.MIDDLE) {
            title = "中级难度记录";
        } else if (diff == Difficulty.HARD) {
            title = "高级难度记录";
        }
        setTitle(title);
        BorderLayout borderLayout = new BorderLayout();
        GridLayout gridLayout = new GridLayout(1, 4);
        gridLayout.setHgap(10);
        JPanel buttonPanel = new JPanel(gridLayout);
        setLayout(borderLayout);

        // 创建组件
        textArea = new JTextArea(10, 10);
        confirmButton = new JButton("确认");
        clearButton = new JButton("清空");
        // 设置字体
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        confirmButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        clearButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        // 设置文本域无法修改
        textArea.setEditable(false);
        // 将记录添加到文本域
        TreeSet<Record> records = RecordUtil.readRecords(RecordUtil.getFilename(this.diff));

        for (Record record : records) {
            textArea.append(record.toString());
        }
        if (Objects.equals(textArea.getText(), "")) {
            clearButton.setEnabled(false);
        }
        // 添加监听事件
        confirmButton.addActionListener(this);
        clearButton.addActionListener(this);
        // 添加组件
        buttonPanel.add(new JPanel());
        buttonPanel.add(confirmButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(new JPanel());
        add(textArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JPanel(), BorderLayout.WEST);
        add(new JPanel(), BorderLayout.EAST);

        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(textArea.getText(), "")) {
            clearButton.setEnabled(false);
        } else {
            clearButton.setEnabled(true);
        }
        Object obj = e.getSource();
        if (obj == confirmButton) {
            setVisible(false);
        } else if (obj == clearButton) {
            textArea.setText("");
            RecordUtil.clearRecord(RecordUtil.getFilename(diff));
        }

    }
}
