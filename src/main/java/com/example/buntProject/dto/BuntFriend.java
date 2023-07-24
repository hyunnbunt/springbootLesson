package com.example.buntProject.dto;

import com.example.buntProject.entity.BuntFriendEntity;
import lombok.*;

@AllArgsConstructor
@ToString
@Setter
@NoArgsConstructor
public class BuntFriend {
    private String name;
    private String age;
    private int weight;

    public BuntFriendEntity toEntity() {
        return new BuntFriendEntity(null, this.name, this.age, this.weight);
    }
}
