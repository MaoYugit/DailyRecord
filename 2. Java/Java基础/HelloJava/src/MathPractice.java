public class MathPractice {
    public static void main(String[] args) {
        System.out.println(Math.abs(-10));
        System.out.println(Math.ceil(3.14));
        System.out.println(Math.floor(3.14));
        System.out.println(Math.round(3.14));
        System.out.println(Math.round(3.5));
        System.out.println(Math.max(10, 20));
        System.out.println(Math.min(10, 20));
        System.out.println(Math.pow(2,3));
        System.out.println(Math.sqrt(16));
        System.out.println(Math.random());

        int randomNumber = (int) (Math.random() * 100) + 1;
        System.out.println(randomNumber);
    }
}
