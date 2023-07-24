package com.example.buntProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Entity
@Getter
@ToString
@NoArgsConstructor
public class BuntFriendEntity {
    @Column
    @GeneratedValue
    @Id
    public Long id;
    @Column
    public String name;
    @Column
    public String age;
    @Column
    public int weight;
}
