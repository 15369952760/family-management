package com.family.mgr;

import com.family.config.StringUtil;
import com.family.mgr.pojo.UserPojo;
import com.family.mgr.service.IUserPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: yuandh
 * @Description:
 * @Date: Created in 15:13 2019/3/21
 * @Modified By:
 */
@Controller
public class UserPojoController {

    @Autowired
    IUserPojoService userPojoService;

    @ResponseBody
    @RequestMapping(value = "/insert")
    public int insert(){
        UserPojo userPojo = new UserPojo();
        userPojo.setUsername("admin");
        userPojo.setPassword(StringUtil.md5("admin"));
        return userPojoService.insert(userPojo);
    }

}
