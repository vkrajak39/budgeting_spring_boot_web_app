package com.exavalu.budgetbakersb.services;
import com.exavalu.budgetbakersb.entity.Menu;
import com.exavalu.budgetbakersb.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> prepareMenus(Long userRoleId) {
        return menuRepository.findByUserRoleId(userRoleId);
    }
}
