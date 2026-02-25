package com.auth.service.repository;

import com.auth.service.entities.RoleModulePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleModulePermissionRepository extends JpaRepository<RoleModulePermission, String> {
    
    List<RoleModulePermission> findByRoleId(String roleId);
    
    List<RoleModulePermission> findByModuleId(String moduleId);
    
    Optional<RoleModulePermission> findByRoleIdAndModuleId(String roleId, String moduleId);
    
    void deleteByRoleIdAndModuleId(String roleId, String moduleId);
}
