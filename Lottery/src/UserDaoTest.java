import java.io.IOException;
import java.sql.SQLException;

import lottery.dao.UserDao;
import lottery.util.TextUtil;

public class UserDaoTest {

	public static void main(String [] args) throws IOException, SQLException {
        String fileName = "test/QQrecord-2022.txt";
        TextUtil.resovle(fileName);
    }
}
