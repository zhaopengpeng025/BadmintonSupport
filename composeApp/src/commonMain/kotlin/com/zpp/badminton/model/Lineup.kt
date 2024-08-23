package com.zpp.badminton.model

/*
 * @Author :Chao Park
 * @Date : on 2024-08-22
 * @Describe :
 **/

data class Lineup(
  val first: Player,
  val second: Player
) {

  var score = 0

  override fun equals(other: Any?): Boolean {
    if (this === other) return true

    other as Lineup

    if (first != other.first && first != other.second) return false
    if (second != other.second && second != other.first) return false

    return true
  }

  override fun toString(): String = "($first, $second)"
  override fun hashCode(): Int {
    var result = first.hashCode() ?: 0
    result = 31 * result + (second.hashCode() ?: 0)
    return result
  }
}

data class Player(
  val name: String,
  var gender: Gender = Gender.Male
){
  fun isMale() = gender == Gender.Male
}

enum class Gender {
  Male, Female
}

