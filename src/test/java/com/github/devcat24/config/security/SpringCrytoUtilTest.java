package com.github.devcat24.config.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringCrytoUtilTest {

    @Test
    public void encryptTest() throws Exception {
        AESUtil aesUtil = new AESUtil();
        String sourceString = "Test password # finally !";
        String encryptedString = aesUtil.encrypt(sourceString);
        System.out.println("encrypted : " + encryptedString);

        String decryptedString = aesUtil.decrypt("Rbqs5s2euiMPmK5WbYZjaKiy4HV1z057jQE0yDuE7Hg=");
        System.out.println("decrypted: " + decryptedString);

        assertEquals(sourceString, decryptedString);

        //System.out.println(":::: " + AESUtil.generateSaltFromString("New Salt Text", 32));
        assertTrue((AESUtil.generateSaltFromString("New Salt Text", 32)).length() == 32*2);
        // -> salt is converted to HEX String -> should be twice of original length !


    }
}
