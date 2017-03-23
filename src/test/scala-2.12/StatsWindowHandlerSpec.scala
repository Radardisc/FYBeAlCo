import java.io.{BufferedOutputStream, ByteArrayOutputStream, OutputStreamWriter}

import org.scalatest.FlatSpec


class StatsWindowHandlerSpec extends FlatSpec {

  "A StatsWindowHandler" should "produce stats from a window" in {
    val window = List.range(1,6).map( x => (BigInt(x),x.toDouble ) )

    val handler = new StatsWindowHandler

    handler.handleWindow(window);

    assert( handler.numPoints == 5)
    assert( handler.maxV == 5.0)
    assert( handler.minV == 1.0)
    assert( handler.timeStampValue == 5.0)
    assert( handler.time == 5)
    assert( handler.rollingSum == 15.0 )

    handler.handleWindow(window.reverse);

    //The windows handler is agnostic regarding the first entry
    //Ordering is assumed in the data
    assert( handler.timeStampValue == 1.0)
    assert( handler.time == 1)

    assert( handler.numPoints == 5)
    assert( handler.maxV == 5.0)
    assert( handler.minV == 1.0)

    assert( handler.rollingSum == 15)



  }


  "A PrintingStatsWindowHandler" should "print stats to an output stream" in {
    val window = List.range(1,6).map( x => ( BigInt(1355270600 +x),1.0+(x.toDouble/10) ) )

    val output = new ByteArrayOutputStream()
    val writer = new OutputStreamWriter(output)
    val handler = new PrintingStatsWindowHandler( writer )

    handler.handleWindow(window)

    assert( handler.numPoints == 5)
    assert( handler.maxV == 1.5)
    assert( handler.minV == 1.1)
    assert( handler.timeStampValue == 1.5)
    assert( handler.time == 1355270605)
    assert( handler.rollingSum == 6.5)

    writer.close()

    val expected = "1355270605 1.50000 5 6.50000 1.10000 1.50000\n"
    assert( new String(output.toByteArray) == expected)
  }
}
