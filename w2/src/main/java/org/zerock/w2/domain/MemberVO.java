package org.zerock.w2.domain;

import lombok.*;

@Getter
@Builder
@ToString
//modelmapper 위해서 생성자 관련 어노테이션 추가
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String mid;
    private String mpw;
    private String mname;
    private String uuid;
}
