package com.arkbase.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

  boolean existsByNameIgnoreCase(String name);

  @Query("select s.id from Skill s where s.name = :name")
  Integer getIdByName(@Param("name") String name);
}
