import java.lang.Exception
import java.lang.NullPointerException

fun reverse(input: Int?): Int {
    try {
        val asd = input.toString()
        var txt = ""
        for (i in asd.length-1 downTo 0){
            txt+=asd[i]
        }
        return txt.toInt()
    } catch (e: Exception){
        return -1
    }
}