package lottery.util;

import java.io.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Murphy
 * @date 2019/4/21 16:23
 */
public class TextUtil {
    /**
     * @param fileName
     * @return void
     * @author Murphy
     * @date 2019/4/21 20:31
     * @description 解析文件进数据库的接口
     */
    public static void resovle(String fileName) throws IOException, SQLException {

        DBUtil.clearTable();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
        // 每一行
        String line = null;
        StringBuilder presentString = new StringBuilder();

        // 正则表达式
        Pattern pattern = Pattern.compile("([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2} )" +  //发言日期+时间
                "(.*?)" +//匹配userName
                "([(<])" +//匹配左括号
                "([0-9]*|.*?@.*?)" +//匹配qq号
                "([)|>])" +//匹配右括号
                "(.*?)" + //匹配content
                "([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2} )");//下一条记录发言日期+时间
        // 读取每一行
        while ((line = bufferedReader.readLine()) != null) {
            presentString.append(line);
            Matcher matcher = pattern.matcher(presentString.toString());

            if (matcher.find()) {
                // 如果找到数据就存进数据库
                DBUtil.store2DB(matcher);
                presentString = new StringBuilder(line);
            }
        }
        // 读到终点之后的处理
        pattern = Pattern.compile("([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2} )" +  //发言日期+时间
                "(.*?)" +//匹配userName
                "([(<])" +//匹配左括号
                "([0-9]*|.*?@.*?)" +//匹配qq号
                "([)|>])" +//匹配右括号
                "(.*)");//匹配content
        Matcher matcher = pattern.matcher(presentString.toString());
        if (matcher.find()) {
            DBUtil.store2DB(matcher);
        }
        // 关闭数据库连接
        try {
            DBUtil.close(null, null, DBUtil.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
