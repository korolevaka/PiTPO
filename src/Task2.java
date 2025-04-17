import java.math.BigInteger;
import java.util.*;

public class Task2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<BigInteger> fibs = new ArrayList<>();
        fibs.add(BigInteger.ONE);
        fibs.add(BigInteger.valueOf(2));

        BigInteger limit = new BigInteger("10").pow(100);
        while (true) {
            int size = fibs.size();
            BigInteger next = fibs.get(size - 1).add(fibs.get(size - 2));
            if (next.compareTo(limit) > 0)
                break;
            fibs.add(next);
        }

        while (sc.hasNext()) {
            String aStr = sc.next();
            String bStr = sc.next();
            if (aStr.equals("0") && bStr.equals("0")) break;

            BigInteger a = new BigInteger(aStr);
            BigInteger b = new BigInteger(bStr);

            int count = 0;
            for (BigInteger f : fibs) {
                if (f.compareTo(a) < 0) continue;
                if (f.compareTo(b) > 0) break;
                count++;
            }
            System.out.println(count);
        }
    }
}
