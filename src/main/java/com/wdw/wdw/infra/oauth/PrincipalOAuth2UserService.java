package com.wdw.wdw.infra.oauth;

import com.wdw.wdw.infra.jwt.PrincipalDetails;
import com.wdw.wdw.infra.oauth.provider.OAuth2UserInfo;
import com.wdw.wdw.infra.oauth.provider.UserInfoFactoryImpl;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.exception.InvalidProviderTypeException;
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
    private final UserRepository userRepository;
    private final UserInfoFactoryImpl userInfoFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        try {
            oAuth2UserInfo = userInfoFactory.makeUserInfo(userRequest, oAuth2User.getAttributes());
        } catch (InvalidProviderTypeException e) {
            log.error(e.getMessage());
        }

        String username = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();
        User user = userRepository.findByUsername(username)
                .orElse(createUser(oAuth2UserInfo));
        userRepository.save(user);
        return new PrincipalDetails(user, oAuth2User.getAttributes());

    }

    private User createUser(OAuth2UserInfo oAuth2UserInfo) {
        log.info("소셜 회원가입 진행");

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String roles = "ROLE_USER";

        User user = User.builder()
                .username(username)
                .roles(roles)
                .provider(provider)
                .providerId(providerId)
                .build();;
        return user;
    }

}
