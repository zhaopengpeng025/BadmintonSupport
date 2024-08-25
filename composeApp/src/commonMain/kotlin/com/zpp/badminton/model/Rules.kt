package com.zpp.badminton.model

/*
 * @Author :Chao Park
 * @Date : on 2024-08-23
 * @Describe : 得分、让分规则
 * - 【15分制】男双遇混双让3分，男双遇女双让5分，混双遇女双让4分，同类队伍不让。
 * - 【21分制】男双遇混双让5分，男双遇女双让8分，混双遇女双让6分，同类队伍不让。
 * -  实际让分可以根据参赛选手水平灵活调整。
 **/


data class Rules(
  val topGoal: Int,
  val maleVsMix: Int,
  val maleVsFemale: Int,
  val mixVsFemale: Int
){
  companion object{
    val fifteen = Rules(15, 3, 5, 4)
    val twentyOne = Rules(21, 5, 8, 6)
  }
}

enum class GoalType(val rules: Rules) {
  Fifteen(Rules.fifteen), TwentyOne(Rules.twentyOne)
}
