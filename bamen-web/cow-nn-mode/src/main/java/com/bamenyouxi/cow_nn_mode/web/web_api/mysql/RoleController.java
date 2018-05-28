package com.bamenyouxi.cow_nn_mode.web.web_api.mysql;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Role;
import com.bamenyouxi.cow_nn_mode.service.mysql.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController  extends AbstractCrudController<Role, Long> {

    @Autowired
    private RoleService roleService;

    @Override
    public AbstractCrudService<Role, Long> getService() {
        return roleService;
    }

    @GetMapping("/getOfficeLimits/")
    public WebResult getOfficeLimits(){
        return WebResult.of(roleService.getOfficeLimits());
    }

    @GetMapping("/getSalesmanLimits/")
    public WebResult getSalesmanLimits(){
        return WebResult.of(roleService.getSalesmanLimits());
    }

    @GetMapping("/getAgent/")
    public WebResult getAgent(){
        return WebResult.of(roleService.getAgent());
    }

    @GetMapping("/getZdOneAgent/")
    public WebResult getZdOneAgent(){
        return WebResult.of(roleService.getZdOneAgent());
    }

}
