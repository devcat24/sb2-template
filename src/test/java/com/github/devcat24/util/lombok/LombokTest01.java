package com.github.devcat24.util.lombok;

import com.github.devcat24.util.stream.Address;
//import lombok.val;
//import lombok.var;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LombokTest01 {
    @Test
    public void lombokTest01(){
        Address addr01 = Address.builder().zipcode(2000).city("Auckland").street("WEST").build();
        Address addr02 = Address.builder().zipcode(3000).city("Hamilton").street("EAST").build();

        // lombok 'var' is main package (experimental until lombok 1.16.12) -> only can used for local variable & for loop -> not for class member field
        var addrList01 = new ArrayList<Address>();
        addrList01.add(addr01);
        addrList01.add(addr02);

        assertEquals("Auckland", addrList01.stream().filter(s -> s.getCity().equalsIgnoreCase("Auckland")).findFirst().get().getCity());
        for(Address a : addrList01) {
            // System.out.println("city_var -> : " + a.getCity());
        }

        // lombok 'val' works exactly like 'var', except the local variable is marked as final
        var addrList02 = addrList01;
        // addrList02 = addrList01;  -> 'val' is immutable, runtime error 'Error:(29, 9) java: cannot assign a value to final variable addrList02'
        //   cf. addrList02.add(Address.builder().zipcode(4000).city("Tauranga").street("NORTH").build());  -> no error (call by reference)

        assertEquals("Auckland", addrList02
                .stream().filter(s -> s.getCity().equalsIgnoreCase("Auckland")).findFirst().get().getCity());
        for(Address a : addrList02) {
            //System.out.println("city_val -> : " + a.getCity());
        }
    }
}
