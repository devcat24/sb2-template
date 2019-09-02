package com.github.devcat24.util.stream;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.*;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamExample01Test {

    @Test
    public void test01(){
        // StreamExample01 streamExample01 = new StreamExample01();
        // streamExample01.example01();

        // --- generating sample data ---
        Address addr01 = Address.builder().zipcode(2000).city("Auckland").street("WEST").build();
        Address addr02 = Address.builder().zipcode(3000).city("Hamilton").street("EAST").build();
        Address addr03 = Address.builder().zipcode(4000).city("Tauranga").street("EAST").build();
        Address addr04 = Address.builder().zipcode(5000).city("Rotorua").street("CENTRAL").build();

        User user01 = User.builder().id(10001L).name("John").address(addr01).interest(Arrays.asList("Sport", "IT")).build();
        User user02 = User.builder().id(10002L).name("Jane").address(addr02).interest(Arrays.asList("Movie", "IT")).build();
        User user03 = User.builder().id(10003L).name("David").address(addr03).interest(Arrays.asList("Pet", "Book", "Sport", "Health", "IT", "Finance", "Environment", "Person")).build();
        User user04 = User.builder().id(10004L).name("Steve").build();

        Order order00 = null;
        Order order01 = Order.builder().id(10001L).date(new Date()).user(user01).build();
        Order order02 = Order.builder().id(10002L).date(new Date()).user(user02).build();
        Order order03 = Order.builder().id(10003L).date(new Date()).user(user03).build();
        Order order04 = Order.builder().id(10004L).date(new Date()).user(user03).build();
        Order order05 = Order.builder().id(10005L).date(new Date()).user(user03).build();
        // --- generating sample data ---

        List<Order> orderList01 = Arrays.asList(order01, order02, order03, order04, order05);
        List<Order> orderList02 = Arrays.asList(order01, order02, order03, order00);


        Optional<Order> optionalObj0a = Optional.empty();
        Optional<Order> optionalObj0b = Optional.ofNullable(order00);
        // Optional<Order> optionalObj0c = Optional.of(order04);           // -> NPE
        Optional<Order> optionalObj0c = Optional.ofNullable(order04);      // <-- returns empty Optional instead of NPE
        //noinspection OptionalAssignedToNull
        assertThat(optionalObj0b != null).isTrue();
        //noinspection ConstantConditions
        assertThat(optionalObj0b.isPresent()).isFalse();
        assertThat("John").isEqualToIgnoringCase(optionalObj0b.orElse(order01).getUser().getName());
        // optionalObj0b.orElseThrow(() -> new RuntimeException("No matching order"));


        Object rtnObj = Optional.ofNullable(Order.builder().build())
                .map(Order::getUser)
                .map(User::getAddress)
                .map(Address::getCity)
                .orElse("Seoul");
        assertThat("SEOUL").isEqualToIgnoringCase(rtnObj.toString());

        rtnObj = Optional.ofNullable(order00)
                .map(Order::getUser)
                .map(User::getAddress)
                .map(Address::getCity)
                .orElse("No where");
        assertThat("No where").isEqualToIgnoringCase(rtnObj.toString());

        // "Pet", "Book", "Sport", "Health", "IT", "Finance", "Environment", "Person"
        user03.getInterest().stream()
                .findFirst()
                .ifPresent(String::toUpperCase);
        //.ifPresent(System.out::println);
        //.ifPresent( r -> System.out.println(r.toUpperCase()) );

        rtnObj = user03.getInterest().stream()
                .filter( s -> s.startsWith("Pe"))
                .map(String::toUpperCase)
                .sorted()
                //.forEach(System.out::println);
                .collect(Collectors.toList());
        assertThat("PERSON").isEqualToIgnoringCase((String) ((List) rtnObj).get(0));

        List<String> streetList = Arrays.asList(addr01, addr02, addr03, addr04).stream()
                .map(a -> a.getStreet())
                .distinct()
                .collect(Collectors.toList());
        assertThat(3).isEqualTo(streetList.size());       // WEST, EAST, CENTRAL


        rtnObj = user03.getInterest().stream()
                .filter( s -> s.startsWith("Fi"))
                .map(String::toUpperCase)
                .findFirst();
        assertThat("FINANCE").isEqualToIgnoringCase((String) ((Optional)rtnObj).orElse("None") );

        // IntStream Object
        assertThat(39).isEqualTo((Integer) IntStream.range(30, 40).max().orElse(10));
        assertThat(30).isEqualTo((Integer) IntStream.range(30, 40).findFirst().orElse(10));
        //assertThat(32).isEqualTo((Integer) IntStream.range(30, 40).findAny().orElse(10));

        rtnObj = IntStream.range(1, 4)
                .mapToObj(i -> "a" + i)
                //.forEach(System.out::println);
                .findFirst().orElse("a100");
        assertThat("a1").isEqualTo((String) rtnObj);

        rtnObj = IntStream.range(1, 10)
                .mapToObj( s -> {
                    if(s%2==0 && s%5==0){
                        return "- 2 * 5 -";
                    } else if(s%2 == 0) {
                        return "- 2 -";
                    } else if(s%5 == 0) {
                        return "- 5 -";
                    } else {
                        return s;
                    }
                }) //.forEach(System.out::println);
                .collect(Collectors.toList());

        // ArrayStream Object
        rtnObj = Arrays.stream(new int[] {1, 2, 3})
                .map(n -> n * 2 + 1)
                //.forEach(System.out::println);            // -> returns '3, 5, 7'
                //.max().ifPresent(System.out::println);    // ->  print '7'
                .average().orElse(10.0D);
        assertThat(5.0D).isEqualTo((Double) rtnObj);


        //Stream.of("a1", "a2", "a3")
        rtnObj = Arrays.asList("a1", "a2", "a3").stream()
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                //.ifPresent(System.out::println);
                .orElse(10);
        assertThat(3).isEqualTo((Integer) rtnObj);

        // using block statement in stream
        rtnObj = Stream.of("d2", "a2", "b1", "b3", "c1")
                .filter(s -> {
                    if(s.startsWith("b")) {
                        return true;
                    }   else return s.startsWith("c");
                })
                // .forEach(s -> {
                //    System.out.println("::>" + s);
                //    System.out.println("---");
                // });
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .map(s -> {
                    int manipulated = s * 10;
                    return manipulated;
                })
                .max()
                .orElse(10);
        assertThat(30).isEqualTo((Integer) rtnObj);



        // custom sorting using block statement
        // index for stream-loop
        Stream.of("d4", "a5", "b1", "b3", "c2")
                .sorted((s1, s2) -> {
                    String prev = s1.substring(1);
                    String next = s2.substring(1);
                    return prev.compareTo(next);
                })
                // .forEach(System.out::println);
                .collect(HashMap::new, (h, o) -> h.put(h.size(), o), (h, o) -> {})  // generate index for stream-loop
                .forEach((i, o) ->  {
                            // System.out.println("[idx: " + ((Integer)i+1) + "] " + o.toString());
                            // System.out.println(" ");
                        }
                );
        // map / sorted / filter    -> return Object
        // forEach                  -> no-return -> action (ex. System.out::println)


        // using Supplier
        Supplier<Stream<String>> streamSupplier =   () -> Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> s.startsWith("a"));
        assertThat(streamSupplier.get().anyMatch(s -> true)).isTrue();
        assertThat(streamSupplier.get().noneMatch(s -> true)).isFalse();


        int [] intMatch = {2, 4, 6};
        rtnObj = Arrays.stream(intMatch).allMatch( a -> a%2 == 0);
        assertThat((boolean) rtnObj).isTrue();
        rtnObj = Arrays.stream(intMatch).anyMatch( a -> a%3 == 0);
        assertThat((boolean) rtnObj).isTrue();
        rtnObj = Arrays.stream(intMatch).noneMatch( a -> a%5 == 0);
        assertThat((boolean) rtnObj).isTrue();


        rtnObj = orderList01.stream()
                .filter( o -> o.getUser().getAddress().getStreet().equalsIgnoreCase("east") )
                //.collect(Collectors.toSet())
                .collect(Collectors.toList());
        int numOfOrder = (int) ( (List<Order>) rtnObj ).stream().filter(o -> o.getUser().getName().equalsIgnoreCase("David")).count();
        assertThat(numOfOrder).isEqualTo(3);


        // Extract as Map with grouping key
        List<Address> addressList = Arrays.asList(addr01, addr02, addr03);
        Map<String, List<Address>> addressMap = addressList.stream()
                .collect(Collectors.groupingBy(a -> a.getStreet()));
        //addressMap.forEach((z, a) -> System.out.format("[%s] : %s \n", z, a ) );
        //addressMap.forEach((z, a) -> System.out.println("["+z+"] " + a ) );

        // List<String> cityList = new ArrayList<>();
        addressMap.forEach((z, a) -> {
            //String combinedZip = a.stream().map(s -> (s.getZipcode()).toString()).collect(Collectors.joining(" ", "[", "]" ));
            String combinedCity = a.stream().map(s -> s.getCity()).collect(Collectors.joining(", ", "[", "]" ));
            //System.out.println("[" + z + "] " + combinedCity );
            // cityList.add(combinedCity);
        });
        // cityList.stream().forEach(System.out::println);


        String allCityString =
                orderList01.stream().map(o -> o.getUser().getAddress().getCity()).collect(Collectors.joining(" and ", "All cities [", "] for delivery address"));
        //System.out.println(allCityString);

        Double addrZipAvg =  orderList01.stream().collect(Collectors.averagingInt(o -> o.getUser().getAddress().getZipcode()));
        assertThat(addrZipAvg).isEqualTo(3400.0D);

        IntSummaryStatistics zipStatistics = orderList01.stream().collect(Collectors.summarizingInt(o -> o.getUser().getAddress().getZipcode()));
        assertThat(5).isEqualTo(zipStatistics.getCount());


        Map<String, String> cityMap = addressList.stream().collect(Collectors.toMap(
                a -> a.getStreet(),                         // param1 -> Key of Map
                a -> a.getCity(),           // param2 -> Value of Map, but if there are multiple -> combind with following text
                (city1, city2)  -> city1 + ";" + city2      // param3 -> combine value with this rule
        ));
        // System.out.println(cityMap);  // -> prints '{WEST=Auckland, EAST=Hamilton;Tauranga}'
        assertThat("Auckland").isEqualToIgnoringCase( cityMap.get("WEST") );


        // using functional interface 'Collector'
        Collector<Address, StringJoiner, String> addressCityCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),                    // supplier
                        (prev, next) -> prev.add(next.getCity().toUpperCase()),  // accumulator
                        (joiner1, joiner2) -> joiner1.merge(joiner2),            // combiner
                        StringJoiner::toString);                                 // finisher
        String mergedCityList = addressList.stream().collect(addressCityCollector);
        assertThat("AUCKLAND | HAMILTON | TAURANGA").isEqualToIgnoringCase(mergedCityList);
        //System.out.println(addressList.stream().collect(addressCityCollector));


        // flatMap for merging 'Stream' data
        String[][] flatMapString01 = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};
        List<String> strMapList01 = Arrays.stream(flatMapString01).map(m -> String.join(",", m)).collect(Collectors.toList());
        // strMapList01.stream().forEach(s -> {   System.out.println(s); System.out.println("--"); }); // result -->  a,b -- c,d -- e,f --

        //noinspection Convert2MethodRef
        List<String> strFlatMapList01 = Arrays.stream(flatMapString01)
                .flatMap(fm -> Arrays.stream(fm))
                .collect(Collectors.toList());       // stream type should be provided for flatMap()
        // strFlatMapList01.stream().forEach(System.out::println);           // result --> a b c d e f


        List<String> strFlatMapList02 = Arrays.stream(flatMapString01)
                .filter(am -> am[0].startsWith("c"))
                .flatMap(fm -> {
                    List<String> flatMapSrc01 = new ArrayList<>();
                    Collections.addAll(flatMapSrc01, fm);
                    return flatMapSrc01.stream();
                }).collect(Collectors.toList());
        // strFlatMapList02.stream().forEach(System.out::println);           // result -->  c d

        Optional<Address> optionalReduced01 = addressList.stream()
                .reduce((prev, next) -> {
                    return prev.getZipcode() > next.getZipcode() ? prev : next;
                });
        assertThat(addr03).isEqualTo(optionalReduced01.orElse(addr02));

        Optional<String> optionalReduced02 = addressList.stream()
                .reduce((prev, next) -> {
                    // return 1 < 2 ? Address.builder().zipcode(10001).city("TopCity").street("Central").build() : prev;    // <-- can return new/modified object
                    return prev.getZipcode() > next.getZipcode() ? prev : next;
                })
                .map(Address::getCity);
        //assertThat("Tauranga").isEqualToIgnoringCase(optionalReduced02.get());
        assertThat("Tauranga").isEqualToIgnoringCase(optionalReduced02.orElse("Hamilton"));

        List<Address> srcAddrList01 = Arrays.asList(addr01, addr02, addr03, addr04);
        AtomicInteger idx01 = new AtomicInteger(0);
        List<Address> targetAddrList01 = Stream.of("d2", "a3", "b1", "a1", "c", "a4", "a2")
                .filter(s -> s.startsWith("a"))
                .sorted((prev, curr) -> Integer.compare(Integer.parseInt(prev.substring(1)), Integer.parseInt(curr.substring(1)))).map(s -> {
                    int idx = idx01.getAndIncrement();
                    // int rand = (int) (Math.random() * 2000);
                    // return Address.builder().zipcode(Integer.parseInt(s.substring(1)) * 1000).city("No Where: " + idx).street(rand + "").build();
                    return srcAddrList01.get( Integer.parseInt(s.substring(1))-1);
                }).collect(Collectors.toList())
                .stream().sorted(Comparator
                        .comparing(Address::getZipcode)
                        .thenComparing( s -> s.getStreet())
                        .reversed())
                //.peek(s -> { System.out.println(s.getCity());})  // -> peek(check without consume)
                .skip(1).limit(3)                                // -> select between {skip} and {limit}
                // .count()                                      // -> returns long type, cf. sum(), min(), max(), average()
                .collect(Collectors.toList());                   // -> cf. collect(Collectors.groupingBy(Address::getStreet))  -> returns as Map<$, $> type
        //idx01 = new AtomicInteger(0);  // -> compile error 'AtomicInteger -> final'
        AtomicInteger idx02 = new AtomicInteger(0);  // -> compile error 'AtomicInteger -> final'
        targetAddrList01.stream().forEach( s -> {
            // System.out.println(" << new AddrList >> : zipcode [" + s.getZipcode() + "]   city [" + s.getCity() + "]   street [" + s.getStreet() + "] (" + idx02.getAndIncrement() +")");
        });


        int[] oArr01 = {1, 2, 3};
        int[] newArr01 = new int[oArr01.length+1];
        newArr01 = Arrays.copyOf(oArr01, oArr01.length+1);
        newArr01[newArr01.length-1] = 999;

        String boxedArr01 = Arrays.stream(newArr01).boxed().map(s -> Integer.toString(s)).collect(Collectors.joining(", ", "[", "]")) ;
        assertThat("[1, 2, 3, 999]").isEqualToIgnoringCase( boxedArr01 );

        List<String> boxedArrList01 = Arrays.stream(newArr01).boxed().map(s -> Integer.toString(s)).collect(Collectors.toList()) ;
        assertThat(1).isEqualTo(boxedArrList01.stream().mapToInt(Integer::parseInt).filter( s -> s > 10).count());
        //boxedArrList01.stream().forEach(System.out::println);



        // List<Address> addrDBList01 = Stream.of(Arrays.asList(1001, 1002, 1003).stream()).map( addressJPARepository::findById ).collect(Collectors.toList()); // -> while passing values to JPARepository
        // Arrays.asList("a1", "b2", "x5").stream();  // -> convert array to stream
        // Arrays.asList("a1", "b2", "x5").stream().collect(Collectors.toList(); // -> convert stream to List  cf. collect(Collectors.groupingBy(Address::getStreet))
        // Arrays.asList("a1", "b2", "x5").stream(). (...) (...)  (..) .toArray[Address01[]::new];      // -> convert stream to array type

        // Parallel Stream
        //    ForkJoinPool commonPool = ForkJoinPool.commonPool();
        //    // System.out.println(commonPool.getParallelism());    // 3
        //    // JVM options: -Djava.util.concurrent.ForkJoinPool.common.parallelism=5
        //
        //    Arrays.asList("a1", "a2", "b1", "c2", "c1")
        //            .parallelStream()
        //            .filter(s -> {
        //                System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
        //                return true;
        //            })
        //            .map(s -> {
        //                System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
        //                return s.toUpperCase();
        //            })
        //            .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));
        //    // Parallel stream -> sort after processing  : only works for while sorting -> after sort, works parallel again
        //    Arrays.asList("a1", "a2", "b1", "c2", "c1")
        //            .parallelStream()
        //            .filter(s -> {                                                                              // -> parallel processin
        //                System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
        //                return true;
        //            })
        //            .map(s -> {                                                                                 // -> parallel processing
        //                System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
        //                return s.toUpperCase();
        //            })
        //            .sorted((s1, s2) -> {                                                                       // -> works serial here
        //                System.out.format("sort: %s <> %s [%s]\n", s1, s2, Thread.currentThread().getName());
        //                return s1.compareTo(s2);
        //            })                                                                                          // -> works parallel again !
        //            .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));
        //    Arrays.asList(addr01, addr02, addr03, addr04).parallelStream()
        //            .reduce(0,
        //                    (sum, a) -> {
        //                        System.out.println("accumulator: sum=" + sum + "; address=" + a.getCity() + " [" + Thread.currentThread().getName() + "]");
        //                        return sum = sum + a.getZipcode();
        //                    },
        //                    (sum1, sum2) -> {
        //                        System.out.println("combiner: sum1=" + sum1 + "; sum2=" + sum2 + " [" + Thread.currentThread().getName() + "]");
        //                        return sum1 + sum2;
        //                    }
        //            );



        // 1. creating stream
        int [] numbers = new int[] {1, 2, 3, 4, 5};
        IntStream intStream = Arrays.stream(numbers);
        IntStream parallelIntStream = intStream.parallel();

        Stream<String> stream = Stream.of("Using", "Stream", "API");
        Stream<String> parallelStringStream = stream.parallel();

        IntStream intStream01 = IntStream.range(1, 10);
        LongStream longStream01 = LongStream.rangeClosed(1, 10);

        //Infinite Stream Generation
        Stream<Double> random = Stream.generate(Math::random);
        Stream<Integer> intNum = Stream.iterate(0, n -> n + 1);

        Stream<Double> d01 = Stream.generate(Math::random);
        // Stream.generate(Math::random).forEach(System.out::println);  // infinite loop
        // System.out.println(d01.findFirst());

        // Stream<int[]> fibonacci = Stream.iterate(new int[] {0, 1}, n -> new int[]{n[1], n[0]+n[1]});
        // fibonacci.limit(0)
        //        .map(n -> n[0])
        //        .forEach(System.out::println);

        // Read from file
        // try(Stream <String> lines = Files.lines(Paths.get("contacts.csv"))){
        //    long numberOfNW = lines.map(line -> line.split(","))
        //                .filter(values -> values[6]. contains("New Yortk"))
        //                .count();
        // }   catch(IOException ex){
        //    throw  new RuntimeException(ex);
        // }
        int sum = IntStream.rangeClosed(0, 100)   // parameter (1, 5) -> 1: accumulator(increasing value), 5 : max value(bigger than accumulator, or returns 0)
                .reduce(100, (left, right) -> left + right);  // parameter (100, (left, right) -> ...) -> 100: initial value
        assertThat(5150).isEqualTo(sum);

        sum = IntStream.rangeClosed(0, 100)
                .parallel()
                .reduce(0, (left, right) -> left + right);
        assertThat(5050).isEqualTo(sum);

    }
}
