package org.zerock.springex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Arrays;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default //특정 필드 기본값 사용시 사용
    @Min(value = 1)
    @Positive
    private int page = 1;

    @Builder.Default
    @Min(value = 10)
    @Max(value = 100)
    @Positive
    private int size = 10;
    private String link;

    public int getSkip(){
        return (page -1 ) * 10;
    }


    /** 검색 / 필터링 */
    private String[] types;
    private String keyword;
    private boolean finished;
    private LocalDate from;
    private LocalDate to;

    public boolean checkType(String type){
        if(type == null || types.length == 0){
            return false;
        }
        // .anyMatch(types::equals): 스트림의 각 요소에 대해 주어진 조건(types::equals)이 하나라도 참이면 true를 반환
        return Arrays.stream(types).anyMatch(types::equals);
    }

    public String getLink(){
        StringBuilder builder = new StringBuilder();
        builder.append("page=" + this.page);
        builder.append("&size=" + this.size);

        if(finished){
            builder.append("&finished=on");
        }

        if(types != null && types.length > 0){
            for (int i=0; i< types.length; i++){
                builder.append("&types=" + types[i]);
            }
        }

        if (keyword != null){
            try {
                builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }

        if (from != null){
            builder.append("&from=" + from.toString());
        }

        if (to != null){
            builder.append("&to=" + to.toString());
        }
        return builder.toString();
    }
}
