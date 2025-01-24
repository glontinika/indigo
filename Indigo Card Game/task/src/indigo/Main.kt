package indigo



fun main() {
    val deck = Deck()
    println(deck.getRanks().joinToString(" ") + "\n")
    println(deck.getSuits().joinToString(" ") + "\n")
    println(deck.getCards().joinToString(" ") {it.getCardTxt()})


}

class Deck(){
    private val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    private val suits = listOf("♦", "♥", "♠", "♣")
    fun getCards() : List<Card> {
        val theDeck = mutableListOf<Card>()
        for (rank in ranks.indices){
            for (suit in suits.indices){
                theDeck.add(Card(ranks[rank],suits[suit]))
            }
        }
        return theDeck.shuffled()
    }

    fun getRanks(): List<String>{
        return ranks
    }
    fun getSuits(): List<String>{
        return suits
    }
}

data class Card (var rank: String, var suit: String){
    fun getCardTxt() = rank + suit
}