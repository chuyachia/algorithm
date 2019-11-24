public class Exponential {

    static int recursiveExponential(int base, int exponent) {
        if (exponent == 0) return 1;
        return base * recursiveExponential(base, exponent - 1);
    }

    static int iterativeExponential(int base, int product, int count) {
        if (count == 0) return product;
        return iterativeExponential(base, base * product, count - 1);
    }

    static int squaringExponential(int base, int exponent) {
        if (exponent == 0) return 1;
        if (exponent % 2 == 0) return (int) Math.pow(squaringExponential(base, exponent / 2), 2);
        return base * squaringExponential(base, (exponent - 1));
    }

    static int iterativeSquaringExponential(int base, int product, int count) {
        if (count == 0) return product;
        if (count % 2 == 0) return iterativeExponential((int) Math.pow(base, 2), product, count / 2);
        return iterativeExponential(base, base * product, count - 1);
    }


    public static void main(String[] args) {
        System.out.println(Exponential.recursiveExponential(2, 10));
        System.out.println(Exponential.iterativeExponential(2, 1, 10));
        System.out.println(Exponential.squaringExponential(2, 10));
        System.out.println(Exponential.iterativeSquaringExponential(2, 1, 10));
    }
}
