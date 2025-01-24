package indigo



fun main() {
    val deck = Deck()
    println(deck.getRanks().joinToString(" ") + "\n")
    println(deck.getSuits().joinToString(" ") + "\n")
    println(deck.getCardTxt().joinToString(" "))


}

class Deck(){
    private val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    private val suits = listOf("♦", "♥", "♠", "♣")
    fun getCards(): MutableList<Card> {
        val theDeck = mutableListOf<Card>()
        for (rank in ranks.indices){
            for (suit in suits.indices){
                theDeck.add(Card(ranks[rank],suits[suit]))
            }
        }
        return theDeck.toList().shuffled().toMutableList()
    }
    fun getCardTxt(): MutableList<String>{
        val deckCards = Deck().getCards()
        val cardTxt = mutableListOf<String>()
        for (card in deckCards){
            cardTxt.add("${card.rank}${card.suit}")
        }
        return cardTxt
    }
    fun getRanks(): List<String>{
        return ranks
    }
    fun getSuits(): List<String>{
        return suits
    }
}

data class Card (var rank: String, var suit: String)