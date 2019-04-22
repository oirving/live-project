import lottery.util.TextUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Murphy
 * @date 2019/4/21 16:27
 */
public class TextUtilTest {

        @Test
    public void main() throws IOException, SQLException {
        String fileName = "Lottery/test/QQrecord-2022.txt";
        TextUtil.resovle(fileName);
    }
//    @Test
//    public void main() {
//        TextUtil.executePython();
//    }
}
