package com.mymusic.jwt.service;

import com.mymusic.jwt.model.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component("rabcService")
@Slf4j
public class MyRBACService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 判断某用户是否具有该request资源的访问权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication){

        Object principal = authentication.getPrincipal();

        if(principal instanceof UserDetails){

            UserDetails userDetails = ((UserDetails)principal);
//            List<GrantedAuthority> authorityList =
//                    AuthorityUtils.commaSeparatedStringToAuthorityList(request.getRequestURI());
            SimpleGrantedAuthority simpleGrantedAuthority =
                    new SimpleGrantedAuthority(request.getRequestURI()) ;

                    // TODO 这是为了测试进行输出的信息
                     log.info("最新的接口路径信息"+simpleGrantedAuthority.toString());
            return userDetails.getAuthorities().contains(simpleGrantedAuthority)
                    || jwtProperties.getDevOpeningURI().contains(request.getRequestURI());
        }

        return false;
    }


}
