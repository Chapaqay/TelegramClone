package sardor.telegram.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import sardor.telegram.sceens.ChatScreen
import sardor.telegram.sceens.HomeScreen
import sardor.telegram.sceens.LoginScreen
import sardor.telegram.sceens.RegisterScreen
import sardor.telegram.sceens.SplashScreen

@Composable
fun NavGraph (){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route)
    {
        composable(route = Screens.Splash.route){
            SplashScreen(navController)
        }
        composable(route = Screens.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screens.Regist.route) {
            RegisterScreen(navController)
        }
        composable(route = Screens.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screens.Chat.route, arguments = listOf(
            navArgument("key"){type= NavType.StringType}
        )) {
            ChatScreen(navController, it.arguments?.getString("key")!!)
        }
    }
}