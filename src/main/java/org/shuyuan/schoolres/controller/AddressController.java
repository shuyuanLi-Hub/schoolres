package org.shuyuan.schoolres.controller;

import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.service.address.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class AddressController
{
    private AddressService service;

    public AddressController(AddressService service)
    {
        this.service = service;
    }

    @PostMapping("/setAddress")
    @ResponseBody
    public void setAddress(String address, User user)
    {
        service.save(user, address);
    }
}
