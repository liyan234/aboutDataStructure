package one;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


//基于文件的的输出流
public class ioDemoTwo {
    public static void main(String[] args) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/");//文件的具体位置
            for (int i = 0; i < 26; i++) {
                fileOutputStream.write(65+i);
            }
            fileOutputStream.close(); //关闭文件字节输出流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
