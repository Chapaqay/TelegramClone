package sardor.telegram.navigation

sealed class Screens(val route: String) {
    object Splash: Screens("splash_screen")
    object Home: Screens("home_screen")
    object Login: Screens("login_screen")
    object Regist: Screens("regist_screen")
    object Chat: Screens("chat_screen" + "/{key}")
}