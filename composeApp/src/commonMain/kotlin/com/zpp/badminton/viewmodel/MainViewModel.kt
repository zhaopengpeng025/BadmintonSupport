package com.zpp.badminton.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zpp.badminton.model.Game
import com.zpp.badminton.model.Gender
import com.zpp.badminton.model.Lineup
import com.zpp.badminton.model.Player
import com.zpp.badminton.model.Tournament
import kotlinx.datetime.LocalDateTime
import kotlin.random.Random

/*
 * @Author :Chao Park
 * @Date : on 2024-08-22
 * @Describe :
 **/
class MainViewModel : ViewModel() {


  var tournaments = mutableStateListOf<Tournament>()


  fun findTournament(title: String): Tournament? {
    return tournaments.find { it.title == title }
  }
}
