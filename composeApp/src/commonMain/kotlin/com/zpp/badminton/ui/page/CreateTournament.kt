package com.zpp.badminton.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zpp.badminton.extensions.currentDateTime
import com.zpp.badminton.extensions.dateString
import com.zpp.badminton.viewmodel.MainViewModel

/*
 * @Author :Chao Park
 * @Date : on 2024-08-22
 * @Describe :
 **/

@Composable
fun CreateTournament(
  viewModel: MainViewModel, navController: NavHostController, modifier: Modifier = Modifier
) {
  TitleBar("创建比赛", navController)
  Column(
    Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {

    var title by remember { mutableStateOf("") }

    var date by remember { mutableStateOf(currentDateTime().dateString()) }


    TextField(
      value = title,
      onValueChange = { title = it },
      label = { Text("输入比赛名称") },
      modifier = modifier.fillMaxWidth(0.8f).padding(bottom = 5.dp),
    )


    TextField(
      value = date,
      onValueChange = { date = it },
      label = { Text("输入比赛日期 格式 yyyy-MM-dd ") },
      modifier = modifier.fillMaxWidth(0.8f).padding(bottom = 10.dp),
    )

    TextField(
      value = viewModel.participantInfo,
      onValueChange = { viewModel.participantInfo = it },
      label = {
        Text("输入参与者")
      },
      modifier = modifier.fillMaxWidth(0.8f).fillMaxHeight(0.5f),
      placeholder = {
        Text(
          """
          输入参与者名单信息, 请按照以下格式输入参与者名单信息：
            1. 选手1 ,0
            2. 选手2 ,0
            3.选手3 ,1
            4.选手4
            *
            *
            *
           可直接粘贴微信接龙信息
           0 代表男性选手，1 代表女性选手, 默认为男性选手
        """.trimIndent()
        )
      })

    Button(
      onClick = {
        viewModel.generateTournament(title, "2024-08-08T14:33").let {
          navController.navigate("$TournamentDetail/$title")
        }
      },
      modifier = Modifier.padding(top = 100.dp).fillMaxWidth(0.8f)
    ) {
      Text("生成比赛")
    }
  }
}
