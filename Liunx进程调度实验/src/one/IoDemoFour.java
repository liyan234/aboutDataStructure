package one;

import java.io.FileWriter;
import java.io.IOException;

public class IoDemoFour {


    //基于字符流的写入
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter(" ");
            //writer.write("   ");
            char[] chars = ("  ").toCharArray();
            //writer.write(chars);
            for (char c : chars) {
                writer.write(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
