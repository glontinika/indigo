package indigo

import kotlin.system.exitProcess

lateinit var deck: MutableList<Card>

fun main() {
    deck = Deck().generateCards()
    deck.shuffled().toMutableList()
    askCommand()
}

fun askCommand(){
    do {
        println("Choose an action (reset, shuffle, get, exit):")
        val command = readln()
        when (command) {
            "reset" -> reset()
            "shuffle" -> shuffle()
            "get" -> getCard()
            "exit" -> {
                println("Bye")
                exitProcess(0)
            }
            else -> println("Wrong action.")
        }
    } while (command != "exit")
}

fun reset(){
    deck = Deck().generateCards()
    println("Card deck is reset.")
    askCommand()
}

fun shuffle() {
    deck.shuffled().toMutableList()
    println("Card deck is shuffled.")
    askCommand()
}

fun getCard(){
    println("Number of cards:")
    try {
        val nOfCards = readln().toInt()
        if (nOfCards !in 1..52) throw NumberFormatException()
        val selectedCards = deck.subList(0,nOfCards)
        println(selectedCards.joinToString(" ") {it.getCardTxt()})
        deck -= selectedCards.toSet()
    } catch (n: NumberFormatException){
        println("Invalid number of cards.")
    } catch (e: Exception){
        println("The remaining cards are insufficient to meet the request.")
    }
    askCommand()
}

class Deck(){
    private val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    private val suits = listOf("♦", "♥", "♠", "♣")
    fun generateCards() : MutableList<Card> {
        val theDeck = mutableListOf<Card>()
        for (rank in ranks.indices){
            for (suit in suits.indices){
                theDeck.add(Card(ranks[rank],suits[suit]))
            }
        }
        return theDeck.shuffled().toMutableList()
    }
}

data class Card (var rank: String, var suit: String){
    fun getCardTxt() = rank + suit
}