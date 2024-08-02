package com.example.invoiceProject.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "PrivilegeDetail")
public class PrivilegeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long privilegeDetailId;
    @Column
    private Integer roleId;
    @Column
    private Integer privilegeId;


    public Long getPrivilegeDetailId() {
        return privilegeDetailId;
    }

    public void setPrivilegeDetailId(Long privilegeDetailId) {
        this.privilegeDetailId = privilegeDetailId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }
}
