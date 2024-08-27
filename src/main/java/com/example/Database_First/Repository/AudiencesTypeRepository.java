package com.example.Database_First.Repository;

import com.example.Database_First.Model.AudiencesTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudiencesTypeRepository extends JpaRepository<AudiencesTypeEntity, Long> {
}
