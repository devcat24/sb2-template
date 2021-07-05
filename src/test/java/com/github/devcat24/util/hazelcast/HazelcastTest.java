//package com.github.devcat24.util.hazelcast;
//
//import com.hazelcast.core.HazelcastInstance;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import static org.assertj.core.api.Java6Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import java.util.Map;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class HazelcastTest {
//    @Qualifier("hazelcastInstance")
//    @Autowired
//    private HazelcastInstance hazelcastInstance;
//    public void setHazelcastInstance(HazelcastInstance hazelcastInstance){
//        this.hazelcastInstance = hazelcastInstance;
//    }
//
//    private String mapName = "sampleMap";
//    @After
//    public void tearDown() {
//        Map<String, String> hMap = hazelcastInstance.getMap(mapName);
//        hMap.clear();
//    }
//
//    @SuppressWarnings("RedundantThrows")
//    @Test
//    public void hazelcastTest01() throws Exception {
//        String valueStr = "{ 'id': '2001', 'name': 'Kim'}";
//
//        // IMap iMap = hazelcastInstance.getMap(mapName);
//        Map<String, String> hzMap = hazelcastInstance.getMap("sampleMap");
//        // Get size of map
//        assertThat(hzMap.size() ).isEqualTo(0);
//
//        // insert new <key, value>
//        hzMap.put("key01", valueStr);
//        assertThat(hzMap.size() ).isEqualTo(1);
//        //System.out.println("key01: " + hzMap.get("key01"));
//        assertThat(hzMap.get("key01").equalsIgnoreCase(valueStr)).isTrue();
//
//        // update existing value
//        String valueStr2 = "{ 'id': '9001', 'name': 'Updated user'}";
//        hzMap.put("key01", valueStr2);
//        //System.out.println("key01: " + hzMap.get("key01") + ",  <-- : " + valueStr);
//        assertThat(hzMap.get("key01").equalsIgnoreCase(valueStr)).isFalse();
//
//        // remove existing value
//        hzMap.remove("key01");
//        assertThat(hzMap.size() ).isEqualTo(0);
//
//        assertThrows(RuntimeException.class, () -> {
//            //hzMap.get("key01");
//            getMatchingValueFromMap(hzMap, "key01");
//        });
//
//    }
//    public String getMatchingValueFromMap(Map<String, String> map, String key) throws RuntimeException{
//        String rtn = map.get(key);
//        if( rtn == null) throw new RuntimeException("No matching key found");
//        return rtn;
//    }
//}
