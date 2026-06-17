package com.henan.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henan.dms.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}