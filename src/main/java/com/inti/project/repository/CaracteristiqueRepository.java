package com.inti.project.repository;

import com.inti.project.domain.Caracteristique;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Caracteristique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaracteristiqueRepository extends JpaRepository<Caracteristique, Long> {

}
