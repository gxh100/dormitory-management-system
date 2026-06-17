package com.henan.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("dormitory")
public class Dormitory {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer buildingId;
    private String name;
    private Integer type;
    private Integer available;
    private String telephone;
}