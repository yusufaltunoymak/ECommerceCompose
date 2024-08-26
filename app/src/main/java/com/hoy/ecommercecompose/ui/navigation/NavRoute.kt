package com.hoy.ecommercecompose.ui.navigation

enum class NavRoute(val route: String) {
    WELCOME("welcome"),
    LOGIN("login"),
    SIGNUP("signup"),
    RESET_PASSWORD("reset_password"),
    SEND_MAIL("send_mail"),
    HOME("home"),
    PRODUCT_DETAIL("product_detail?productId={productId}"),
    CATEGORY_SCREEN("category_screen?category={category}"),
    FAVORITE("favorite"),
    PROFILE("profile"),
    SEARCH("search"),
    CART("cart"),
    PAYMENT("payment"),
    ORDER("order"),
    NOTIFICATION("notification");

    fun withArgs(vararg args: String): String {
        return route.split("?").first() + args.joinToString(prefix = "?", separator = "&")
    }
}
