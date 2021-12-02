import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Lambda {
    public static void main(String[] args) {
        String[] array = {"Apple", "Orange", "Banana", "Lemon"};
        Arrays.sort(array, String::compareTo);
        System.out.println(Arrays.toString(array));

        Stream<Integer> result = Stream.generate(new NatualSupplier()).map(x->x*x);
        result.limit(20).forEach(System.out::print);

        System.out.println(new Fibonacci().fibonacci(5));

        LongStream fibonacci = Stream.generate(new Fibonacci()).mapToLong(Long::longValue).limit(10);
        fibonacci.forEach(System.out::println);
    }

    static class NatualSupplier implements Supplier<Integer> {
        int n = 0;
        @Override
        public Integer get() {
            n++;
            return n;
        }
    }

    static class Fibonacci implements Supplier<Long> {
        int n = 0;
        @Override
        public Long get() {
            n++;
            return fibonacci(n);
        }

        public Long fibonacci(int n) {
            if (n == 1 || n == 2) return 1L;
            else return fibonacci(n-1)+fibonacci(n-2);
        }
    }
}
