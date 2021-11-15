package com.example.jdbc.service;

import com.example.jdbc.entity.User;
import com.example.jdbc.repository.UserRepository;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    //注入UserRepository
    @Resource
    private UserRepository userRepository;

    //查询全部
    public List<Map<String, Object>> findAll(){
        return userRepository.findAll();
    }

    public void delete(Integer id){
        userRepository.delete(id);
    }

    public Integer insertGetKey(User user){
        return userRepository.insertGetKey(user);
    }

    public void upDate(User user){ userRepository.upDate(user); }

    public List<User> findByKeys(String keys){return userRepository.findByKeys(keys);}

    public List<User> findByPage(Integer pagesize,Integer pageindex){return userRepository.findByPage(pagesize,pageindex);}
//
//    public void upDate(String userName, String sex, String idNumber, String phone, Date birth, String address, Integer id) {
//        userRepository.upDate(userName,sex,idNumber,phone,birth,address,id);
//    }
}