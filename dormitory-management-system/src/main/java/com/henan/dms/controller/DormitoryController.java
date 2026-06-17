package com.henan.dms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henan.dms.dto.DormitorySearchDto;
import com.henan.dms.entity.Dormitory;
import com.henan.dms.mapper.DormitoryMapper;
import com.henan.dms.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dormitory")
public class DormitoryController {

    @Autowired
    private DormitoryMapper dormitoryMapper;

    @PostMapping("/getAllByCon")
    public Object getAllByCon(@RequestBody DormitorySearchDto dto) {
        Page<Dormitory> page = new Page<>(dto.getPageNo(), dto.getSize());
        QueryWrapper<Dormitory> wrapper = new QueryWrapper<>();
        if (dto.getBuildingId() != null) {
            wrapper.eq("building_id", dto.getBuildingId());
        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            wrapper.like("name", dto.getName());
        }
        Page<Dormitory> dormitoryPage = dormitoryMapper.selectPage(page, wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("data", dormitoryPage.getRecords());
        result.put("count", dormitoryPage.getTotal());
        return ResultUtil.isSuccess(result);
    }

    @PostMapping("/add")
    public Object add(@RequestBody Dormitory dormitory) {
        return dormitoryMapper.insert(dormitory) > 0 ? ResultUtil.isSuccess("添加成功", null) : ResultUtil.isFail(20001, "添加失败");
    }

    @PostMapping("/update")
    public Object update(@RequestBody Dormitory dormitory) {
        return dormitoryMapper.updateById(dormitory) > 0 ? ResultUtil.isSuccess("更新成功", null) : ResultUtil.isFail(20001, "更新失败");
    }

    @PostMapping("/delById")
    public Object delById(@RequestParam Integer id) {
        return dormitoryMapper.deleteById(id) > 0 ? ResultUtil.isSuccess("删除成功", null) : ResultUtil.isFail(20001, "删除失败");
    }
}