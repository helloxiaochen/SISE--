package test.JFrame;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class Storecheck extends JFrame {
    Vector rowData,columnNames;
    JTable jt=null;
    JScrollPane jsp=null;
    PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;

    public Storecheck(){
        setTitle("库存查看");
        setVisible(true);
        setLocation(700,300);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //创建JTable显示数据库数据
        columnNames = new Vector();
        columnNames.add("编号");
        columnNames.add("名称");
        columnNames.add("价格");
        columnNames.add("库存");
        columnNames.add("仓库");
        rowData = new Vector();
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
        Box box = Box.createHorizontalBox();
        box.add(jsp);
        Box vBox = Box.createVerticalBox();
        vBox.add(box);
        this.setContentPane(vBox);
        this.pack();
    }

    public static void main(String[] args){
        new Storecheck();
    }
}
