package com.henan.dms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSearchDto {
    private String name;
    private String gender;
    private String state;
    private Integer pageNo = 1;
    private Integer size = 5;
}