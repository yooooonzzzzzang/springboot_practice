package org.zerock.springex.mapper;

import org.apache.ibatis.annotations.Select;

// 현재 시간을 처리
public interface TimeMapper {
    @Select("select not()") // ; 없음 주의
    String getTime();
}
