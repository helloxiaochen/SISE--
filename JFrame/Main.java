package test.JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main extends JFrame {

    public Main(){
        setTitle("首页");
        setVisible(true);
        setSize(800,560);
        setLocation(700,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //菜单栏
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jm1 = new JMenu("采购");
        JMenu jm2 = new JMenu("库存");
        JMenu jm3 = new JMenu("销售");
        JMenu jm4 = new JMenu("备份");
        JMenuItem jm5 = new JMenuItem("采购订单");
        JMenuItem jm7 = new JMenuItem("库存管理");
        JMenuItem jm6 = new JMenuItem("查看库存");
        JMenuItem jm8 = new JMenuItem("销售订单");
        JMenuItem jm9 = new JMenuItem("sql备份");
        JMenuItem jm10 = new JMenuItem("订单查看");
        JMenuItem jm11 = new JMenuItem("订单查看");

        jm1.add(jm5);
        jm1.add(jm11);
        jm2.add(jm7);
        jm2.add(jm6);
        jm3.add(jm8);
        jm3.add(jm10);
        jm4.add(jm9);
        jMenuBar.add(jm1);
        jMenuBar.add(jm2);
        jMenuBar.add(jm3);
        jMenuBar.add(jm4);
        add(jMenuBar, BorderLayout.NORTH);

        //背景图片
        this.setVisible(true);
        ImageIcon img = new ImageIcon("src\\test\\photo\\photo.jpg");
        JLabel imgLabel = new JLabel(img);
        this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        ((JPanel)cp).setOpaque(false);

        //菜单栏事件
        jm5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Buy();
            }
        });

        jm7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Storehouse();
            }
        });

        jm6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Storecheck();
            }
        });

        jm11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Buycheck();
            }
        });

        jm8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Sale();
            }
        });

        jm10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Salecheck();
            }
        });

        jm9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newBackup(e);
                JOptionPane.showMessageDialog(null,"备份成功");
            }

        });
    }

    public boolean newBackup(ActionEvent e) {
        try{
            Runtime rt = Runtime.getRuntime();
            // 调用 调用mysql的安装目录的命令
            Process child = rt.exec("cmd /c D:/mysql8/bin/mysqldump -h localhost -p3306 -uroot -p123456 test > D:\\test.sql");
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
            InputStreamReader xx = new InputStreamReader(in, "utf-8");
            // 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader br = new BufferedReader(xx);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();

//            // 要用来做导入用的sql目标文件：
//            FileOutputStream fout = new FileOutputStream("D:\\IDEA project\\src\\test\\test.sql");
//            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
//            writer.write(outStr);
//            writer.flush();
            in.close();
            xx.close();
            br.close();
//            writer.close();
//            fout.close();

            if(child.waitFor() == 0){//0 表示线程正常终止。
                return true;
            }
        }catch (Exception a) {
            a.printStackTrace();
        }
        return false;
    }

    public static void main(String[] ages){
        new Main();
    }
}
