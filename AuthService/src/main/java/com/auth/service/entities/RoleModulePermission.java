package com.auth.service.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "role_module_permission")
@Builder
@Data
public class RoleModulePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Many permissions → One role
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    // Many permissions → One module
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    private Boolean canView = false;
    private Boolean canEdit = false;
    private Boolean canDelete = false;
    private Boolean canDownload = false;
}
