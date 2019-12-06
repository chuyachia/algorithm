public class MaximumPoint {

    @FunctionalInterface
    interface F {
        double apply(double x);
    }

    private double threshold = 0.001;
    private double q = (Math.sqrt(5) - 1) / 2; // Golden ratio
    private double p = Math.pow(q, 2);

    private double a, b, x, y;
    private F function;

    public MaximumPoint(double a, double b, F function) {
        if (a >= b) throw new RuntimeException("Interval start a must be smaller than interval end b");
        this.a = a;
        this.b = b;
        this.x = xPoint(a, b);
        this.y = yPoint(a, b);
        this.function = function;
    }

    public MaximumPoint(double a, double b, double threshold, F function) {
        this(a, b, function);
        this.threshold = threshold;
    }

    public double find() {
        while (!closeEnough(x, y)) {
            double xValue = function.apply(x);
            double yValue = function.apply(y);

            if (xValue > yValue) {
                b = y;
                y = x;
                x = xPoint(a, b);
            } else {
                a = x;
                x = y;
                y = yPoint(a, b);
            }
        }

        return x;
    }

    private double xPoint(double a, double b) {
        return a + p * (b - a);
    }

    private double yPoint(double a, double b) {
        return a + q * (b - a);
    }

    private boolean closeEnough(double x, double y) {
        return Math.abs(x - y) < threshold;
    }

    public static void main(String[] args) {
        F sinFunction = x -> Math.sin(x);

        MaximumPoint mp = new MaximumPoint(0, 3, sinFunction); // Max of pi between 0 and 3 is pi /2
        System.out.println(mp.find() * 2);  // pi value
    }
}
