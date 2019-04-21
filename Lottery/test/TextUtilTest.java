import org.junit.jupiter.api.Test;
import lottery.util.TextUtil;

import java.io.IOException;

/**
 * @author Murphy
 * @date 2019/4/21 16:27
 */
public class TextUtilTest {

    @Test
    public void main() throws IOException {
        String fileName = "Lottery/test/QQrecord-2022.txt";
        TextUtil.resovle(fileName);
    }
}
