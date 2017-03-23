
class StatsWindowHandler extends WindowHandler {
  var time:BigInt=0
  var timeStampValue:Double=0.0
  var numPoints:Integer=0
  var rollingSum: Double=0.0
  var minV:Double=0.0
  var maxV:Double=0.0

  override def handleWindow(window: List[(BigInt, Double)]) = {
    numPoints=window.length
    time=window(numPoints-1)._1
    timeStampValue=window(numPoints-1)._2
    rollingSum=window.foldLeft(0.0)( (acc,current) => acc+current._2 )
    minV=window.minBy(_._2)._2
    maxV=window.maxBy(_._2)._2
  }
}
