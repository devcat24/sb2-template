package com.github.devcat24.util.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamSample02 {
    public void steamSample02a(){
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

        /*Stream<int[]> fibonacci = Stream.iterate(new int[] {0, 1}, n -> new int[]{n[1], n[0]+n[1]});
        fibonacci.limit(0)
                .map(n -> n[0])
                .forEach(System.out::println);*/

        /*  // Read from file
        try(Stream <String> lines = Files.lines(Paths.get("contacts.csv"))){
            long numberOfNW = lines.map(line -> line.split(","))
                        .filter(values -> values[6]. contains("New Yortk"))
                        .count();
        }   catch(IOException ex){
            throw  new RuntimeException(ex);
        }*/

        List<StreamContact> contactList = new ArrayList<>();
        contactList.add(StreamContact.builder().name("Jane Button").email("jane@streamapi.org").city("New York").state("New York").age(23).build());
        contactList.add(StreamContact.builder().name("John Lloyd").email("john@streamapi.org").city("New York").state("New York").age(27).build());
        contactList.add(StreamContact.builder().name("Pedro Adkins").email("pedro@streamapi.org").city("Dallas").state("Texas").age(40).build());
        contactList.add(StreamContact.builder().name("Jeannette Fernandez").email("jeannette@streamapi.org").city("Houston").age(31).state("Texas").build());
        contactList.add(StreamContact.builder().name("Rick Morales").email("rick@streamapi.org").city("Austin").state("Texas").age(25).build());
        contactList.add(StreamContact.builder().name("Lynn Perez").email("@streamapi.org").city("San Francisco").state("California").age(51).build());
        contactList.add(StreamContact.builder().name("Donald Palmer").email("donald@streamapi.org").city("Milwaukee").state("Wisconsin").age(23).build());
        contactList.add(StreamContact.builder().name("Kara Hammond").email("kara@streamapi.org").city("Rochester").state("New York").age(35).build());
        contactList.add(StreamContact.builder().name("Jerry Osborne").email("jerry@streamapi.org").city("Akron").state("Ohio").age(42).build());
        contactList.add(StreamContact.builder().name("Sherry Phelps").email("sherry@streamapi.org").city("New Orleans").state("Louisiana").age(11).build());
        contactList.add(StreamContact.builder().name("Delbert Rose").email("delbert@streamapi.org").city("Wichita").state("Kansas").age(39).build());

        contactList.stream()
                .filter(c -> c.equalToState("New York"))
                .forEach(c -> System.out.println(c.getEmail() + "     <-  " + c));

        System.out.println("Stream Map: ");
        contactList.stream()
                .map(c -> c.getEmail())
                .forEach( email -> System.out.println("Email: " + email));

        System.out.println("Stream Map & Lamda expression: ");  // lamda expression -> "ClassName::MethodName"
        contactList.stream()
                .map(StreamContact::getEmail)
                .forEach(System.out::println);



        int sum = IntStream.rangeClosed(0, 100)   // parameter (1, 5) -> 1: accumulator(increasing value), 5 : max value(bigger than accumulator, or returns 0)
                .reduce(100, (left, right) -> left + right);  // parameter (100, (left, right) -> ...) -> 100: initial value
        System.out.println("Reduce: " + sum);

        sum = IntStream.rangeClosed(0, 100)
                .parallel()
                .reduce(0, (left, right) -> left + right);
        System.out.println("Reduce Parallel: " + sum);

        sum = IntStream.rangeClosed(1, 100)
                .reduce(0, Integer::sum); // -> methods using two parameters
        System.out.println("Reduce two parameter methods: " + sum);

        sum = contactList.stream().mapToInt(StreamContact::getAge).sum();
        System.out.println("Math Sum: " + sum);

        OptionalDouble avg = contactList.stream().mapToInt(StreamContact::getAge).average();
        if(avg.isPresent()) { System.out.println("Math Avg: " + avg); }

        OptionalInt min = contactList.stream().mapToInt(StreamContact::getAge).min();
        if(min.isPresent()) { System.out.println("Math Min: " + min); }

        OptionalInt max = contactList.stream().mapToInt(StreamContact::getAge).max();
        if(max.isPresent()) { System.out.println("Math Max: " + max); }

        IntSummaryStatistics summaryStatistics = contactList.stream()
                .mapToInt(StreamContact::getAge)
                .summaryStatistics();
        System.out.println("SummaryStatistics: " + summaryStatistics);
        System.out.println("    < " + summaryStatistics.getSum() + " > ");
        // summaryStatistics.getSum() / .getAverage() / .getMin() / .getMax() / .getCount()

        // convert to Iterator
        Iterator<StreamContact> streamContactIterator  = contactList.stream().iterator();

        // convert to Array
        Object[] objArray = contactList.stream().toArray();
        StreamContact[] streamContactArray = contactList.stream().toArray(StreamContact[]::new);

        List<String> cities = new ArrayList<>();
        /*cities = contactList.stream()
                .map(contact -> contact.getCity())
                .collect(() -> new ArrayList<>(), (list, city) -> list.add(city), (left, right) -> left.addAll(right));
        System.out.println("Collect case #1 : " + cities);*/
        /*cities = contactList.stream()
                .map(contact -> contact.getCity())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Collect case #2 : " + cities);*/
        cities = contactList.stream()
                .map(contact -> contact.getCity())
                .distinct()                     // ! -> remove duplication
                .collect(Collectors.toList());
        System.out.println("Collector : " + cities);

        // Collectors factory methods
        List<String> cityList = contactList.stream().map(c -> c.getCity()).collect(Collectors.toList());
        Set<String> citySet = contactList.stream().map(c -> c.getCity()).collect(Collectors.toSet());
        TreeSet<String> cityTreeSet = contactList.stream().map(c -> c.getCity()).collect(Collectors.toCollection(TreeSet::new));
        Map<String, String> cityMap01 = contactList.stream().collect(Collectors.toMap(StreamContact::getName, StreamContact::getCity));
        Map<String, Object> cityMap02 = contactList.stream().collect(Collectors.toMap(StreamContact::getName, Function.identity()));
        String allCityString = contactList.stream().map(StreamContact::getCity).collect(Collectors.joining());
        String allEmailString = contactList.stream().map(StreamContact::getEmail).collect(Collectors.joining("|"));


        // Grouping using Collectors
        Map<String, List<StreamContact>> contactsByState = new HashMap<>();
        contactsByState = contactList.stream().collect(Collectors.groupingBy(StreamContact::getState));
        System.out.println("Grouping by Collectors: " + contactsByState);

    }

}
