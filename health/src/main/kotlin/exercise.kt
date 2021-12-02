import java.lang.IndexOutOfBoundsException

class Aerobic(name : String, time : String) {
  // String형 시간 생성
  private val stringTime = time
  // 초 단위 시간
  private val timeOfSecond = changeToSecond()

  // 문자열 길이 다를 때 발생하는 예외
  class DifferentLengthException(message: String) : Exception(message)

  private fun validateLength(time : String) {

  }

  // stringTime을 초 단위로 바꿔주는 함수
  private fun changeToSecond() : Int {
    // 분과 초 분리
    val str = stringTime.split(":")

    var result : Int = 0
    try {
      val min = str[0].toInt()
      val sec = str[1].toInt()

      result = min * 60 + sec
    }
    catch (e : IndexOutOfBoundsException) {
      println("Wrong time format!")
    }

    return result
  }

  fun getTimeBySecond() : Int {
    return timeOfSecond
  }
}
