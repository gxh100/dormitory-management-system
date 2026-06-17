package com.henan.dms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DormitorySearchDto {
    private Integer buildingId;
    private String name;
    private Integer pageNo = 1;
    private Integer size = 5;
}