package com.pinyougou.shop.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    //调用sellerService接口，并且对其进行set处理
    private SellerService sellerService;

    //set处理
    public void setSellerService(SellerService sellerService){
        this.sellerService=sellerService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.构建角色列表
        List<GrantedAuthority> list= new ArrayList<>();
        //2.添加角色
        list.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        //3.根据用户名查询实体类
        TbSeller tbSeller = sellerService.findOne(username);
            if (tbSeller!=null){
                if (tbSeller.getStatus().equals("1")){//商家已经审核通过
                    return new User(username,tbSeller.getPassword(),list);
                }else {
                    return null;
                }
            }else {
                return null;
            }
    }

}
