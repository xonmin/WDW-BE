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

        String username = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();

        User user = userRepository.findByUsername(username)
                .orElse(createUser(oAuth2UserInfo));
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

    User createUser(OAuth2UserInfo oAuth2UserInfo) {
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();
        User user = User.builder()
                .username(username)
                .provider(provider)
                .providerId(providerId)
                .email(email)
                .name(name)
                .build();
        return userRepository.save(user);
    }

}
