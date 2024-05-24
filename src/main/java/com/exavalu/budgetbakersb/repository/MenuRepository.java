package com.exavalu.budgetbakersb.repository;

import com.exavalu.budgetbakersb.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m INNER JOIN MenuRole mr ON m.menuId = mr.id.menuId WHERE mr.id.roleId = :userRoleId")
	List<Menu> findByUserRoleId(@Param("userRoleId") Long userRoleId);
}
