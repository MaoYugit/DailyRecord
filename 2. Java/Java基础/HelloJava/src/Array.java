import java.util.Arrays;

public class Array {
    public static void main(String[] args) {
        int[] scores = new int[5];
        String[] names = new String[5];

        scores[0] = 10;
        scores[1] = 20;
        names[0] = "MaoYu";

        int[] topScores = {98, 95, 100};
        String[] pets = {"cat", "dog", "pig"};

        System.out.println(Arrays.toString(scores));
        System.out.println(Arrays.toString(names));
        System.out.println(Arrays.toString(topScores));
        System.out.println(Arrays.toString(pets));
    }
}
