import java.io.{ByteArrayOutputStream, OutputStreamWriter}

import scala.io.Source

object ProcessFile {
  def main(args:Array[String] ) : Unit = {
    args.length match {
      case 1 =>
        parseAndDisplay(args.head)
      case _ =>
        println("File must be specified")
    }
  }

  def parseAndDisplay(file:String): Unit = {

    val output = new ByteArrayOutputStream()
    val writer = new OutputStreamWriter(output)
    val handler = new PrintingStatsWindowHandler( writer )
    val reader = new FileReader


    writer.write(header())
    reader.traverseFile(file,new TimeWindowParser(60,handler))
    writer.close()

    println(new String(output.toByteArray()))
  }

  def header() : String ={
    """T          V        N RS      MinV    MaxV
----------------------------------------------
"""
  }

}


class FileReader {

  def traverseFile( string: String, lineParser: LineParser ): Unit = {
    traverseStream( Source.fromFile(string).getLines(), lineParser );

  }
  def traverseStream( iterator: Iterator[String],  lineParser: LineParser ): Unit = {
    for( line <- iterator ){
      lineParser.parseLine(line)
    }
  }


}
