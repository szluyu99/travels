package com.yusael.travels.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true) // 链式调用
public class Result {
    private Boolean state = true;
    private String msg;
    private String userId;
}
