package MyReadBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

//读取body体中的各种数据
public class OrdersReaderBody {
    public static String readBody(HttpServletRequest request) throws UnsupportedEncodingException {
        //1.先获取body的长度
        int length = request.getContentLength();
        //2.读取的这个
        byte[] buffer = new byte[length];

        try {
            //buffer 就是一个缓冲区 将数据写入buffer中
            InputStream inputStream = request.getInputStream();
            inputStream.read(buffer, 0, length);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(buffer, "UTF-8");
    }
}
