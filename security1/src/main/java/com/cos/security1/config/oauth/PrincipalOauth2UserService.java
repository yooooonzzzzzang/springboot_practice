package com.cos.security1.config.oauth;

import com.cos.security1.config.auth.PrincipleDetails;
import com.cos.security1.config.oauth.provider.GoogleUserInfo;
import com.cos.security1.config.oauth.provider.OAuth2UserInfo;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;
    // 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    // 함수 종료시 @AuthenticationPrincipal 어노테이션 만들어진다
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // sub: 구글 아이디 pk
        // 회원가입 정보
        // username = "google_{sub}
        // password = "암호화(겟인데어)"
        // email = "getAttributes.getemail()"
        // role = "ROLE_USER"
        // provider = "google"
        // providerId = sub
        System.out.println("clientRegistration: "+userRequest.getClientRegistration()); // registrationId 로 어떤 OAuth 로 로그인했는지 확인가능
        System.out.println("access_token: "+ userRequest.getAccessToken().getTokenValue());

        OAuth2User oauth2User = super.loadUser(userRequest);
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인을 완료 -> code리턴 (OAuth-Client라이브러리) ->AccessToken 요청
        // userRequest 정보 -> 회원 프로필을 받아야함(loadUser함수 호출) -> 회원프로필
        System.out.println("getAttributes: "+oauth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String username = provider + "_" + providerId; //google_101010010101
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String role = "ROLE_USER";


/*
        // 회원가입을 강제로 진행해볼 예정
        String provider = userRequest.getClientRegistration().getRegistrationId(); // google
        String providerId = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");
        String username = provider + "_" + providerId; //google_101010010101
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String role = "ROLE_USER";
*/
        User findUser = userRepository.findByUsername(username);
        if(findUser == null){
            findUser = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(findUser);
        }

        // 회원가입 강제로 진행(User)
        return new PrincipleDetails(findUser, oauth2User.getAttributes());
    }
}
