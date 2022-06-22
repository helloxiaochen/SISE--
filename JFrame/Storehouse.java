package test.JFrame;

import com.mysql.jdbc.StringUtils;
import test.mysql.DBSourse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class Storehouse extends JFrame {

    Vector rowData,columnNames;
    JTable jt=null;
    JScrollPane jsp=null;
    PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;

    public Storehouse(){
        setTitle("库存管理");
        setVisible(true);
        //setSize(700,300);
        setLocation(700,300);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //面板和输入框
        JLabel l1 = new JLabel("编号:");
        JLabel l2 = new JLabel("名称:");
        JLabel l3 = new JLabel("价格:");
        JLabel l4 = new JLabel("库存:");
        JLabel l5 = new JLabel("仓库:");
        JTextField t1 = new JTextField(10);
        JTextField t2 = new JTextField(10);
        JTextField t3 = new JTextField(10);
        JTextField t4 = new JTextField(10);
        JTextField t5 = new JTextField(10);

        //创建水平容器
        Box box1 = Box.createHorizontalBox();
        box1.add(l1);
        box1.add(t1);
        box1.add(l2);
        box1.add(t2);
        box1.add(l3);
        box1.add(t3);
        Box box2 = Box.createHorizontalBox();
        box2.add(l4);
        box2.add(t4);
        box2.add(l5);
        box2.add(t5);
        //box2.add(Box.createHorizontalBox());

        //创建JTable显示数据库数据
       columnNames = new Vector();
       columnNames.add("编号");
       columnNames.add("名称");
       columnNames.add("价格");
       columnNames.add("库存");
       columnNames.add("仓库");
       rowData = new Vector();
//       Vector hang = new Vector();
//       hang.add("1");
//       hang.add("橡皮擦");
//       hang.add("3");
//       hang.add("100000");
//       hang.add("文具部");
//       rowData.add(hang);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            ct = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true",
                    "root","123456");
            ps = ct.prepareStatement("select * from store");
            rs = ps.executeQuery();

            while(rs.next()){
                Vector hang = new Vector();
                hang.add(rs.getInt(1));
                hang.add(rs.getString(2));
                hang.add(rs.getInt(3));
                hang.add(rs.getString(4));
                hang.add(rs.getString(5));

                rowData.add(hang);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
                if(ct!=null){
                    ct.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
       jt = new JTable(rowData,columnNames);
       jsp = new JScrollPane(jt);
       Box box3 = Box.createHorizontalBox();
       box3.add(jsp);
       Box vBox2 = Box.createVerticalBox();
       vBox2.add(box3);
//       this.setContentPane(vBox2);
//       this.pack();

       //定义按钮
        JButton enterbtn = new JButton("进库");
        JButton delectbtn = new JButton("出库");
        //JButton breakbtn = new JButton("刷新");
        Box box4 = Box.createHorizontalBox();
        box4.add(enterbtn);
        box4.add(delectbtn);
        //box4.add(breakbtn);

        //创建垂直容器，放置水平箱
        Box vBox = Box.createVerticalBox();
        vBox.add(box1);
        vBox.add(box2);
        vBox.add(box3);
        vBox.add(box4);
        this.setContentPane(vBox);
        this.pack();

        //按钮事件
        enterbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = null;
                int id1 = t1.getColumns();
                String name1 = t2.getText();
                int price1 =t3.getColumns();
                String store1 = t4.getText();
                String packet1 = t5.getText();

                if(StringUtils.isEmptyOrWhitespaceOnly(String.valueOf(id1))){
                    JOptionPane.showMessageDialog(null,"编号不能为空,必须为数字");
                    return;
                }
                if(StringUtils.isEmptyOrWhitespaceOnly(name1)){
                    JOptionPane.showMessageDialog(null,"名称不能为空");
                    return;
                }
                if(StringUtils.isEmptyOrWhitespaceOnly(String.valueOf(price1))){
                    JOptionPane.showMessageDialog(null,"价格不能为空,必须是数字");
                    return;
                }
                if(StringUtils.isEmptyOrWhitespaceOnly(store1)){
                    JOptionPane.showMessageDialog(null,"库存不能为空");
                    return;
                }
                if(StringUtils.isEmptyOrWhitespaceOnly(packet1)){
                    JOptionPane.showMessageDialog(null,"仓库不能为空");
                    return;
                }

                if(e.getSource() == enterbtn){
                    String id = t1.getText();
                    String name = t2.getText();
                    String price = t3.getText();
                    String store = t4.getText();
                    String packet = t5.getText();

                    DBSourse sourse = new DBSourse("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true",
                            "root", "123456");
                    sql = "insert store(id,name,price,store,packet) value("+"?,?,?,?,?)";

                    try {
                        Connection connection = sourse.getConnection();
                        System.out.println(connection);
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, String.valueOf(id));
                        preparedStatement.setString(2,name);
                        preparedStatement.setString(3,String.valueOf(price));
                        preparedStatement.setString(4,store);
                        preparedStatement.setString(5,packet);

                        int i  = preparedStatement.executeUpdate();
                        if(i>0){
                            System.out.println(i+"成功行");
                        }
                        JOptionPane.showMessageDialog(null,"添加成功");
                    } catch (SQLException throwables) {
                        JOptionPane.showMessageDialog(null,"编号、价格、库存必须输入正数");
                        throwables.printStackTrace();
                    }
                }
            }
        });

        delectbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id1 = t1.getColumns();
                String name1 = t2.getText();
                int price1 =t3.getColumns();
                String store1 = t4.getText();
                String packet1 = t5.getText();

                if(StringUtils.isEmptyOrWhitespaceOnly(String.valueOf(id1))){
                    JOptionPane.showMessageDialog(null,"编号不能为空,必须为数字");
                    return;
                }
                if(StringUtils.isEmptyOrWhitespaceOnly(name1)){
                    JOptionPane.showMessageDialog(null,"名称不能为空");
                    return;
                }
                if(StringUtils.isEmptyOrWhitespaceOnly(String.valueOf(price1))){
                    JOptionPane.showMessageDialog(null,"价格不能为空,必须是数字");
                    return;
                }
                if(StringUtils.isEmptyOrWhitespaceOnly(store1)){
                    JOptionPane.showMessageDialog(null,"库存不能为空");
                    return;
                }
                if(StringUtils.isEmptyOrWhitespaceOnly(packet1)){
                    JOptionPane.showMessageDialog(null,"仓库不能为空");
                    return;
                }
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true",
                            "root","123456");
                    String sql = "delete from store where id=?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, Integer.parseInt(t1.getText()));
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    //JOptionPane.showMessageDialog(null,"出库成功");
                } catch (ClassNotFoundException classNotFoundException) {
                    JOptionPane.showMessageDialog(null,"已出库");
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(null,"已出库");
                throwables.printStackTrace();
            }
            }
        });

//        breakbtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String sql = null;
//
//                DBSourse sourse = new DBSourse("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true",
//                        "root", "123456");
//                sql = "select * from store";
//
//                try {
//                    Connection connection = sourse.getConnection();
//                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//
//            }
//        });
    }

    public static void main(String[] args){
        new Storehouse();
    }
}
