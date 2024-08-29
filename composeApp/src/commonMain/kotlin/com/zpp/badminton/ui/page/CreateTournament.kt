package com.zpp.badminton.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zpp.badminton.extensions.dateString
import com.zpp.badminton.extensions.toDate
import com.zpp.badminton.model.GoalType
import com.zpp.badminton.viewmodel.CreateViewModel

/*
 * @Author :Chao Park
 * @Date : on 2024-08-22
 * @Describe :
 **/

@Composable
fun CreateTournament(
  viewModel: CreateViewModel, navController: NavHostController, modifier: Modifier = Modifier
) {
  Column {

    TitleBar("创建比赛", navController)
    Column(
      Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {

      var titleError by remember { mutableStateOf(viewModel.title.isEmpty() ) }
      Column(Modifier.fillMaxWidth().padding(10.dp)) {
        OutlinedTextField(
          value = viewModel.title,
          onValueChange = {
            viewModel.title = it
            titleError = it.isEmpty()
          },
          label = { Text("比赛标题") },
          modifier = modifier.fillMaxWidth(),
          isError = titleError,
          supportingText = {
            if (titleError) Text(
              "比赛标题不能为空",
              color = OutlinedTextFieldDefaults.colors().errorSupportingTextColor,
              fontSize = 12.sp
            )
          },
        )
        Spacer(Modifier.height(10.dp))

        TournamentDatePicker(viewModel, modifier)

      }

      OutlinedTextField(value = viewModel.participantsInfo,
        onValueChange = { viewModel.participantsInfo = it },
        label = { Text("参与者") },
        modifier = modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp).fillMaxHeight(0.5f),
        placeholder = { Text(RULE_TIPS.trimIndent()) })

      GoalSystem(viewModel)

      GoalOffsetList(viewModel)

      Button(
        onClick = {
          viewModel.createTournament().let {
            navController.navigate("$TournamentDetail/${viewModel.title}")
          }
        }, modifier = Modifier.padding(top = 50.dp, bottom = 20.dp).fillMaxWidth(0.8f),
        enabled = !titleError
      ) {
        Text("创建比赛")
      }
    }
  }
}

@Composable
private fun TournamentDatePicker(
  viewModel: CreateViewModel,
  modifier: Modifier
) {
  var showDatePicker by remember { mutableStateOf(false) }

  OutlinedTextField(
    value = viewModel.dateMilliSecond.toDate().dateString(),
    onValueChange = { /*viewModel.dateString = it*/ },
    label = { Text("比赛日期") },
    readOnly = true,
    trailingIcon = {
      IconButton(onClick = { showDatePicker = !showDatePicker }) {
        Icon(
          imageVector = Icons.Default.DateRange,
          contentDescription = "Select date"
        )
      }
    },
    modifier = modifier.fillMaxWidth(),
  )

  if (showDatePicker) {
    DatePickerModalInput(
      onDateSelected = { milliseconds ->
        milliseconds?.let {
          viewModel.dateMilliSecond = it
        }
      },
      onDismiss = { showDatePicker = false }
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
  onDateSelected: (Long?) -> Unit,
  onDismiss: () -> Unit
) {
  val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

  DatePickerDialog(
    onDismissRequest = onDismiss,
    confirmButton = {
      TextButton(onClick = {
        onDateSelected(datePickerState.selectedDateMillis)
        onDismiss()
      }) {
        Text("OK")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  ) {
    DatePicker(state = datePickerState)
  }
}


@Composable
private fun GoalOffsetList(viewModel: CreateViewModel) {
  Divider()
  GoalOffset("男双让女双", viewModel.maleVsFemale) {
    viewModel.maleVsFemale += it
  }
  Divider()
  GoalOffset("男双让混双", viewModel.maleVsMix) {
    viewModel.maleVsMix += it
  }
  Divider()
  GoalOffset("混双让女双", viewModel.mixVsFemale) {
    viewModel.mixVsFemale += it
  }
  Divider()
}

@Composable
private fun GoalSystem(viewModel: CreateViewModel) {
  Row(
    horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
  ) {
    CommonRadio("15分制",
      viewModel.goalType == GoalType.Fifteen,
      onClick = {
        viewModel.goalType = GoalType.Fifteen
        viewModel.updateGoalOffset()
      })
    CommonRadio("21分制",
      viewModel.goalType == GoalType.TwentyOne,
      onClick = {
        viewModel.goalType = GoalType.TwentyOne
        viewModel.updateGoalOffset()
      })
  }
}

@Composable
fun GoalOffset(label: String, offset: Int, changed: (Int) -> Unit) {
  Row(
    Modifier.fillMaxWidth().height(60.dp).padding(start = 10.dp, end = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(label)
    Spacer(Modifier.weight(1f))
    Row {
      Box(
        Modifier.size(40.dp).background(Color(0xFFEEEEEE)),
        contentAlignment = Alignment.Center
      ) {
        Text("+", Modifier.clickable { changed(1) }, fontSize = 25.sp)
      }
      Spacer(Modifier.size(5.dp))
      Box(
        Modifier.size(40.dp).background(Color(0xFFEEEEEE)),
        contentAlignment = Alignment.Center
      ) {
        Text("$offset", fontSize = 20.sp)
      }
      Spacer(Modifier.size(5.dp))
      Box(
        Modifier.size(40.dp).clickable { changed(-1) }.background(Color(0xFFEEEEEE)),
        contentAlignment = Alignment.Center
      ) {
        Text("-", fontSize = 35.sp)
      }
    }
  }
}

@Composable
fun CommonRadio(
  text: String, checked: Boolean, onClick: () -> Unit
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    RadioButton(
      selected = checked, onClick = { onClick() }, modifier = Modifier.padding(10.dp)
    )
    Text(text)
  }

}

private const val RULE_TIPS = """
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
        """
