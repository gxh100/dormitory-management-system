package com.henan.dms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henan.dms.dto.UserSearchDto;
import com.henan.dms.entity.User;
import com.henan.dms.mapper.UserMapper;
import com.henan.dms.utils.JWTUtils;
import com.henan.dms.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    // 登录：硬编码验证 admin/admin
    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        if ("admin".equals(user.getUsername()) && "admin".equals(user.getPassword())) {
            String token = JWTUtils.getToken(1, "admin");   // userId 固定为 1
            return ResultUtil.isSuccess(token);
        } else {
            return ResultUtil.isFail(20001, "用户名或密码错误");
        }
    }

    // 退出登录（可简单返回成功）
    @PostMapping("/loginOut")
    public Object logout() {
        return ResultUtil.isSuccess("退出成功！", null);
    }

    // 获取用户信息（用于前端显示）
    @GetMapping("/getInfo")
    public Object getUserInfo(String token) {
        if (token == null || token.trim().isEmpty()) {
            return ResultUtil.isFail(20001, "请携带token！");
        }
        try {
            var claims = JWTUtils.parseToken(token);
            Object username = claims.get("username");
            String avatar = "http://localhost:8081/dms/images/avatar.png"; // 可替换为真实头像地址
            Map<String, Object> map = new HashMap<>();
            map.put("name", username);
            map.put("avatar", avatar);
            return ResultUtil.isSuccess(map);
        } catch (Exception e) {
            return ResultUtil.isFail(20001, "token无效或已过期");
        }
    }

    // 多条件分页查询用户（原有代码，保持不变）
    @PostMapping("/getAllUsersByCon")
    public Object getAllUsersByCon(@RequestBody UserSearchDto dto) {
        Page<User> page = new Page<>(dto.getPageNo(), dto.getSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
            wrapper.like("username", dto.getUsername());
        }
        if (dto.getFlag() != null) {
            wrapper.eq("flag", dto.getFlag());
        }
        Page<User> userPage = userMapper.selectPage(page, wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("data", userPage.getRecords());
        result.put("count", userPage.getTotal());
        return ResultUtil.isSuccess(result);
    }

    // 添加用户（原有代码）
    @PostMapping("/addUser")
    public Object addUser(@RequestBody User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        User exist = userMapper.selectOne(wrapper);
        if (exist != null) {
            return ResultUtil.isFail(20001, "用户名已存在");
        }
        int insert = userMapper.insert(user);
        return insert > 0 ? ResultUtil.isSuccess("添加成功", null) : ResultUtil.isFail(20001, "添加失败");
    }

    // 修改用户（原有代码）
    @PostMapping("/updateUser")
    public Object updateUser(@RequestBody User user) {
        String oldUserName = user.getOldUserName();
        String newUserName = user.getUsername();
        if (!oldUserName.equals(newUserName)) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", newUserName);
            User exist = userMapper.selectOne(wrapper);
            if (exist != null) {
                return ResultUtil.isFail(20001, "用户名已存在，请重新填写");
            }
        }
        int i = userMapper.updateById(user);
        return i == 1 ? ResultUtil.isSuccess("更新成功", null) : ResultUtil.isFail(20001, "更新失败");
    }

    // 删除用户（原有代码）
    @PostMapping("/delUserById")
    public Object delUserById(@RequestParam Integer userId) {
        int i = userMapper.deleteById(userId);
        return i > 0 ? ResultUtil.isSuccess("删除成功", null) : ResultUtil.isFail(20001, "删除失败");
    }
}