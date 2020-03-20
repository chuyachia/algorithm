package math;

public class Fibonacci {

    static int recursiveFibonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return recursiveFibonacci(n - 1) + recursiveFibonacci(n - 2);
    }

    static int iterativeFibonacci(int next, int current, int count) {
        if (count == 0) return current;
        return iterativeFibonacci(next + current, next, --count);
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        System.out.println(Fibonacci.iterativeFibonacci(1, 0, 40));
        long endTime = System.nanoTime();
        System.out.println(String.format("Iterative execution time : %s", endTime - startTime));

        startTime = System.nanoTime();
        System.out.println(Fibonacci.recursiveFibonacci(40));
        endTime = System.nanoTime();
        System.out.println(String.format("Recursive execution time : %s", endTime - startTime));
    }
}
