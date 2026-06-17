package com.henan.dms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henan.dms.dto.StudentSearchDto;
import com.henan.dms.entity.Student;
import com.henan.dms.mapper.StudentMapper;
import com.henan.dms.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;

    @PostMapping("/getAllByCon")
    public Object getAllByCon(@RequestBody StudentSearchDto dto) {
        Page<Student> page = new Page<>(dto.getPageNo(), dto.getSize());
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            wrapper.like("name", dto.getName());
        }
        if (dto.getGender() != null && !dto.getGender().isEmpty()) {
            wrapper.eq("gender", dto.getGender());
        }
        if (dto.getState() != null && !dto.getState().isEmpty()) {
            wrapper.eq("state", dto.getState());
        }
        Page<Student> studentPage = studentMapper.selectPage(page, wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("data", studentPage.getRecords());
        result.put("count", studentPage.getTotal());
        return ResultUtil.isSuccess(result);
    }

    @PostMapping("/add")
    public Object add(@RequestBody Student student) {
        return studentMapper.insert(student) > 0 ? ResultUtil.isSuccess("添加成功", null) : ResultUtil.isFail(20001, "添加失败");
    }

    @PostMapping("/update")
    public Object update(@RequestBody Student student) {
        return studentMapper.updateById(student) > 0 ? ResultUtil.isSuccess("更新成功", null) : ResultUtil.isFail(20001, "更新失败");
    }

    @PostMapping("/delById")
    public Object delById(@RequestParam Integer id) {
        return studentMapper.deleteById(id) > 0 ? ResultUtil.isSuccess("删除成功", null) : ResultUtil.isFail(20001, "删除失败");
    }
}