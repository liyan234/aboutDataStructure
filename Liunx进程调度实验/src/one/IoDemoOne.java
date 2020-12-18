package one;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IoDemoOne {

    // 一个基于文件的字节输入流对象
    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream("/");//具体的文件的位置
            int temp = file.read();//读取一个字节
            while (temp != -1) {
                System.out.println(temp);
                temp = file.read();
            }
            file.close();//读完了 关闭这个
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
