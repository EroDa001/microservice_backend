package com.esisba.mscommandproducts.proxies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data @AllArgsConstructor
public class JwtDto {
    private Boolean isValid;
    private Integer userId;
    private Integer companyId;
}
