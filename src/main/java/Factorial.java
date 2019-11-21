public class Factorial {

    static int recursiveFactorial(int n) {
        if (n == 1) {
            return 1;
        }

        return n * recursiveFactorial(n - 1);
    }

    static int iterativeFactorial(int accumulated, int count) {
        if (count == 0) {
            return accumulated;
        }

        return iterativeFactorial(accumulated * count, count - 1);
    }

    public static void main(String[] args) {
        System.out.println(Factorial.recursiveFactorial(6)); // 720
        System.out.println(Factorial.iterativeFactorial(6, 6 - 1)); // 720
    }
}
