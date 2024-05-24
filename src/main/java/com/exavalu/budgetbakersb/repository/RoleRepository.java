package com.exavalu.budgetbakersb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exavalu.budgetbakersb.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r.roleName FROM Role r WHERE r.roleId = (SELECT u.userRoleId FROM User u WHERE u.userid = ?1)")
    String findRoleNameByUserId(int userId);
}
