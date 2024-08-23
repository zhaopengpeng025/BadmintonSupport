package com.zpp.badminton

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zpp.badminton.ui.page.GameList
import com.zpp.badminton.ui.page.CreateTournament
import com.zpp.badminton.ui.page.ParticipantsEditor
import com.zpp.badminton.ui.page.TournamentDetail
import com.zpp.badminton.viewmodel.MainViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  MaterialTheme {

    AppNavigation()
  }
}

@Composable
private fun AppNavigation() {
  val viewModel: MainViewModel = viewModel { MainViewModel() }
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = GameList) {
    composable(ParticipantsEditor) {
      CreateTournament(viewModel,navController)
    }
    composable("$TournamentDetail/{title}",) { entry ->
      val title = entry.arguments?.getString("title") ?: ""
      println("navigation title:$title")
      title.let { viewModel.tournaments.find { it.title == title } }
        ?.let { TournamentDetail(it,navController) }?: throw IllegalStateException("找不到相关比赛")
    }
    composable(GameList) {
      GameList(viewModel,navController)
    }
  }
}
