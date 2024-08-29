package com.zpp.badminton.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zpp.badminton.data.TournamentMemoryDataSource
import com.zpp.badminton.model.Game
import com.zpp.badminton.model.Gender
import com.zpp.badminton.model.GoalType
import com.zpp.badminton.model.Lineup
import com.zpp.badminton.model.LineupType
import com.zpp.badminton.model.Player
import com.zpp.badminton.model.Tournament
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

class CreateViewModel : ViewModel() {
  var title by mutableStateOf("")
  var dateMilliSecond by mutableStateOf(Clock.System.now().toEpochMilliseconds())
  var participantsInfo by mutableStateOf(
    """
    #接龙
    周三没抢到场地，本周活动时间改为周四2和3号场地，时间18-20点，限16人，本周玩法为A,B组对抗娱乐赛，玩家通过抽签选定A，B组，输的团队要给赢的团队买水，混双对男双，男双发球时不能偷后场，21分制，团队赛赢一场积两分，三局两胜，裁判自由担任，球若干，其余费用AA，先接龙后续微信小程序抽A,B组。                                    注:本次为私下约球赛，大家跟平时一样打即可，当然做好买水准备

    1. Tony
    2. Mary,1
    3. Anthony 
    4. LeBron
    5. Tracy,1
    6. McGrady,0
    7. Sar
    8. Jacob
    9. Tiago,1
    10. Taylor,1
    11. Messi
    12. Nadal
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


  fun createTournament(): Tournament {
    val tournament = Tournament(
      title, Instant.fromEpochMilliseconds(dateMilliSecond).toLocalDateTime(
        TimeZone.currentSystemDefault()
      )
    )
    participantsInfo.split("\n").filter { it.matches(Regex("^[0-9].*")) }
      .map { it.replace(Regex("\\d+\\."), "") }.map {
        it.split(",").let { arr ->
          if (arr.size > 1) {
            Player(arr[0], Gender.entries[arr[1].trim().toInt()])
          } else {
            Player(it)
          }
        }
      }.let { participants ->
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
              games.add(Game(groups[i], groups[i + 1]).also {
                assignScoreOffset(it)
              })
            }
          }
        }
      }.let {
        tournament.games = it
      }
    println("tournament games = ${tournament.games}")
    TournamentMemoryDataSource.addTournament(tournament)
    return tournament
  }

  /**
   * 根据两个队伍类型，分配让分
   * [LineupType.MenDouble] vs [LineupType.MixedDouble] 让 [maleVsMix] 分
   * [LineupType.MenDouble] vs [LineupType.WomenDouble] 让 [maleVsFemale] 分
   * [LineupType.MixedDouble] vs [LineupType.WomenDouble] 让 [mixVsFemale] 分
   * @param game Game
   */
  private fun assignScoreOffset(game: Game) {
    when (game.first.lineupType) {
      LineupType.MenDouble -> when (game.second.lineupType) {
        LineupType.MixedDouble -> game.second.score = maleVsMix
        LineupType.WomenDouble -> game.second.score = maleVsFemale
        else -> {}
      }
      LineupType.MixedDouble -> when (game.second.lineupType) {
        LineupType.MenDouble -> game.first.score = maleVsMix
        LineupType.WomenDouble -> game.second.score = mixVsFemale
        else -> {}
      }
      LineupType.WomenDouble -> when (game.second.lineupType) {
        LineupType.MenDouble -> game.first.score = maleVsFemale
        LineupType.MixedDouble -> game.first.score = mixVsFemale
        LineupType.WomenDouble -> {}
      }
    }
    println("game >>>>>>>>>>>>> = ${game.first.lineupType} vs ${game.second.lineupType} ${game.first.score}:${game.second.score}")
    println("game =============== ${game.first} vs ${game.second}")
  }


  private fun generateRandomPairs(
    usedPairs: MutableList<Lineup>, list: List<Player>
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
