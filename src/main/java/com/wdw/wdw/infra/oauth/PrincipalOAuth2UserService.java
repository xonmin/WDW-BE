package com.wdw.wdw.infra.oauth;

<<<<<<< HEAD:src/main/java/com/wdw/wdw/infra/oauth/PrincipalOAuth2UserService.java

=======
>>>>>>> 6e31e2cf61d92c40f0bcc04b9e4e64da41f21bdd:src/main/java/com/wdw/wdw/config/oauth/PrincipalOAuth2UserService.java
import com.wdw.wdw.infra.jwt.PrincipalDetails;
import com.wdw.wdw.infra.oauth.provider.OAuth2UserInfo;
import com.wdw.wdw.infra.oauth.provider.UserInfoFactoryImpl;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.exception.InvalidProviderType;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {
    private UserRepository userRepository;
    private UserInfoFactoryImpl userInfoFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        try {
            oAuth2UserInfo = userInfoFactory.makeUserInfo(userRequest, oAuth2User.getAttributes());
        } catch (InvalidProviderType e) {
            String message = e.getMessage();
            System.out.println(message);
            e.printStackTrace();
        }

<<<<<<< HEAD:src/main/java/com/wdw/wdw/infra/oauth/PrincipalOAuth2UserService.java
        String username = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();

        User user = userRepository.findByUsername(username)
                .orElse(createUser(oAuth2UserInfo));
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

    User createUser(OAuth2UserInfo oAuth2UserInfo) {
        log.info("소셜 회원가입 진행");
=======
>>>>>>> 6e31e2cf61d92c40f0bcc04b9e4e64da41f21bdd:src/main/java/com/wdw/wdw/config/oauth/PrincipalOAuth2UserService.java
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String email = oAuth2UserInfo.getEmail();
        String roles = "ROLE_USER";

        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = User.builder()
                    .username(username)
                    .email(email)
                    .roles(roles)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(user);
        }
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

}
