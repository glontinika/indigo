package indigo

import kotlin.system.exitProcess

val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
val suits = listOf("♦", "♥", "♠", "♣")
val winningRank = listOf("A", "10", "J", "Q", "K")

val deck = Deck()
var tableCards = TableCards()
var computerCards = PlayerCards()
var userCards = PlayerCards()

fun main() {
    deck.createDeck()
    deck.tossCardsToTable()
    deck.tossCardsToPlayer(userCards)
    deck.tossCardsToPlayer(computerCards)

    println("Indigo Card Game")
    var command = ""
    do {
        println("Play first?")
        command = readln()
    } while (command != "yes" && command != "no")
    println("Initial cards on the table: ${tableCards.cards.joinToString(" ")}")

    var userTurn = command == "yes"
    var userIsLastWinner = userTurn
    do {
        do {
            userIsLastWinner = if (userTurn) userCards.playCard(userIsLastWinner) else computerCards.playCard(userIsLastWinner)
            userTurn = !userTurn
        } while (userCards.cards.size > 0 || computerCards.cards.size > 0)

        if (deck.cards.size > 0) {
            deck.tossCardsToPlayer(userCards)
            deck.tossCardsToPlayer(computerCards)
        } else {
            if (tableCards.cards.size>0) {
                if (userIsLastWinner) userCards.winCards() else computerCards.winCards()
            }
            exitGame(userIsLastWinner)
        }
    } while (true)
}

open class CardGroup() {
    var cards: MutableList<Card> = mutableListOf()
    protected fun moveCards(moveTo: CardGroup, nOfCards: Int) {
        repeat(nOfCards) {
            moveTo.cards.add(this.cards[0])
            cards.removeFirst()
        }
    }
}

class Deck() : CardGroup() {
    fun tossCardsToTable() {
        moveCards(tableCards, 4)
    }

    fun tossCardsToPlayer(moveTo: PlayerCards) {
        moveCards(moveTo, 6)
    }

    fun createDeck() {

        for (rank in ranks.indices) {
            for (suit in suits.indices) {
                cards.add(Card(ranks[rank], suits[suit]))
            }
        }
        cards = cards.shuffled().toMutableList()
    }
}

class TableCards : CardGroup()

class PlayerCards : CardGroup() {
    var wonCards: MutableList<Card> = mutableListOf()
    var point = 0
    var someoneWon = ""
    var candidateCards : MutableList<Card> = mutableListOf()
    var tableLastCard = Card("", "")
    fun playCard(userIsLastWinner: Boolean) : Boolean{
        try {
            tableLastCard = tableCards.cards.last()
            println("${tableCards.cards.size} cards on the table, and the top card is $tableLastCard")
        } catch (e: NoSuchElementException) {
            println("No cards on the table")
            tableLastCard = Card("", "")
        }
        var cardNo = ""
        if (this == userCards) {
            do {
                println("Cards in hand: ${this.cards.joinToString(" ") { "${cards.indexOf(it) + 1})$it" }}")
                println("Choose a card to play (1-${cards.size}):")
                cardNo = readln()
                if (cardNo == "exit") gameOver()
            } while (cardNo.toIntOrNull() == null || cardNo.toInt() !in 1..cards.size)
        } else {
            println(this.cards.joinToString(" "))
            candidateCards = findCandidateCards()
            cardNo = when{
                cards.size == 1 -> "1"
                tableLastCard.suit == "" -> noCardsOnTableOrCandidate()
                candidateCards.size == 0 -> noCardsOnTableOrCandidate()
                candidateCards.size == 1 -> (cards.indexOf(candidateCards.first())+1).toString()
                candidateCards.size > 1 -> noCardsOnTableOrCandidate(tableLastCard)
                else -> (cards.indexOf(cards.random())+1).toString()
            }
            println("Computer plays ${this.cards[cardNo.toInt()-1]}")
        }
        val chosenCard = this.cards[cardNo.toInt() - 1]
        tableCards.cards.add(chosenCard)
        this.cards.remove(chosenCard)
        if (tableLastCard.suit != "") {
            if (tableLastCard.suit == chosenCard.suit || tableLastCard.rank == chosenCard.rank) {
                this.winCards()
            }
        }
        return when (someoneWon){
            "user" -> true
            "computer" -> false
            else -> userIsLastWinner
        }
    }

    fun winCards(){
        val iterator = tableCards.cards.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            this.wonCards.add(item)
            iterator.remove()
            if (item.rank in winningRank) point++
        }
        printWinning()
        someoneWon = if (this == userCards) "user" else "computer"
    }

    fun printWinning() {
        println("${if (this == userCards) "Player" else "Computer"} wins cards")
        println("Score: Player ${userCards.point} - Computer ${computerCards.point}")
        println("Cards: Player ${userCards.wonCards.size} - Computer ${computerCards.wonCards.size}")
    }

    fun noCardsOnTableOrCandidate(tableLastCard: Card = Card("", "")): String{
        val sameSuits: MutableList<Card>
        val sameRanks: MutableList<Card>
        if (tableLastCard.suit == ""){
            sameSuits = getSameSuits()
            sameRanks = getSameRanks()
        } else {
            sameSuits = getSameSuits(listOf(tableLastCard.suit),findCandidateCards())
            sameRanks = getSameRanks(listOf(tableLastCard.rank),findCandidateCards())
        }


        val chosenCard = when {
            sameSuits.size > 0 -> sameSuits.random()
            sameRanks.size > 0 -> sameRanks.random()
            else -> cards.random()
        }
        return (cards.indexOf(chosenCard)+1).toString()

    }

    fun getSameSuits(_suits: List<String> = suits, listOfCardsToSearch: MutableList<Card> = computerCards.cards): MutableList<Card>{
        val sameSuits: MutableList<Card> = mutableListOf()
        _suits.forEach {
            var counter = 0
            for (card in listOfCardsToSearch){
                if (card.suit == it) counter++
            }
            if (counter>1){
                for (card in listOfCardsToSearch){
                    if (card.suit == it) sameSuits+= card
                }
            }
        }
        return sameSuits
    }

    private fun getSameRanks(_ranks: List<String> = ranks, listOfCardsToSearch: MutableList<Card> = computerCards.cards): MutableList<Card>{
        val sameRanks: MutableList<Card> = mutableListOf()
        _ranks.forEach {
            var counter = 0
            for (card in listOfCardsToSearch){
                if (card.rank == it) counter++
            }
            if (counter>1){
                for (card in listOfCardsToSearch){
                    if (card.rank == it) sameRanks+= card
                }
            }
        }
        return sameRanks
    }

    fun findCandidateCards(): MutableList<Card>{
        val candidateCards: MutableList<Card> = mutableListOf()
            for (card in computerCards.cards){
                if (card.rank == tableLastCard.rank || card.suit == tableLastCard.suit){
                    candidateCards.add(card)
                }
            }
        return candidateCards
    }

}


data class Card(var rank: String, var suit: String) {
    override fun toString(): String {
        return rank + suit
    }
}

fun exitGame(userIsLastWinner: Boolean) {
    when {
        userCards.cards.size > computerCards.cards.size -> userCards.point+=3
        userCards.cards.size < computerCards.cards.size -> computerCards.point+=3
        userIsLastWinner -> userCards.point+=3
        !userIsLastWinner -> computerCards.point+=3
    }

    println("Score: Player ${userCards.point} - Computer ${computerCards.point}")
    println("Cards: Player ${userCards.wonCards.size} - Computer ${computerCards.wonCards.size}")
    gameOver()
}

fun gameOver(){
    println("Game Over")
    exitProcess(0)
}