package com.github.devcat24.util.stream;


import java.util.Arrays;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"UnnecessaryLocalVariable", "WeakerAccess", "CodeBlock2Expr", "Convert2MethodRef", "SimplifyStreamApiCallChains"})
public class FunctionalInterfaceExample01 {

    public void example01(){

        /*  This example code moved to Test case -> FunctionalInterfaceExample01Test.class

        // 1. Supplier ( parameter -> x , return -> o,  get() interface)
        Supplier<Stream<String>> streamSupplier = () -> Stream.of("d2", "a2", "b1", "b3", "c", "a5")
                .filter(s -> s.startsWith("a"));
        //streamSupplier.get().forEach(System.out::println);
        String aggreatedStreamReult01 = streamSupplier.get().collect(Collectors.joining("-", "[", "]"));
        assertThat("[a2-a5]").isEqualToIgnoringCase(aggreatedStreamReult01);
        assertThat(streamSupplier.get().anyMatch(s -> true)).isTrue();
        assertThat(streamSupplier.get().noneMatch(s -> true)).isFalse();

        IntSupplier intSupplier01 = () -> {
            int num = (int) (Math.random() * 7) + 1;
            return num;
        };
        int intSupplierResult01 = intSupplier01.getAsInt();
        assertThat(intSupplierResult01).isGreaterThan(0);


        // 2. Consumer ( parameter -> o , return -> x, accept() interface)
        Consumer<String> streamConsumer01 = (p1) -> { System.out.println(p1); };
        streamConsumer01.accept("one object parameter");
        BiConsumer<String, String> streamBiConsumer01 = (p1, p2) -> { System.out.println(p1 + ", " + p2); };
        streamBiConsumer01.accept("first param", "second param");

        // 3. Function ( parameter -> o (only one param) , return -> o (Object) , apply() interface)
        //      => useful for type-conversion
        //        cf. UnaryOperator/BinaryOperator => cannot change return type (parameter & return should be same type of object)
        Function<String, Integer> streamFunction01 = p -> {
            return Integer.parseInt(p.substring(1)) * 10;
        };
        assertThat(30).isEqualTo(streamFunction01.apply("a3"));

        // 4. Predicate ( parameter -> o (only one param) , return -> o (boolean) , test() interface)
        Predicate<String> streamPredicate01 = p -> {
            return Integer.parseInt(p.substring(1)) > 0;
        };
        assertThat(streamPredicate01.test("C5")).isTrue();

        Address addr01 = Address.builder().zipcode(2000).city("Auckland").street("WEST").build();
        Address addr02 = Address.builder().zipcode(3000).city("Hamilton").street("EAST").build();
        Address addr03 = Address.builder().zipcode(4000).city("Tauranga").street("EAST").build();
        Address addr04 = Address.builder().zipcode(5000).city("Rotorua").street("CENTRAL").build();

        Predicate<Address> isEastPredicate01 = p -> { return p.getStreet().equalsIgnoreCase("EAST"); };
        Arrays.asList(addr01, addr02, addr03, addr04).stream().filter(isEastPredicate01).forEach(System.out::println);
        // using method-style Predicate
        Arrays.asList(addr01, addr02, addr03, addr04).stream().filter(isCentralPredicate01()).forEach(System.out::println);


        // 5. UnaryOperator ( parameter -> o (one), return -> o (Object) , apply() interface )
        //      => UnaryOperator/BinaryOperator => cannot change return type (parameter & return should be same type of object)
        //          cf. Function => parameter & return could be different object type => useful for type-conversion
        //UnaryOperator<String> str = (msg) -> { return msg.toUpperCase();} ;
        UnaryOperator<String> unaryOperator01 = (msg) -> msg.toUpperCase() ;
        assertThat("UNARYOPERATOR").isEqualTo(unaryOperator01.apply("UnaryOperator"));
        // 6. BinaryOperator
        //BinaryOperator<Integer> binaryOperator01 = (p1, p2) -> p1 + p2;
        BinaryOperator<Integer> binaryOperator01 = (p1, p2) -> {return p1 + p2; };
        assertThat(30).isEqualTo(binaryOperator01.apply(10, 20));



        // cf. Runnable interface -> functional style
        // Runnable r = () -> { System.out.println(" test "); };
        // r.run();
        */

    }

    /*  This example code moved to Test case
    public Predicate<Address> isCentralPredicate01 () {
        return p -> p.getStreet().equalsIgnoreCase("CENTRAL");
    }
    */
}
