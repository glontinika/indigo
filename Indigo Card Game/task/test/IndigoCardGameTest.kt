import org.hyperskill.hstest.dynamic.DynamicTest
import org.hyperskill.hstest.stage.StageTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testing.TestedProgram

class CardGameTest : StageTest<Any>() {

    @DynamicTest
    fun normalExeTest(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim()
        var position = checkOutput(outputString.toLowerCase(), 0, "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong menu prompt.")

        outputString = main.execute("reset").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Card deck is reset.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the reset action.")

        outputString = main.execute("shuffle").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Card deck is shuffled.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the shuffle action.")

        outputString = main.execute("get").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Number of cards:".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the get action.")

        outputString = main.execute("52").trim()
        position = checkOIfValidCards(outputString)
        if ( position  == -1 ) return CheckResult(false, "Wrong cards printout.")
        if (!checkIfUniqueCards(outputString)) return CheckResult(false, "There are duplicate cards.")
        position = checkOutput(outputString.toLowerCase(), position, "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong menu prompt.")

        outputString = main.execute("exit").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Bye".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong exit message.")

        if (!main.isFinished) return CheckResult(false, "Application hasn't exited after exit command.")

        return CheckResult.correct()
    }

    @DynamicTest
    fun InsufficientNumberTest(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim()
        var position = checkOutput(outputString.toLowerCase(), 0, "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong menu prompt.")

        outputString = main.execute("reset").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Card deck is reset.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the reset action.")

        outputString = main.execute("get").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Number of cards:".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the get action.")

        outputString = main.execute("10").trim()
        position = checkOIfValidCards(outputString)
        if ( position  == -1 ) return CheckResult(false, "Wrong cards printout.")
        if (!checkIfUniqueCards(outputString)) return CheckResult(false, "There are duplicate cards.")
        position = checkOutput(outputString.toLowerCase(), position, "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong menu prompt.")

        outputString = main.execute("get").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Number of cards:".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the get action.")

        outputString = main.execute("43").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "The remaining cards are insufficient to meet the request.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after input number of cards larger than remaining cards.")

        outputString = main.execute("reset").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Card deck is reset.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the reset action.")

        outputString = main.execute("get").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Number of cards:".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the get action.")

        outputString = main.execute("52").trim()
        position = checkOIfValidCards(outputString)
        if ( position  == -1 ) return CheckResult(false, "Wrong cards printout.")
        if (!checkIfUniqueCards(outputString)) return CheckResult(false, "There are duplicate cards.")
        position = checkOutput(outputString.toLowerCase(), position, "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong menu prompt.")

        outputString = main.execute("get").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Number of cards:".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the get action.")

        outputString = main.execute("1").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "The remaining cards are insufficient to meet the request.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after input number of cards larger than remaining cards.")

        outputString = main.execute("exit").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Bye".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong exit message.")

        if (!main.isFinished) return CheckResult(false, "Application hasn't exited after exit command.")

        return CheckResult.correct()
    }

    @DynamicTest
    fun InvalidNumberTest(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim()
        var position = checkOutput(outputString.toLowerCase(), 0, "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong menu prompt.")

        outputString = main.execute("get").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Number of cards:".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the get action.")

        outputString = main.execute("0").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Invalid number of cards.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after input of invalid number of cards.")

        outputString = main.execute("get").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Number of cards:".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the get action.")

        outputString = main.execute("one").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Invalid number of cards.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after input of invalid number of cards.")

        outputString = main.execute("get").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Number of cards:".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after the get action.")

        outputString = main.execute("53").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Invalid number of cards.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after input of invalid number of cards.")

        outputString = main.execute("exit").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Bye".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong exit message.")

        if (!main.isFinished) return CheckResult(false, "Application hasn't exited after exit command.")

        return CheckResult.correct()
    }

    @DynamicTest
    fun WrongActionTest(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim()
        var position = checkOutput(outputString.toLowerCase(), 0, "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong menu prompt.")

        outputString = main.execute("action").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Wrong action.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after input of invalid action.")

        outputString = main.execute("game").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Wrong action.".toLowerCase(), "Choose an action (reset, shuffle, get, exit):".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong output after input of invalid action.")

        outputString = main.execute("exit").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "Bye".toLowerCase())
        if ( position  == -1 ) return CheckResult(false, "Wrong exit message.")

        if (!main.isFinished) return CheckResult(false, "Application hasn't exited after exit command.")

        return CheckResult.correct()
    }
}

fun checkOutput(outputString: String, searchPos: Int, vararg checkStr: String): Int {
    var searchPosition = searchPos
    for (str in checkStr) {
        val findPosition = outputString.indexOf(str, searchPosition)
        if (findPosition == -1) return -1
        if ( outputString.substring(searchPosition until findPosition).isNotBlank() ) return -1
        searchPosition = findPosition + str.length
    }
    return searchPosition
}

fun checkOIfValidCards(outputString: String): Int {
    val lines = outputString.lines()
    val cards = lines.first().trim().split(" ")
    val cardRegex = "(A|[2-9]|10|J|Q|K)(\\u2666|\\u2665|\\u2660|\\u2663)".toRegex()
    for (card in cards) if (!card.matches(cardRegex)) {
        return -1
    }

    return lines.first().length
}

fun checkIfUniqueCards(outputString: String): Boolean {
    val lines = outputString.lines()
    val cards = lines.first().trim().split(" ")
    return cards.distinct().size == cards.size
}


