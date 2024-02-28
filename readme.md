# OAuth2 with JWT

![OAuthJWT 모식도.png](OAuth2%20with%20JWT%2041a591000e224421b023e941dd220458/OAuthJWT_%25EB%25AA%25A8%25EC%258B%259D%25EB%258F%2584.png)

1. JWTFilter : 개발자가 커스텀해서 등록
    1. 모든 주소에서 동작
2. OAuth2AuthorizationRequestRedirectFilter
    1. /oauth2/authorization/서비스명
    2. /oauth2/authorization/naver 등
3. OAuth2LoginAuthenticationFilter : 외부 인증 서버에 설정할 redirect url
    1. /login/ouath2/code/서비스명
    2. /login/ouath2/code/naver 등

- OAuth2 클라이언트에서 구현해야 할 부분
    - OAuth2UserDetailService
    - OAuth2UserDetails
    - LoginSuccessHandelr

- JWT에서 구현해야 할 부분
    - JWTFilter
    - JWTUtil : JWT 발급 및 검증하는 클래스