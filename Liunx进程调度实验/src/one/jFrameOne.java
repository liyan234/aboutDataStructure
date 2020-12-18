package one;

import javax.swing.*;

public class jFrameOne extends JFrame {

    public JLabel userLabel, passwordLabel; // 用户标签 密码标签
    public JButton loginButton, cancelButton; // 登录按钮 取消按钮
    public JPasswordField passwordField;
    public JTextField userField;

    public jFrameOne() {
        this.setSize(400, 450);// 窗口的大小
        this.setTitle("登录");
        this.setLayout(null);
        init();
        this.setVisible(true);
    }

    private void init() {
        //初始化上面这几个东西
        userLabel = new JLabel("用户名");
        passwordLabel = new JLabel("密码");
        userField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("登录");
        cancelButton = new JButton("取消");

        userLabel.setSize(60,30);
        userLabel.setLocation(60,60);
        this.add(userLabel);

        userField.setSize(180,30);
        userField.setLocation(120,60);
        this.add(userField);

        passwordLabel.setSize(60,30);
        passwordLabel.setLocation(60,120);
        this.add(passwordLabel);

        passwordField.setSize(180,30);
        passwordField.setLocation(120,120);
        this.add(passwordField);

        loginButton.setSize(80,80);
        loginButton.setLocation(120,180);
        this.add(loginButton);

        cancelButton.setSize(80,80);
        cancelButton.setLocation(280,180);
        this.add(cancelButton);

    }

    public static void main(String[] args) {
        jFrameOne j = new jFrameOne();
    }

}
