package com.iweb.d0429_springboot_shop.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yang
 * @date 2023/4/29 12:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private Integer start;
    private final Integer count = 15;
    private Integer end;

    public void calculateLast(int total){
        if (total<=count){
            end = 0;
        }else {
            if (total%count==0){
                end = total-count;
            }else {
                end = total-total%count;
            }
        }
    }
}
