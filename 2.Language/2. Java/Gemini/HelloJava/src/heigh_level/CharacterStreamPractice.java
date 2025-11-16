package heigh_level;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class CharacterStreamPractice {
    public static void main(String[] args) {
        File file = new File("Poem.txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("静夜思\n"); // \n 是换行符
            writer.write("床前明月光，\n");
            writer.write("疑是地上霜。\n");
            writer.write("举头望明月，\n");
            writer.write("低头思故乡。\n");
            System.out.println("诗歌写入成功！");
        } catch (IOException e) {
            System.out.println("写入文件时出错: " + e.getMessage());
        }

        System.out.println("\n--- 2. 使用 FileReader 读取文件 ---");

        try (FileReader reader = new FileReader(file)) {
            // 一次读取一个字符
            int charCode;
            System.out.println("开始逐字读取：");
            while ((charCode = reader.read()) != -1) { // read()返回-1表示读到文件末尾
                // 将整数编码转为字符并打印
                System.out.print((char) charCode);
            }
        } catch (IOException e) {
            System.out.println("读取文件时出错: " + e.getMessage());
        }
    }
}
