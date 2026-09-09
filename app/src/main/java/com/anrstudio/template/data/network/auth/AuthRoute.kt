package com.pegas.aura.aigirlfriend.soul.data.network.auth

object AuthRoute {
    const val GUEST_LOGIN = "/api/v1/auth/guest"
    const val REFRESH = "/api/v1/auth/refresh"
    const val ME = "/api/v1/auth/me"
    const val LOGOUT = "/api/v1/auth/logout"

    fun isGuest(path: String): Boolean = path.endsWith("/auth/guest")
    fun isRefresh(path: String): Boolean = path.endsWith("/auth/refresh")
    fun isLogout(path: String): Boolean = path.endsWith("/auth/logout")
    fun isAppConfig(path: String): Boolean = path.contains("/app-configs/by-package/")
}
