package heigh_level;

import java.io.File;
import java.io.IOException;

public class FilePractice {
    public static void main(String[] args) throws IOException {
        File file = new File("myFile.txt");

        if (!file.exists()) {
            System.out.println("文件不存在，现在创建它！");
            file.createNewFile();
        } else  {
            System.out.println("文件已存在。");
        }

        System.out.println("文件名: " + file.getName());
        System.out.println("文件路径: " + file.getPath());
        System.out.println("绝对路径: " + file.getAbsolutePath());
        System.out.println("文件大小 (字节): " + file.length());
    }
}
