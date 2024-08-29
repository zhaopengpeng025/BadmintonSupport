package com.zpp.badminton.model

/*
 * @Author :Chao Park
 * @Date : on 2024-08-22
 * @Describe :
 **/

data class Lineup(
  val first: Player,
  val second: Player,
  var score: Int = 0
) {


  var lineupType: LineupType = LineupType.MenDouble
    private set
    get() {
      return when {
        first.isMale() && second.isMale() -> LineupType.MenDouble
        first.isMale() && !second.isMale() || !first.isMale() && second.isMale() -> LineupType.MixedDouble
        else -> LineupType.WomenDouble
      }
    }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true

    other as Lineup

    if (first != other.first && first != other.second) return false
    if (second != other.second && second != other.first) return false

    return true
  }

  override fun toString(): String = "($first, $second $score)"
  override fun hashCode(): Int {
    var result = first.hashCode()
    result = 31 * result + second.hashCode()
    return result
  }
}

enum class LineupType {
  MenDouble, WomenDouble, MixedDouble
}

data class Player(
  val name: String,
  var gender: Gender = Gender.Male
) {
  fun isMale() = gender == Gender.Male
}

enum class Gender {
  Male, Female
}

