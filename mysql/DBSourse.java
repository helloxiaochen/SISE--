package test.mysql;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DBSourse implements DataSource{
    private String driverclass;
    private String jdbc;
    private String user;
    private String password;
    private List<Connection> connections=new ArrayList<>();

    //定义函数和事件
    public String getDriverclass() {
        return driverclass;
    }

    public void setDriverclass(String driverclass) {
        this.driverclass = driverclass;
    }

    public String getJdbc() {
        return jdbc;
    }

    public void setJdbc(String jdbc) {
        this.jdbc = jdbc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DBSourse(String driverclass, String jdbc, String user, String password) {
        this.driverclass = driverclass;
        this.jdbc = jdbc;
        this.user = user;
        this.password = password;
        //创建十个连接
        for (int i=0;i<10;i++){
            Connection connection=null;
            try {
                Class.forName(driverclass);

                connection = DriverManager.getConnection(jdbc, user, password);

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            connections.add(connection);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connections.remove(0);
    }

    public void close(Connection connection){
        connections.add(connection);
    }
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }


}

