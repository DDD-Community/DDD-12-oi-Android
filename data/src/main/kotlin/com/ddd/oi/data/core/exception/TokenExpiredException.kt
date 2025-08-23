package com.ddd.oi.data.core.exception

class TokenExpiredException(
    message: String = "토큰이 만료되었습니다. 다시 로그인해주세요."
) : Exception(message)