import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Sublists {
    public static <E> Stream<List<E>> of(List<E> list) {
        return Stream.concat(prefixes(list).flatMap(Sublists::suffixes), Stream.of(Collections.emptyList()));
    }

    private static <E> Stream<List<E>> prefixes(List<E> list) {
        return IntStream.rangeClosed(0, list.size())
                .mapToObj(end -> list.subList(0, end));
    }

    private static <E> Stream<List<E>> suffixes(List<E> list) {
        return IntStream.range(0, list.size())
                .mapToObj(start -> list.subList(start, list.size()));
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("a","b","c","d","e");
        Sublists.of(list)
                .forEach(System.out::println);
    }
}
