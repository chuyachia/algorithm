public class IntegralApproximation {

    @FunctionalInterface
    interface F {
        double apply(double x);
    }

    @FunctionalInterface
    interface T {
        double apply(double x, int count);
    }

    static double sum(F term, F next, double a, double b) {
        if (a > b) return 0;
        return term.apply(a) + sum(term, next, next.apply(a), b);
    }

    static double midPointApproximation(F f, double a, double b, double dx) {
        return dx * sum(f, (double x) -> x + dx, a + dx / 2, b);
    }

    static double nSumWithTransformation(F term, F next, T transformation, double a, int n) {
        double sum = 0;
        int count = 0;
        while (count <= n) {
            sum += transformation.apply(term.apply(a), count);
            a = next.apply(a);
            count++;
        }

        return sum;
    }

    static double simpsonRule(F f, double a, double b, int n) {
        double h = (b - a) / n;
        return (h / 3) * nSumWithTransformation(f, (double x) -> x + h,
                (double x, int c) -> {
                    if (c == 0 || c == n) return x;
                    if (c % 2 == 1) return 4 * x;
                    return 2 * x;
                }, a, n);
    }

    public static void main(String[] args) {
        // Sum from 2 to 5
        System.out.println(IntegralApproximation.sum((x) -> x, (x) -> x + 1, 2, 5));

        // Midpoint approximation of cube function
        System.out.println(IntegralApproximation.midPointApproximation((x) -> Math.pow(x, 3), 0, 1, 0.001));

        // Simpson's rule approximation of cube function
        System.out.println(IntegralApproximation.simpsonRule((x) -> Math.pow(x, 3), 0, 1, 20));
    }
}
