import org.scalatest.FlatSpec

import scala.collection.mutable.ListBuffer


class WindowCollector extends WindowHandler {
  val windows: ListBuffer[List[(BigInt, Double)]] = ListBuffer[List[(BigInt, Double)]]()

  override def handleWindow(window: List[(BigInt, Double)]) = {
    windows += window
  }
}

class TimeWindowParserSpec extends FlatSpec {
  "A TimeWindowParser" should "produce a window with one update" in {
      val collector = new WindowCollector
      val windowLength = 60;
      val windowParser = new TimeWindowParser(windowLength,collector)

      val time=1;
      val value=2.0;
      windowParser.processLine(time,value);

      assert(collector.windows.length == 1)
      assert(collector.windows(0).length == 1)
      assert(collector.windows(0)(0) == (time,value) )

  }

  "A TimeWindowParser" should "produce a window with one update and another with one update 60 seconds later " in {
    val collector = new WindowCollector
    val windowLength = 60;
    val windowParser = new TimeWindowParser(windowLength,collector)

    val time=1;
    val value=2.0;
    windowParser.processLine(time,value);

    assert(collector.windows.length == 1)
    assert(collector.windows(0).length == 1)
    assert(collector.windows(0)(0) == (time,value) )

    windowParser.processLine(time+windowLength,value)

    assert(collector.windows.length == 2)
    assert(collector.windows(0).length == 1)
    assert(collector.windows(1).length == 1)
    assert(collector.windows(0)(0) == (time,value) )
    assert(collector.windows(1)(0) == (time+60,value) )

  }


  "A TimeWindowParser" should "produce a window with two updates and another with two updates 60 seconds later " in {
    val collector = new WindowCollector
    val windowLength = 60;
    val windowParser = new TimeWindowParser(windowLength,collector)

    val time=1;
    val value=2.0;
    val offset=30;
    windowParser.processLine(time,value);

    assert(collector.windows.length == 1)
    assert(collector.windows(0).length == 1)
    assert(collector.windows(0)(0) == (time,value) )

    val nextTime1 = time+offset
    windowParser.processLine(nextTime1,value)

    assert(collector.windows.length == 2)
    assert(collector.windows(0).length == 1)
    assert(collector.windows(0)(0) == (time,value) )
    assert(collector.windows(1).length == 2)
    assert(collector.windows(0)(0) == (time,value) )
    assert(collector.windows(1)(1) == (nextTime1,value) )

    val nextTime2 = nextTime1+offset
    windowParser.processLine(nextTime2,value)

    assert(collector.windows.length == 3)
    assert(collector.windows(0).length == 1)
    assert(collector.windows(0)(0) == (time,value) )
    assert(collector.windows(1).length == 2)
    assert(collector.windows(0)(0) == (time,value) )
    assert(collector.windows(1)(1) == (nextTime1,value) )
    assert(collector.windows(2).length == 2)
    assert(collector.windows(2)(0) == (nextTime1,value) )
    assert(collector.windows(2)(1) == (nextTime2,value) )

  }
}
