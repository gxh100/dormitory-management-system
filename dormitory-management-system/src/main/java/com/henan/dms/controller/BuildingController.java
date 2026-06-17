package com.henan.dms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henan.dms.dto.BuildingSearchDto;
import com.henan.dms.entity.Building;
import com.henan.dms.mapper.BuildingMapper;
import com.henan.dms.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/building")
public class BuildingController {

    @Autowired
    private BuildingMapper buildingMapper;

    @PostMapping("/getAllByCon")
    public Object getAllByCon(@RequestBody BuildingSearchDto dto) {
        Page<Building> page = new Page<>(dto.getPageNo(), dto.getSize());
        QueryWrapper<Building> wrapper = new QueryWrapper<>();
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            wrapper.like("name", dto.getName());
        }
        if (dto.getAdminId() != null) {
            wrapper.eq("admin_id", dto.getAdminId());
        }
        Page<Building> buildingPage = buildingMapper.selectPage(page, wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("data", buildingPage.getRecords());
        result.put("count", buildingPage.getTotal());
        return ResultUtil.isSuccess(result);
    }

    @PostMapping("/add")
    public Object add(@RequestBody Building building) {
        int insert = buildingMapper.insert(building);
        return insert > 0 ? ResultUtil.isSuccess("添加成功", null) : ResultUtil.isFail(20001, "添加失败");
    }

    @PostMapping("/update")
    public Object update(@RequestBody Building building) {
        int update = buildingMapper.updateById(building);
        return update > 0 ? ResultUtil.isSuccess("更新成功", null) : ResultUtil.isFail(20001, "更新失败");
    }

    @PostMapping("/delById")
    public Object delById(@RequestParam Integer id) {
        int del = buildingMapper.deleteById(id);
        return del > 0 ? ResultUtil.isSuccess("删除成功", null) : ResultUtil.isFail(20001, "删除失败");
    }
}