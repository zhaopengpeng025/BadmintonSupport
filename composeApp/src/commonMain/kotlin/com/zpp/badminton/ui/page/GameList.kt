package com.zpp.badminton.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zpp.badminton.extensions.dateString
import com.zpp.badminton.viewmodel.MainViewModel
import org.jetbrains.compose.resources.painterResource

/*
 * @Author :Chao Park
 * @Date : on 2024-08-23
 * @Describe :
 **/

@Composable
fun GameList(viewModel: MainViewModel, navigation: NavHostController) {

  Scaffold(
    topBar = {
      TitleBar("比赛列表", showBack = false)
    },
    bottomBar = {
      Button(onClick = {
        navigation.navigate(ParticipantsEditor)
      }, Modifier.fillMaxWidth()) {
        Text("创建比赛")
      }
    }
  ) {
    LazyColumn(Modifier.fillMaxSize()) {
      items(viewModel.tournaments.size) {
        for (tournament in viewModel.tournaments) {
          GameAbstract(tournament.title, tournament.state, tournament.date.dateString())
        }
        Divider()
      }
    }
  }
}

@Composable
fun TitleBar(title: String, navController: NavHostController? = null, showBack: Boolean = true) {
  Box(contentAlignment = Alignment.CenterStart) {
    Box(
      Modifier.fillMaxWidth().height(50.dp).background(MaterialTheme.colors.primary),
      contentAlignment = Alignment.Center
    ) {

      Text(
        text = title,
        fontSize = 20.sp,
        color = MaterialTheme.colors.onPrimary,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
      )
    }
    if (showBack) {
      Icon(
        Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = null,
        Modifier.clickable {
          navController?.popBackStack()
        }.padding(start = 20.dp),
        tint = Color.White
      )
    }
  }
}
