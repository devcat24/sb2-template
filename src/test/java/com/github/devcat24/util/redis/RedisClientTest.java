package com.github.devcat24.util.redis;

/*
// temporary disabled as Embedded Redis Server has a startup issue on Windows

import com.github.devcat24.mvc.dto.fi.ItemGroupCode;
import com.github.devcat24.mvc.redis.repo.ItemGroupCodeRepo;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisClientTest {
    @Autowired
    ItemGroupCodeRepo itemGroupCodeRepo;

    @After
    public void tearDown(){
        itemGroupCodeRepo.deleteAll();
    }
    @Test
    public void redisCRUDTest01() throws Exception {

        String itemId = "I2019";
        ItemGroupCode newItemGroupCode = ItemGroupCode.builder().itemId(itemId).groupId(200).updatedAt(LocalDateTime.of(2019,8, 21, 0, 0)).build();
        // save
        itemGroupCodeRepo.save(newItemGroupCode);

        // query
        ItemGroupCode queriedItemGroupCode = itemGroupCodeRepo.findById(itemId).orElseThrow(() -> new Exception("No matching key found : " + itemId));
        // System.out.println(" [queried from Redis] itemId: " + queriedItemGroupCode.getItemId() + ",   groupId: " + queriedItemGroupCode.getGroupId() +", dateTime: " + queriedItemGroupCode.getUpdatedAt().toString());
        assertThat(queriedItemGroupCode.getGroupId()).isEqualTo(200);

        // update
        queriedItemGroupCode.refresh(100, LocalDateTime.of(2019, 8, 22, 0, 0));
        itemGroupCodeRepo.save(queriedItemGroupCode);

        // query
        ItemGroupCode queriedItemGroupCode02 = itemGroupCodeRepo.findById(itemId).orElseThrow(() -> new Exception("No matching key found : " + itemId));
        // System.out.println(" [queried after update] itemId: " + queriedItemGroupCode02.getItemId() + ",   groupId: " + queriedItemGroupCode02.getGroupId() +", dateTime: " + queriedItemGroupCode02.getUpdatedAt().toString());
        assertThat(queriedItemGroupCode.getGroupId()).isEqualTo(100);

        // delete
        itemGroupCodeRepo.delete(queriedItemGroupCode02);
        // ItemGroupCode queriedItemGroupCode03 = itemGroupCodeRepo.findById(itemId).orElseThrow(() -> new Exception("No matching key found : " + itemId));
        // System.out.println(" [queried after delete] itemId: " + queriedItemGroupCode03.getItemId() + ",   groupId: " + queriedItemGroupCode03.getGroupId() +", dateTime: " + queriedItemGroupCode03.getUpdatedAt().toString());

        Throwable thrown = catchThrowable(()-> { itemGroupCodeRepo.findById(itemId).orElseThrow(() -> new RuntimeException("No matching key found : " + itemId)); });
        assertThat(thrown).isInstanceOf(RuntimeException.class).hasMessageContaining("No matching key found");
    }
}
*/