package com.auth.service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "role_master")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String roleName;

    private String description;

    private Boolean isActive = true;

    // One role → many users
    @OneToMany(mappedBy = "role")
    private List<AuthUser> users;

    // One role → many permissions
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<RoleModulePermission> permissions;
}
