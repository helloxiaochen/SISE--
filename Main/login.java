package test.Main;

import com.mysql.jdbc.StringUtils;
import test.JFrame.Main;
import test.mysql.DBSourse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JFrame{

    private JFrame frame;
    /**
     * 启动应用程序
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    login window = new login();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建应用程序
     */
    public login() {
        initialize();
    }

    /**
     * 初始化
     */
    private void initialize() {
        frame = new JFrame("登录");
        frame.setSize(700,400);
        frame.setLocation(700,300);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        //标题
        JLabel l1 = new JLabel("进销存系统");
        l1.setFont(new Font("宋体", Font.BOLD, 25));
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        l1.setBounds(441, 10, 159, 52);
        frame.getContentPane().add(l1);
        //单选框设定
//        JRadioButton user = new JRadioButton("用户");
//        user.setFont(new Font("宋体",Font.BOLD,18));
//        user.setBounds(393, 68, 121, 23);
//        frame.getContentPane().add(user);
//        JRadioButton superuser = new JRadioButton("管理员");
//        superuser.setFont(new Font("宋体",Font.BOLD,18));
//        superuser.setBounds(544, 68, 121, 23);
//        frame.getContentPane().add(superuser);
//        ButtonGroup group = new ButtonGroup();
//        group.add(user);
//        group.add(superuser);
        //定义输入框和面板
        JLabel l2 = new JLabel("用户名:");
        l2.setFont(new Font("宋体",Font.BOLD,15));
        l2.setBounds(430,113,65,33);
        frame.getContentPane().add(l2);
        JTextField t1 = new JTextField(20);
        t1.setBounds(505,119,121,21);
        frame.getContentPane().add(t1);
        JLabel l3 = new JLabel("密 码:");
        l3.setFont(new Font("宋体",Font.BOLD,15));
        l3.setBounds(430,172,54,15);
        frame.getContentPane().add(l3);
        JPasswordField t2 = new JPasswordField(20);
        t2.setBounds(505,169,121,21);
        t2.setEchoChar('*');
        frame.getContentPane().add(t2);
        //定义按钮
        JButton enterbtn = new JButton("登录");
        enterbtn.setBounds(377,245,93,23);
        enterbtn.setFont(new Font("宋体",Font.BOLD,15));
        frame.getContentPane().add(enterbtn);
        JButton resertbtn = new JButton("重置");
        resertbtn.setBounds(480,245,93,23);
        resertbtn.setFont(new Font("宋体",Font.BOLD,15));
        frame.getContentPane().add(resertbtn);
        JButton registeredbtn = new JButton("注册");
        registeredbtn.setBounds(581,245,93,23);
        registeredbtn.setFont(new Font("宋体",Font.BOLD,15));
        frame.getContentPane().add(registeredbtn);

        //背景图片
        frame.setVisible(true);
        ImageIcon img = new ImageIcon("src\\test\\photo\\picture.jpg");
        JLabel imgLabel = new JLabel(img);
        frame.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(100,100,img.getIconWidth(),img.getIconHeight());
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        ((JPanel)cp).setOpaque(false);

        //定义按钮事件
        resertbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == resertbtn){
                    t1.setText("");
                    t2.setText("");
                }
            }
        });

        registeredbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registered();
                dispose();
//                frame.removeNotify();
            }
        });

        enterbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = null;

                String username = t1.getText();
                String password = String.valueOf(t2.getPassword());

                if(StringUtils.isEmptyOrWhitespaceOnly(username)){
                    JOptionPane.showMessageDialog(null, "用户名不能为空！");
                    return;
                }
                if(StringUtils.isEmptyOrWhitespaceOnly(password)){
                    JOptionPane.showMessageDialog(null, "密码不能为空！");
                    return;
                }

                DBSourse sourse = new DBSourse("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true",
                        "root", "123456");
                sql = "select * from user where username = ? and password = ?";

                try {
                    Connection connection = sourse.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1,t1.getText());
                    preparedStatement.setString(2, String.valueOf(t2.getPassword()));

                    new Main();
                    dispose();
                    frame.removeNotify();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }
}
