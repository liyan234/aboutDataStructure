package one;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ioDemoThree {

    //字符流
    public static void main(String[] args) {

        try {
            FileReader fileReader = new FileReader(" "); //地址
            int temp = fileReader.read();
            while (temp != -1) {
                System.out.println((char)temp);
                temp = fileReader.read();
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
