package com.example.buntProject.repository;

import com.example.buntProject.entity.BuntFriendEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface BuntRepository extends CrudRepository<BuntFriendEntity, Long> {

    @Override
    ArrayList<BuntFriendEntity> findAll();
}
