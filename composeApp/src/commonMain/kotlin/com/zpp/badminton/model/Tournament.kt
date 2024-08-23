package com.zpp.badminton.model

import kotlinx.datetime.LocalDateTime

/*
 * @Author :Chao Park
 * @Date : on 2024-08-23
 * @Describe :
 **/
data class Tournament(
  val title: String,
  val date: LocalDateTime,

) {
  var participants: List<Player> = emptyList()
  var games: List<Game> = emptyList()
  var state = State.NotStart
  var rules = Rules.twentyOne

  var tips = """
                        让分说明（可选）
    【15分制】男双遇混双让3分，男双遇女双让5分，混双遇女双让4分，同类队伍不让。
    【21分制】男双遇混双让5分，男双遇女双让8分，混双遇女双让6分，同类队伍不让。
    实际让分可以根据参赛选手水平灵活调整。
  """.trimIndent()

  enum class State(val text: String) {
    NotStart("未开始"), Playing("进行中"), Finished("已结束")
  }
}


data class Game(
  val first: Lineup,
  val second: Lineup,
)
