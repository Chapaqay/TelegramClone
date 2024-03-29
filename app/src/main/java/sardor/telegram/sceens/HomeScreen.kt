package sardor.telegram.sceens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import sardor.telegram.api.Firebase
import sardor.telegram.api.SharedHelper
import sardor.telegram.model.User
import sardor.telegram.navigation.Screens
import sardor.telegram.ui.theme.Secondary

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val currentUserKey = SharedHelper.getInstance(context).getKey()
    val users = remember { mutableStateListOf<User>() }

    val search = remember { mutableStateOf("") }


    Firebase.getAllUsers(context, search.value) { it2 ->
        users.clear()
        users.addAll(it2)
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Secondary)
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(search, navController)
        if (users.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Chats(navController, users)
        }
    }


}

@Composable
fun Chats(navController: NavController, chats: SnapshotStateList<User>) {
    LazyColumn(
        Modifier
            .background(White, RoundedCornerShape(6.dp))
    ) {
        items(chats.size) {
            LazyItem(it, chats[it], navController)
        }
    }
}

@Composable
fun LazyItem(index: Int, chat: User, navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable {
                navController.navigate("chat_screen/${chat.key!!}")
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Image(Icons.Rounded.Person, contentDescription = "", Modifier.height(50.dp))
        Column(
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chat.fullName!!,
                textAlign = TextAlign.Center,
                color = Black
            )
            Text(
                text = chat.username!!,
                textAlign = TextAlign.Center,
                color = Gray,
                fontSize = 12.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(search: MutableState<String>, navController: NavController) {
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            modifier = Modifier
                .padding(10.dp)
                .weight(1f),
            shape = RoundedCornerShape(6.dp),
            //shape Card viyuv ni vazifasini bajarmoqa
            value = search.value,
            onValueChange = { search.value = it },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            placeholder = { Text("Search") },
        )
        Button(
            onClick = {
                SharedHelper.getInstance(context).logOut()
                navController.navigate(Screens.Login.route)
            }, modifier = Modifier
                .size(60.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Red)

        ) {
            Image(
                Icons.Rounded.ExitToApp,
                contentDescription = "",
                Modifier.fillMaxSize()
            )
        }
    }
}


