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

        DatabaseUtil.clearTable();
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
                DatabaseUtil.store2DB(matcher);
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
            DatabaseUtil.store2DB(matcher);
        }
        // 关闭数据库连接
        try {
            DatabaseUtil.close(null, null, DatabaseUtil.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param
     * @return void
     * @author Murphy
     * @date 2019/4/22 14:31
     * @description 执行Python程序
     * 需要的依赖在 “.\Lottery\src\lottery\python\analysis.py” 已声明
     */
    public static void executePython() {
        Runtime runtime = Runtime.getRuntime();
        String cmd = "python .\\Lottery\\src\\lottery\\python\\analysis.py";
        String output = "提示：请注意使用环境，" +
                "#pymysql：pip3 install pymysql\n" +
                "#seaborn：conda install seaborn\n" +
                "#numpy:conda install numpy\n" +
                "# wordcloud:pip3 install wordcloud\n" +
                "from wordcloud import WordCloud, STOPWORDS\n" +
                "from matplotlib: pip3 install matplotlib\n";
        System.err.println(output);
        try {
            Process process = runtime.exec(cmd);
            InputStream in = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String message;
            while ((message = br.readLine()) != null) {
                sb.append(message);
            }
            System.out.println(sb);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
