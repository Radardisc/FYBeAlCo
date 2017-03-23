import java.io.{ByteArrayOutputStream, OutputStreamWriter}

import org.scalatest.FlatSpec

class LineCounter extends LineParser {

  var count:Int = 0

  override def parseLine(line: String): Unit = {
    count = count + 1
  }
}

class FileReaderSpec extends FlatSpec {
  "A FileReader" should "traverse a file with a LineAggregator" in {
    val agg = new LineCounter
    val reader = new FileReader
    val path = getClass.getResource("data_scala.txt").getFile
    reader.traverseFile(path,agg);

    assert( agg.count > 0)
  }

  "A FileReader" should "create an output stream when combined PrintingStatsWindowHandler" in {

    val output = new ByteArrayOutputStream()
    val writer = new OutputStreamWriter(output)
    val handler = new PrintingStatsWindowHandler( writer )
    val reader = new FileReader
    val path = getClass.getResource("data_scala.txt").getFile
    reader.traverseFile(path,new TimeWindowParser(60,handler));

    writer.close()

    Console.out.write(output.toByteArray);
  }
}
