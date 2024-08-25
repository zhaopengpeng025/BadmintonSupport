package com.zpp.badminton.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zpp.badminton.model.Game
import com.zpp.badminton.model.Gender
import com.zpp.badminton.model.GoalType
import com.zpp.badminton.model.Lineup
import com.zpp.badminton.model.Player
import com.zpp.badminton.model.Tournament
import kotlinx.datetime.LocalDateTime
import kotlin.random.Random

class CreateViewModel : ViewModel() {
  var title by mutableStateOf("")
  var tournamentDate by mutableStateOf("")
  var participantsInfo by mutableStateOf(
    """
    #接龙
    周三没抢到场地，本周活动时间改为周四2和3号场地，时间18-20点，限16人，本周玩法为A,B组对抗娱乐赛，玩家通过抽签选定A，B组，输的团队要给赢的团队买水，混双对男双，男双发球时不能偷后场，21分制，团队赛赢一场积两分，三局两胜，裁判自由担任，球若干，其余费用AA，先接龙后续微信小程序抽A,B组。                                    注:本次为私下约球赛，大家跟平时一样打即可，当然做好买水准备

    1. 懿安
    2. 张新,1
    3. 空指针 
    4. 陈扬
    5. 郑云霄,1
    6. 王半斤,0
    7. 剑平
    8. Jacob
    9. 抽本抽,1
    10. Vonyo,1
    11. 建文
    12. 张炜
  """.trimIndent()
  )
  var goalType by mutableStateOf(GoalType.TwentyOne)

  var maleVsMix by mutableStateOf(goalType.rules.maleVsMix)
  var maleVsFemale by mutableStateOf(goalType.rules.maleVsFemale)
  var mixVsFemale by mutableStateOf(goalType.rules.mixVsFemale)

  fun updateGoalOffset() {
    goalType.rules.let {
      maleVsMix = it.maleVsMix
      maleVsFemale = it.maleVsFemale
      mixVsFemale = it.mixVsFemale
    }
  }


  fun generateTournament(title: String, date: String): Tournament {
    val tournament = Tournament(title, LocalDateTime.parse(date))
    participantsInfo.split("\n")
      .filter { it.matches(Regex("^[0-9].*")) }
      .map { it.replace(Regex("\\d+\\."), "") }
      .map {
        it.split(",").let { arr ->
          if (arr.size > 1) {
            Player(arr[0], Gender.entries[arr[1].trim().toInt()])
          } else {
            Player(it)
          }
        }
      }
      .let { participants ->
        tournament.participants = participants
        val histories = mutableListOf<Lineup>()
        mutableListOf<List<Lineup>>().also { groups ->
          repeat(8) {
            val pairs = generateRandomPairs(histories, participants)
            groups.add(pairs)
          }
        }
      }.let { groups ->
        mutableListOf<Game>().also { games ->
          groups.forEach { groups ->
            groups.shuffled()
            for (i in groups.indices step 2) {
              games.add(Game(groups[i], groups[i + 1]))

            }
          }
        }
      }.let {
        tournament.games = it
      }
    return tournament
  }

  private fun generateRandomPairs(
    usedPairs: MutableList<Lineup>,
    list: List<Player>
  ): List<Lineup> {

    val pairs = mutableListOf<Lineup>()
    val availableItems = list.toMutableList()
    while (availableItems.size > 1) {
      val firstIndex = Random.nextInt(availableItems.size)
      val firstItem = availableItems.removeAt(firstIndex)
      val secondIndex = Random.nextInt(availableItems.size)
      val secondItem = availableItems.removeAt(secondIndex)

      val pair = Lineup(firstItem, secondItem)
      if (pair !in usedPairs) {
        pairs.add(pair)
      } else {
        availableItems.add(firstItem)
        availableItems.add(secondItem)
        if (availableItems.size == 2) {
          availableItems.addAll(pairs.map { it.first })
          availableItems.addAll(pairs.map { it.second })
          pairs.clear()
        }
      }
    }
    usedPairs.addAll(pairs)
    return pairs
  }
}
