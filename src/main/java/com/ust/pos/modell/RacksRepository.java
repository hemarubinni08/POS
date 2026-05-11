package com.ust.pos.modell;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RacksRepository extends JpaRepository<Racks, Long> {

    Racks findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    List<Racks> findByStatusTrue();

}
