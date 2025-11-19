package learn.maoyu.basic.ControlFlowStatement;

import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Random random = new Random();
        int numberToGuess = random.nextInt(100)+1;

        Scanner sc = new Scanner(System.in);

        int userGuess = 0;
        int tries = 0;

        System.out.println("我已经想好了一个1到100的数字，你来猜猜看！");

        while (userGuess != numberToGuess) {
            System.out.print("请输入你猜的数字: ");
            userGuess = sc.nextInt();
            tries++;
            if (userGuess > numberToGuess) {
                System.out.println("太大了，再小一点！");
            } else if (userGuess < numberToGuess) {
                System.out.println("太小了，再大一点！");
            } else {
                System.out.println("恭喜你！猜对了！这个数字就是 " + numberToGuess);
                System.out.println("你总共猜了 " + tries + " 次。");
            }
        }
        sc.close();
    }
}
