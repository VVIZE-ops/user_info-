package com.example.jdbc.controller;


import com.example.jdbc.entity.User;
import com.example.jdbc.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;

    /**
     *
     */
    @Controller
    public class HelloController {
        @RequestMapping(value = {"/","/index.html"})
        public String index(){
            return "index";
        }
    }
    /**
     * describe：主页，展示所有数据，并且有增删改的跳转按钮
     * @return
     */
    @GetMapping("/findStu")
    public List<Map<String, Object>> findStu(){
        List<Map<String, Object>> usertList = userService.findAll();
        return usertList;

    }

    /**
     * 插入数据
     * @param user
     * @return
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PostMapping ("/insertGetKey")
    public Object insertGetKey(User user){
       try {
           userService.insertGetKey(user);
           return "insert uccess";
       }catch (Exception e){
           return "Can not input null";
       }
    }

    /**
     * 删除
     * @param ids
     * @return
     */

     @PostMapping("/deletes")
     public Object delete(@RequestParam String ids){
     String[] id=ids.split(",");
     //        System.out.println(ids.length());
     try{
     for(int i=0;i<id.length;i++){
     userService.delete(Integer.valueOf(id[i]));}
     return "success";}
     catch (Exception e){
     return "Please select at least one";
        }
     }

    /**
     * 更新
     * @param user
     * @return
     */
    @PostMapping("/update")
    public Object update( User user) {
        try {
            userService.upDate(user);
            //model.addAttribute("user",new User());
            return "Update Success";
        }catch (Exception e){
            return "Can not input null";
        }
    }

    /**
     * 查询
     * @param keys
     * @return
     */
    @GetMapping("/findByKeys")
    public Object findByKeys(@RequestParam(name = "keys",required = false)  String keys){
        if((keys!=null&&!"".equals(keys))){
            List<User> usertList = userService.findByKeys(keys);
            return usertList;
        } else{
            return userService.findAll();}
    }

    @GetMapping("/paging")
    public Object paging(String pagesize, String pageindex){
        List<User> usertList = userService.findByPage(Integer.parseInt(pagesize),Integer.parseInt(pageindex));
        return  usertList;
    }
}