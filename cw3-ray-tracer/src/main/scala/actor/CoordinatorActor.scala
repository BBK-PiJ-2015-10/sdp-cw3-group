package actor

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.routing.RoundRobinPool
import application.Initialize
import application.SetPixel
import application.TracerConfiguration
import application.WorkUnit
import application.Finalize
import application.SetPixel
import application.WorkUnit

class CoordinatorActor(configuration: TracerConfiguration) extends Actor {

  var accumulator: ActorRef = _
  
  //var waiting = configuration.dimensions._1 * configuration.dimensions._2
  var waiting = configuration.workUnits

  var startTime = System.currentTimeMillis()

  def receive = {

    case Initialize => {

      println("Initializing all Actors")

      initializeSystem(configuration)

    }
    
//    case SetPixel(x,y,c) => {
    case SetPixel(pixels) => {
      
//      accumulator ! SetPixel(x,y,c)
      accumulator ! SetPixel(pixels)
      waiting -= 1
      if (waiting == 0 ) {
        accumulator ! Finalize  
        println(s"Finalize after ${System.currentTimeMillis() - startTime} (ms)")
      }
      
      
    }
    
    
    case Finalize => {
      context stop self
      context.system.terminate
      
    }
    

  }

  def initializeSystem(configuration: TracerConfiguration) = {

    accumulator = context.actorOf(Props(new AccumulatorActor(configuration)), name = "accumulatorActor")

    println("Create workers")
    
    accumulator ! Initialize

    val workerRouter = context.actorOf(Props(classOf[WorkerActor], configuration)
      .withRouter(RoundRobinPool(configuration.workers)), name = "workerRouter")

//    for (i <- 0 until configuration.dimensions._2 ) workerRouter ! WorkUnit(0, i, configuration.dimensions._1 -1, i)

    val blockSize = Math.ceil(Math.max(configuration.dimensions._1.toDouble * configuration.dimensions._2.toDouble / configuration.workUnits, 1)).toInt
                                               

    val cartesianPixels = (0 to configuration.dimensions._1 - 1).flatMap(x => (0 to configuration.dimensions._2 - 1).map { y => (x, y) })

    (0 to configuration.workUnits - 1).foreach { x =>  workerRouter ! WorkUnit(cartesianPixels.slice(x * blockSize, x * blockSize + blockSize)) }


  }

}
