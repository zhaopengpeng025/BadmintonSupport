package com.zpp.badminton.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zpp.badminton.extensions.dateString
import com.zpp.badminton.model.Game
import com.zpp.badminton.model.Lineup
import com.zpp.badminton.model.Player
import com.zpp.badminton.model.Tournament
import com.zpp.badminton.ui.theme.FemalePlayer
import com.zpp.badminton.ui.theme.MalePlayer

/*
 * @Author :Chao Park
 * @Date : on 2024-08-23
 * @Describe :
 **/
@Composable
fun TournamentDetail(tournament: Tournament,navController: NavHostController) {
  TitleBar("比赛详情",navController)
  LazyColumn(Modifier.fillMaxSize().padding(top = 50.dp)) {
    item {
      Header(
        tournament.title,
        tournament.date.dateString(),
        tournament.state,
        tournament.participants,
        tournament.tips
      )
    }
    itemsIndexed(tournament.games) { index, game ->
      if (index == 0) {
        Divider(Modifier.height(5.dp))
      }
      GameItem(game)
      Divider(Modifier.height(5.dp))
    }
  }
}

@Composable
private fun GameItem(game: Game) {
  Row(Modifier.fillMaxWidth().height(100.dp), horizontalArrangement = Arrangement.SpaceBetween) {
    Lineup(game.first)
    ScoreBoard(game.first.score, game.second.score)
    Lineup(game.second)
  }
}

@Composable
private fun Lineup(lineup: Lineup) {
  Column(
    modifier = Modifier.width(100.dp).fillMaxHeight(),
    verticalArrangement = Arrangement.SpaceEvenly,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Text(
      text = lineup.first.name,
      Modifier.background(if (lineup.first.isMale()) MalePlayer else FemalePlayer).padding(5.dp),
      color = Color.White
    )

    Text(
      text = lineup.second.name,
      Modifier.background(if (lineup.second.isMale()) MalePlayer else FemalePlayer).padding(5.dp),
      color = Color.White
    )

  }
}

@Composable
fun ScoreBoard(score1: Int, score2: Int, modifier: Modifier = Modifier) {
  Row(
    modifier.fillMaxWidth(0.5f).fillMaxHeight(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(score1.toString(), fontSize = 50.sp)
    Text("vs", fontSize = 40.sp)
    Text(score2.toString(), fontSize = 50.sp)
  }
}


@Composable
private fun Header(
  title: String,
  date: String,
  state: Tournament.State,
  participants: List<Player>,
  tips: String
) {
  Column(Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)) {
    GameAbstract(title, state, date)
    Divider()
    ParticipantsFlow(participants)
    Divider()
    Text(tips)
  }

}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
private fun ParticipantsFlow(participants: List<Player>) {
  FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    participants.forEach { player ->
      FilterChip(false, onClick = {}) {
        Text(player.name)
      }
    }
  }
}

@Composable
fun GameAbstract(
  title: String,
  state: Tournament.State,
  date: String
) {
  Column(Modifier.fillMaxWidth()) {
    Row(
      Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 5.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = title, fontWeight = FontWeight.Bold)

      Text(text = state.text)
    }
    Text(text = date, Modifier.padding(top = 10.dp, bottom = 10.dp))
  }
}
