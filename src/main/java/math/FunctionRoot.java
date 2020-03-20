package math;

public class FunctionRoot {
    // Newton's method for approximating differentiable function root
    @FunctionalInterface
    interface F {
        double apply(double x);
    }

    F function;
    private double guess;
    private double threshold = 0.001;
    private double dx = 0.001;

    public FunctionRoot(F function) {
        this(function, 1);
    }

    public FunctionRoot(F function, double guess) {
        this.function = function;
        this.guess = guess;
    }

    public double find() {
        double newGuess = improve(guess);
        while (!closeEnough(guess, newGuess)) {
            guess = newGuess;
            newGuess = improve(guess);
        }

        return newGuess;
    }

    private F derivative(F f, double dx) {
        return x -> (f.apply(x + dx) - f.apply(x)) / dx;
    }

    private double improve(double guess) {
        return guess - function.apply(guess) / derivative(function, dx).apply(guess);
    }

    private boolean closeEnough(double x, double y) {
        return Math.abs(x - y) < threshold;
    }

    public static void main(String[] args) {
        F function = x -> Math.cos(x) - x;

        FunctionRoot fr = new FunctionRoot(function);
        System.out.println(fr.find());

        F sqrt = x -> Math.pow(x, 2) - 2; // square root of 2

        FunctionRoot fr2 = new FunctionRoot(sqrt);
        System.out.println(fr2.find());
    }
}
