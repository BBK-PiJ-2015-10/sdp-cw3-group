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
  
  var waiting = configuration.dimensions._1 * configuration.dimensions._2
  //var waiting = configuration.workUnits


  def receive = {

    case Initialize => {

      println("Initializing all Actors")

      initializeSystem(configuration)

    }
    
    case SetPixel(x,y,c) => {
      
      waiting -= 1
      accumulator ! SetPixel(x,y,c)
      if (waiting == 0 ) {
        accumulator ! Finalize  
        println("Finalize")
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

    //val noOfPixels = (configuration.dimensions._1 * configuration.dimensions._2) / configuration.workUnits

    for (i <- 0 until configuration.dimensions._1) workerRouter ! WorkUnit(0, i, configuration.dimensions._2 -1, i)

    //    println(s"Create ${configuration.workUnits} sub units from ${configuration.dimensions}")

  }

}
