import java.io.OutputStreamWriter

class PrintingStatsWindowHandler( outputStreamWriter: OutputStreamWriter ) extends StatsWindowHandler {

  override def handleWindow(window: List[(BigInt, Double)]) = {
    super.handleWindow(window)

    outputStreamWriter.write(formatStats(this.time,this.timeStampValue,this.numPoints,this.rollingSum,this.minV,this.maxV))
  }

  //I could probably extract this into a trait, but it seems a bit like overkill
  def formatStats(time: BigInt, initial: Double, numPoints: Integer, rollingSum: Double, minV: Double, maxV: Double) : String = {
    return f"${time.toString()} $initial%1.5f $numPoints $rollingSum%1.5f $minV%1.5f $maxV%1.5f\n"
  }
}
