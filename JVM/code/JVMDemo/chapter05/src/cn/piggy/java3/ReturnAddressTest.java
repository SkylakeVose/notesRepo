package cn.piggy.java3;

import java.io.FileReader;
import java.io.IOException;

public class ReturnAddressTest {

    public char methodChar() {
        return 'a';
    }


    public String methodString() {
        return null;
    }


    public void methodVoid() {

    }


    static {
        int x = 10;
    }

    public void method2() {
        try {
            method1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void method1() throws IOException {
        FileReader fis = new FileReader("piggy.txt");
        char[] cBuffer = new char[1024];
        int len;
        while((len = fis.read(cBuffer)) != -1) {
            String str = new String(cBuffer, 0, len);
            System.out.println(str);
        }
        fis.close();
    }
}
