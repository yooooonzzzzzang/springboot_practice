package org.zerock.w2.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
//modelmapper 위해서 생성자 관련 어노테이션 추가
@AllArgsConstructor
@NoArgsConstructor
// 엔티티
public class TodoVO {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
}
