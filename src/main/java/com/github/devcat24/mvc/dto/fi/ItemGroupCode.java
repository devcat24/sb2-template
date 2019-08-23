package com.github.devcat24.mvc.dto.fi;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@RedisHash("ItemGroupCode")
public class ItemGroupCode implements Serializable {
    @Id
    private String itemId;
    private Integer groupId;
    private LocalDateTime updatedAt;

    @Builder
    public ItemGroupCode(String itemId, Integer groupId, LocalDateTime updatedAt) {
        this.itemId = itemId;
        this.groupId = groupId;
        this.updatedAt = updatedAt;
    }
    public void refresh(Integer groupId, LocalDateTime updatedAt) {
        if(updatedAt.isAfter(this.updatedAt)){
            this.groupId = groupId;
            this.updatedAt = updatedAt;
        }
    }
}
