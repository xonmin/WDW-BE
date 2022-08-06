package com.wdw.wdw.config.oauth;

import com.wdw.wdw.config.auth.PrincipalDetails;
import com.wdw.wdw.config.oauth.provider.GoogleUserInfo;
import com.wdw.wdw.config.oauth.provider.OAuth2UserInfo;
import com.wdw.wdw.config.oauth.provider.UserInfoFactoryImpl;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.exception.InvalidProviderType;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

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
