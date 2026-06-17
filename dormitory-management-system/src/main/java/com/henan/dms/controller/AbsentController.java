package com.henan.dms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henan.dms.dto.AbsentSearchDto;
import com.henan.dms.entity.Absent;
import com.henan.dms.mapper.AbsentMapper;
import com.henan.dms.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/absent")
public class AbsentController {

    @Autowired
    private AbsentMapper absentMapper;

    @PostMapping("/getAllByCon")
    public Object getAllByCon(@RequestBody AbsentSearchDto dto) {
        Page<Absent> page = new Page<>(dto.getPageNo(), dto.getSize());
        QueryWrapper<Absent> wrapper = new QueryWrapper<>();
        if (dto.getStudentId() != null) {
            wrapper.eq("student_id", dto.getStudentId());
        }
        if (dto.getReason() != null && !dto.getReason().isEmpty()) {
            wrapper.like("reason", dto.getReason());
        }
        Page<Absent> absentPage = absentMapper.selectPage(page, wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("data", absentPage.getRecords());
        result.put("count", absentPage.getTotal());
        return ResultUtil.isSuccess(result);
    }

    @PostMapping("/add")
    public Object add(@RequestBody Absent absent) {
        return absentMapper.insert(absent) > 0 ? ResultUtil.isSuccess("添加成功", null) : ResultUtil.isFail(20001, "添加失败");
    }

    @PostMapping("/update")
    public Object update(@RequestBody Absent absent) {
        return absentMapper.updateById(absent) > 0 ? ResultUtil.isSuccess("更新成功", null) : ResultUtil.isFail(20001, "更新失败");
    }

    @PostMapping("/delById")
    public Object delById(@RequestParam Integer id) {
        return absentMapper.deleteById(id) > 0 ? ResultUtil.isSuccess("删除成功", null) : ResultUtil.isFail(20001, "删除失败");
    }
}