package com.github.devcat24.util.stream;

import lombok.Data;

import java.util.*;

@SuppressWarnings({"UnusedAssignment", "unused", "ConstantConditions"})
public class StreamSample04 {

    public Object steamSample04a(){
        // Optional is kind of special Stream type which contains maximum 1 element inside !
        // Optional is kind of special Stream type which contains maximum 1 element inside !
        // Optional is kind of special Stream type which contains maximum 1 element inside !


        StreamOrder order = new StreamOrder();
        Object rtnObj = new Object();
        int min02 = 60;

        // type #01
        rtnObj = Optional.ofNullable(order)
                        .map(StreamOrder::getMember)
                        .map(StreamMember::getAddress)
                        .map(StreamAddress::getCity)
                        .orElse("Seoul");

        // type #02
        // --- old style ---
        // if( order != null && order.getDate().getTime() > System.currentTimeMillis() - min02 * 1000){
        //    rtnObj = order.getMember();
        // }
        //
        // --- new style ---
        rtnObj = Optional.ofNullable(order)
                .filter( o -> o.getDate().getTime() > System.currentTimeMillis() - min02 * 1000)
                .map(StreamOrder::getMember);


        // type #03
        Map<Integer, String> cities = new HashMap<>();
        cities.put(1, "Seoul");
        cities.put(2, "Busan");
        cities.put(3, "Daejeon");
        String city03 = cities.get(4); // return null
        int length = 100;
        // --- old style ---
        //length = (city03 == null) ? 0 : city03.length();
        //
        // --- new style ---
        length = Optional.ofNullable(cities.get(4)).map(String::length).orElse(0);


        // type #04
        List<String> cities04 = Arrays.asList("Seoul", "Busan", "Daejeon");
        // --- old style ---
        String city04 = null;
        try {
            city04 = cities04.get(3);
        } catch (ArrayIndexOutOfBoundsException ao){
            throw new ArrayIndexOutOfBoundsException("No such city");
        }
        length = (city04 == null) ? 0 : city04.length();
        //
        // --- new style ---
        final int cityIdx = 4;
        length = getAsOptional(cities04, cityIdx).map(String::length).orElse(0);
        length = getAsOptional(cities04, cityIdx).map(String::length).orElseThrow(RuntimeException::new);
        length = getAsOptional(cities04, cityIdx).map(String::length).orElseThrow(() -> new RuntimeException("No matching city: " + cityIdx));
        //
        // --- new style : using ifPresent (invoke callback-like method as parameter)  cf. isPresent (use 'orElse' rather than 'isPresent') --
        getAsOptional(cities04, 3).ifPresent( c -> {
            int nameLength04a = c.length();
            System.out.println("city name length : " + nameLength04a);

        });


        // cf. isPresent vs orElse : https://dzone.com/articles/optional-ispresent-is-bad-for-you




        return null;
    }
    private static <T> Optional<T> getAsOptional(List<T> list, int index) {
        try {
            return Optional.of(list.get(index));
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Data
    private class StreamOrder {
        private Long id;
        private Date date;
        private StreamMember member;
    }

    @Data
    private class StreamMember {
        private Long id;
        private String name;
        private StreamAddress address;
    }

    @Data
    private class StreamAddress {
        private String stree;
        private String city;
        private String zipcode;
    }
}
