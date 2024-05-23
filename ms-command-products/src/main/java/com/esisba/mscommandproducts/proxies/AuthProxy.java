package com.esisba.mscommandproducts.proxies;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-authentification")
public interface AuthProxy {

    @PostMapping("/api/v1/auth/verify-token")
    public JwtDto validateToken(@RequestParam String token);

    @GetMapping("/api/v1/user-infos")
    public UserInfosDto getUser(@RequestHeader("Authorization") String authorizationHeader);

}
