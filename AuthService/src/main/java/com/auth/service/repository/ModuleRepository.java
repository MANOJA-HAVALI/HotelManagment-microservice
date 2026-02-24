package com.auth.service.repository;

import com.auth.service.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module, String> {
    
    Optional<Module> findByModuleName(String moduleName);
    
    boolean existsByModuleName(String moduleName);
}
