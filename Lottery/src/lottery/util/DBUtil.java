package lottery.util;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBUtil {
	private static final String ip = "127.0.0.1";
	private static final int port = 3306;
	private static final String database = "live";
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

	public static Connection getConnection() throws SQLException {
		String url = String.format("jdbc:mysql://%s:%d/%s?useSSL=false&characterEncoding=%s", ip, port, database, encoding);
		return DriverManager.getConnection(url, loginName, password);
	}

	private static Connection connection;

	public static synchronized Connection getInstance() throws SQLException {
		if (connection == null) connection = getConnection();
		return connection;
	}
  
	/* 关闭连接方法*/
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
	 * @description ����Ϣ�洢��database, ��Ϊ��Щ�ط���emoji�����Ա�Ҫ�ĳ�ʹ��utf8mb4��
	 */
	public static void store2DB(Matcher matcher) {
		try {
			String startTime = matcher.group(1);
			Date time = parseTime(startTime);
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
			preparedStatement.setDate(3, time);
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
	 * @description ��̬�ڲ���
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
	 * @description ������Ϣ
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
	 * @return java.util.Date
	 * @author Murphy
	 * @date 2019/4/21 17:26
	 * @description ����ʱ���Dateʵ��
	 */
	private static Date parseTime(String startTime) {
		java.util.Date date = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = dateFormat.parse(startTime);
		} catch (ParseException px) {
			px.printStackTrace();
		}
		return new Date(date.getTime());
	}

}