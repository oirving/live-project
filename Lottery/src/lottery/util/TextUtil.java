package lottery.util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Murphy
 * @date 2019/4/21 16:23
 */
public class TextUtil {
    public static void resovle(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        StringBuilder presentString = new StringBuilder();
        Pattern pattern = Pattern.compile("(2022-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2} )" +  //发言日期+时间
                "(.*?)" +//匹配userName
                "(\\()" +//匹配左括号
                "([0-9]*)" +//匹配qq号
                "(\\))" +//匹配右括号
                "(.*?)" + //匹配content
                "(2022-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2} )");//下一条记录发言日期+时间
//        Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} .*?[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
        while ((line = bufferedReader.readLine()) != null) {
            presentString.append(line);
            Matcher matcher = pattern.matcher(presentString.toString());
            if (matcher.find()) {
                DBUtil.store2DB(matcher);
                presentString = new StringBuilder(line);
            }
        }
        presentString.append(line);
        Matcher matcher = pattern.matcher(presentString.toString());
        if (matcher.find()) {
            DBUtil.store2DB(matcher);
        }
    }

}
