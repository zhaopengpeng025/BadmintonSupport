package com.zpp.badminton.data

import com.zpp.badminton.model.Tournament

/*
 * @Author :Chao Park
 * @Date : on 2024-08-27
 * @Describe :
 **/
object TournamentMemoryDataSource {

    private val tournaments = mutableListOf<Tournament>()

    fun addTournament(tournament: Tournament) {
        tournaments.add(tournament)
    }

    fun getTournaments(): List<Tournament> {
        return tournaments
    }

    fun getTournament(title: String): Tournament? {
        return tournaments.find { it.title == title }
    }

}
