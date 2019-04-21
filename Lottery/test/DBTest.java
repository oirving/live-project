import lottery.util.DBUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Murphy
 * @date 2019/4/21 18:42
 */
public class DBTest {

    @Test
    public void test() throws SQLException {
        Connection connection = DBUtil.getInstance();
        String sql = "select  * from chat_record ORDER BY recordId desc ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.print(resultSet.getString(2));
            System.out.print(resultSet.getString(3));
            System.out.print(resultSet.getDate(4));
            System.out.print(resultSet.getString(5));
            System.out.print(resultSet.getInt(6));
            System.out.println(resultSet.getString(7));
        }
    }
}
