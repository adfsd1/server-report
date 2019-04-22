package cn.report.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Test1 {
    public static void main(String[] args) {
        String filePath = "config/jdbc.properties";
        Properties prop = new Properties();
        try {

            InputStream fis = new FileInputStream(filePath);
            prop.load(fis);

            OutputStream fos = new FileOutputStream(filePath);
            prop.setProperty("ii", "77");

            prop.store(fos, filePath);

        } catch (Exception e) {

        }

    }

}