package com.esisba.msannoncements.Proxy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfosDto {
    private Integer userId;

    private List<Permission> permissions;

    private Integer companyId;
}