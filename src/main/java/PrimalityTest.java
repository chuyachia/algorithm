import java.util.Random;

public class PrimalityTest {

    private static boolean trialDivision(int n) {
        if (n < 3) return n > 1;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i = i + 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }

        return true;
    }

    private static Random random = new Random();

    private static int powerModulo(int base, int count, int mod) {
        int accumlated = 1;
        while (count > 0) {
            if (count % 2 == 0) {
                count /= 2;
                base = (base * base) % mod;
            } else {
                count -= 1;
                accumlated = (accumlated * base) % mod;
            }
        }
        return accumlated;
    }

    private static boolean fermatTest(int n, int times) {
        while (times > 0) {
            int d = random.nextInt(n - 2);
            if (powerModulo(d, n, n) != d) return false;
            times--;
        }

        return true;


    }

    public static void main(String[] args) {
        System.out.println(PrimalityTest.trialDivision(1297));
        System.out.println(PrimalityTest.fermatTest(1297, 5));

        // Edge cases where fermatTest fail
        System.out.println(PrimalityTest.trialDivision(561));
        System.out.println(PrimalityTest.fermatTest(561,5 ));
    }
}
