package com.github.devcat24.util.stream;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamSample01 {

    public void streamSample01() {
        List<String> myList01 = Arrays.asList("a1", "a2", "b1", "c2", "c1");
        myList01.stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);  // a1

        Stream.of("a1", "a2", "a3")
                .findFirst()
                .ifPresent(System.out::println);  // a1

        IntStream.range(1, 4)
                .forEach(System.out::println);

        Arrays.stream(new int[]{1, 2, 3})
                .map(n -> 2 * n + 1)
                //.forEach(System.out::println);
                .average()
                .ifPresent(System.out::println);  // 5.0

        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);  // 3

        OptionalInt optionalInt01 = Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max();
        if (optionalInt01.isPresent()) {
            System.out.println(optionalInt01.getAsInt());
        }

        IntStream.range(1, 4)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

        Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

        System.out.println("------------>");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                });
        System.out.println("<------------");

        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .forEach(s -> System.out.println("forEach: " + s));

        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });

        System.out.println("------------------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("forEach: " + s));

        System.out.println("------------------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));

        int [] intMatch = { 2, 4, 6};
        boolean result = Arrays.stream(intMatch).allMatch( a -> a%2 == 0);
        System.out.println("All multiple of 2 :? " + result);
        result = Arrays.stream(intMatch).anyMatch( a -> a%3 == 0);
        System.out.println("Any multiple of 3 :? " + result);
        result = Arrays.stream(intMatch).noneMatch( a -> a%3 == 0);
        System.out.println("No multiple of 3 :? " + result);

        Stream.of("d2", "a2", "b1", "b3", "c")
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));

        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));

        Stream<String> stream =
                Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

        stream.anyMatch(s -> true);    // ok
        // stream.noneMatch(s -> true);   // exception -> stream has already been operated upon or closed


        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

        streamSupplier.get().anyMatch(s -> true);   // ok
        streamSupplier.get().noneMatch(s -> true);  // ok


        // Thread.getAllStackTraces().keySet().stream().filter( th -> String.valueOf(1000L).equals("****")).findAny()


    }
    public void streamSample02() {
        List<StreamDTO> dtoList = Arrays.asList(
                StreamDTO.builder().name("Max").age(18).build(),
                StreamDTO.builder().name("Peter").age(23).build(),
                StreamDTO.builder().name("Pamela").age(23).build(),
                StreamDTO.builder().name("David").age(12).build() );

        List<StreamDTO> filtered = dtoList.stream()
                                            .filter( p -> p.name.startsWith("P"))
                                            .collect(Collectors.toList());
        for(StreamDTO dto : filtered ){ System.out.println( dto.getName() + ":" + dto.getAge()); }

        Map<Integer, List<StreamDTO>> personByAge = dtoList.stream()
                .collect(Collectors.groupingBy(p -> p.age));
        personByAge.forEach((age, p) -> System.out.format("age %s: %s \n", age, p));

        Double averageAge = dtoList.stream()
                .collect(Collectors.averagingInt(p -> p.age));
        System.out.println("average age: " + averageAge);

        IntSummaryStatistics ageSummary = dtoList.stream()
                .collect(Collectors.summarizingInt(p -> p.age));
        System.out.println(ageSummary);

        String phrase = dtoList.stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In Germany [", "] are of legal age."));
        System.out.println(phrase);

        Map<Integer, String> map = dtoList.stream()
                .collect(Collectors.toMap(
                        p -> p.age,
                        p -> p.name,
                        (name1, name2) -> name1 + ";" + name2));
        System.out.println(map);

        Collector<StreamDTO, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "), // supplier
                        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
                        (j1, j2) -> j1.merge(j2),               // combiner
                        StringJoiner::toString);                // finisher

        String names = dtoList
                .stream()
                .collect(personNameCollector);

        System.out.println(names);  // MAX | PETER | PAMELA | DAVID


        List<StreamFoo> fooList = new ArrayList<>();
        IntStream
                .range(1,4)
                .forEach(i -> fooList.add(new StreamFoo("Foo" + i)));
        fooList.forEach( f -> IntStream
                                .range(1, 4)
                                .forEach(i -> f.bars.add(new StreamBar("Bar" + i + " <- " + f.getName()))));
        fooList.stream()
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));
        System.out.println("----------------------------------------");

        IntStream.range(1, 4)
                .mapToObj(i -> new StreamFoo("Foo" + i))
                .peek(f -> IntStream.range(1, 4)
                        .mapToObj(i -> new StreamBar ("Bar " + i + " <- " + f.name))
                        .forEach(f.bars::add))
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));

        Optional<StreamDTO> optionalReduced = dtoList.stream()
                .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2);
        optionalReduced.ifPresent(System.out::println);

        int maxValue = 0;
        OptionalInt optionalMaxInt = IntStream.range(4, 10).reduce( (a, b) -> a > b ? a : b) ;
        if(optionalMaxInt.isPresent()) { maxValue = optionalMaxInt.getAsInt(); }
        System.out.println("maxOptionalInt: " + maxValue);

        OptionalInt optionalRSum = IntStream.range(4, 10).reduce( (a, b) -> a+b);
        if(optionalRSum.isPresent()) { System.out.println("optionalRSum: " + optionalRSum.getAsInt()); }

        StreamDTO streamDTOReducedSum = dtoList.stream()
                .reduce(new StreamDTO("", 0), (p1, p2) -> {
                    p1.age += p2.age;
                    p1.name += p2.name;
                    return p1;
                });
        System.out.format("name=%s; age=%s \n", streamDTOReducedSum.name, streamDTOReducedSum.age);

        System.out.println("------------Parallel Streams -----------");
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(commonPool.getParallelism());    // 3
        // JVM options: -Djava.util.concurrent.ForkJoinPool.common.parallelism=5


        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));

        System.out.println("---------------------------------------------");
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n",
                            s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));

        System.out.println("---------------------------------------------");

        dtoList.parallelStream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s [%s]\n",
                                    sum, p, Thread.currentThread().getName());
                            return sum += p.age;
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
                                    sum1, sum2, Thread.currentThread().getName());
                            return sum1 + sum2;
                        });
    }

}
