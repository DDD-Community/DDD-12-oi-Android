package com.ddd.oi.presentation.login.contract

data class LoginState(
    val currentSocialLogin: SocialType? = null
)


enum class SocialType {
    NAVER,
    KAKAO,
    GOOGLE;
}