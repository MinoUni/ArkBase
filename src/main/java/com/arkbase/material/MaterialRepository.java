package com.arkbase.material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

  boolean existsByNameIgnoreCase(String name);

  @Query("select m.id from Material m where m.name = :name")
  Integer getIdByName(@Param("name") String name);
}
