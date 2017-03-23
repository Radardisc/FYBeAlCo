import scala.collection.mutable.Queue



class TimeWindowParser( windowLength: Int, windowHandler: WindowHandler) extends LineParser {

  val items:Queue[(BigInt,Double)] = Queue[(BigInt,Double)]();

  override def parseLine(line: String): Unit = {
    val split: Array[String] = line.split("\\s")
    val time = BigInt(split(0))
    val value = split(1).toDouble
    processLine(time,value)
  }

  def handleWindow() = {
    windowHandler.handleWindow(items.toList)
  }

  def processLine( time:BigInt, value:Double) = {
    if (items.length == 0) {
      items.enqueue((time, value))

    } else {

      while(items.nonEmpty && items.front._1 + windowLength <= time) {
        items.dequeue
      }

      items.enqueue((time,value))

    }
    handleWindow()
  }
}
