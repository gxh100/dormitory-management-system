package com.henan.dms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsentSearchDto {
    private Integer studentId;
    private String reason;
    private Integer pageNo = 1;
    private Integer size = 5;
}