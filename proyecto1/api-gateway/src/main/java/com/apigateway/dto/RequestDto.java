package com.apigateway.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestDto {
    private String uri;
    private String method;
}
