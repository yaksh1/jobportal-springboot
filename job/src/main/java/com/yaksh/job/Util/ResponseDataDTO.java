package com.yaksh.job.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDataDTO {
private String message;
private boolean status;
private Object data;

    public ResponseDataDTO(String message, boolean status) {
        this.message = message;
        this.status = status;
    }
}
