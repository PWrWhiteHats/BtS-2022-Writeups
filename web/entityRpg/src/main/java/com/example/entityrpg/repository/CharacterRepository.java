package com.example.entityrpg.repository;

import com.example.entityrpg.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CharacterRepository extends JpaRepository<Character,Long> {
    Character findCharacterByName(String name);
    Character findCharacterByUUID(UUID UUID);
}
