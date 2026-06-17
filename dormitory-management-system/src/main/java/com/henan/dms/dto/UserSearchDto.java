package com.henan.dms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchDto {
    private String username;   // 模糊查询
    private Integer flag;      // 职位
    private Integer pageNo = 1;
    private Integer size = 5;
}