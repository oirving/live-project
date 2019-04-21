package lottery.util;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBUtil {

    //常量
    private static final String ip = "127.0.0.1";
    private static final int port = 3306;
    private static final String database = "lottery";
    private static final String encoding = "UTF-8";
    private static final String loginName = "root";
    private static final String password = "0523";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param
     * @return java.sql.Connection
     * @author Murphy
     * @date 2019/4/21 20:08
     * @description 获取连接
     */
    public static Connection getConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s:%d/%s?useSSL=false&characterEncoding=%s", ip, port, database, encoding);

        return DriverManager.getConnection(url, loginName, password);
    }


    private static Connection connection;

    /**
     * @param
     * @return java.sql.Connection
     * @author Murphy
     * @date 2019/4/21 20:08
     * @description 单例模式
     */
    public static synchronized Connection getInstance() throws SQLException {
        if (connection == null) connection = getConnection();

        return connection;
    }

    /**
     * @param rs
     * @param stmt
     * @param conn
     * @return void
     * @author Murphy
     * @date 2019/4/21 20:08
     * @description 关闭连接的方法
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            if (stmt != null)
                stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param matcher
     * @return void
     * @author Murphy
     * @date 2019/4/21 17:40
     * @description 因为emoji存不下，数据库表改成了utf8m64
     */
    public static void store2DB(Matcher matcher) {
        try {
            String startTime = matcher.group(1);
            Timestamp time = string2Date(startTime);
            String userName = matcher.group(2);
            String userId = matcher.group(4);
            String message = matcher.group(6);
            Connection connection = getInstance();
            Content content = parseContent(message);

            String sql = "insert into chat_record(userId,userName,time,isUseKeyword,numberOfCharacters,content) " +
                    "					values(?		,?		,?	,?				,?,					?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, userName);
            preparedStatement.setTimestamp(3, time);
            preparedStatement.setBoolean(4, content.isUseKeyword);
            preparedStatement.setInt(5, content.numberOfCharacters);
            preparedStatement.setString(6, message);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Murphy
     * @date 2019/4/21 17:40
     * @description 静态内部类
     */
    static class Content {
        boolean isUseKeyword;
        int numberOfCharacters;
    }

    /**
     * @param mesage
     * @return lottery.util.DBUtil.Content
     * @author Murphy
     * @date 2019/4/21 17:40
     * @description 解析内容
     */
    private static Content parseContent(String mesage) {

        Pattern pattern = Pattern.compile("(#.*?#)");
        Matcher matcher = pattern.matcher(mesage);

        Content content = new Content();

        int numberOfCharacters = mesage.length();
        while (matcher.find()) {
            content.isUseKeyword = true;
            numberOfCharacters -= matcher.group().length();
        }
        content.numberOfCharacters = numberOfCharacters;

        return content;
    }

    /**
     * @param startTime
     * @return java.sql.Timestamp
     * @author Murphy
     * @date 2019/4/21 17:26
     * @description 解析时间
     */
    private static Timestamp string2Date(String startTime) {
        return Timestamp.valueOf(startTime);
    }

    /**
     * @param
     * @return void
     * @author Murphy
     * @date 2019/4/21 20:36
     * @description 在抽奖开始之前清空表
     */
    public static void clearTable() throws SQLException {

        String sql = "DELETE FROM `chat_record` WHERE recordId>0;";
        PreparedStatement preparedStatement = getInstance().prepareStatement(sql);
        preparedStatement.execute();
        preparedStatement.close();
    }
}