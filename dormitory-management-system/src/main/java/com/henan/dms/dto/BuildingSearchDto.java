package com.henan.dms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingSearchDto {
    private String name;      // 楼宇名称模糊查询
    private Integer adminId;  // 管理员ID精确查询
    private Integer pageNo = 1;
    private Integer size = 5;
}