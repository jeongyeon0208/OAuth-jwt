package com.example.oauthjwt.service;

import com.example.oauthjwt.dto.CustomOauth2User;
import com.example.oauthjwt.dto.NaverResponse;
import com.example.oauthjwt.dto.OAuth2Response;
import com.example.oauthjwt.dto.UserDto;
import com.example.oauthjwt.entity.UserEntity;
import com.example.oauthjwt.repository.UserReporitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserReporitory userReporitory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User = " + oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("registrationId = " + registrationId);

        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else return null;

        //로그인 성공시 진행할 것
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        UserEntity existData = userReporitory.findByUsername(username);

        if (existData == null) {

            UserEntity userEntity = UserEntity.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .email(oAuth2Response.getEmail())
                    .role("ROLE_USER")
                    .build();

            userReporitory.save(userEntity);

            UserDto userDto = UserDto.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .email(oAuth2Response.getEmail())
                    .role("ROLE_USER")
                    .build();

            return new CustomOauth2User(userDto);
        }

        else {
            existData.updateName(oAuth2Response.getName());
            existData.updateEmail(oAuth2Response.getEmail());

            userReporitory.save(existData);

            UserDto userDto = UserDto.builder()
                    .username(existData.getUsername())
                    .name(oAuth2Response.getName())
                    .email(oAuth2Response.getEmail())
                    .role(existData.getRole())
                    .build();

            return new CustomOauth2User(userDto);
        }
    }
}
