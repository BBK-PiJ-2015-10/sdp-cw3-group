package actor

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.routing._
import application.Initialize
import application.SetPixel
import application.TracerConfiguration
import application.WorkUnit
import application.Finalize
import application.SetPixel
import application._


import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._


class CoordinatorActor(configuration: TracerConfiguration) extends Actor {

  var accumulator: ActorRef = _
  
  var waiting: Int = _

  var startTime = System.currentTimeMillis()

  def receive = {

    case Initialize => {

      println("Initializing all Actors")
      initializeSystem(configuration)
    }

    case m:SetPixel => {
      
      accumulator ! m
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

    //keeping track of how many work units
    waiting = configuration.workUnits


    accumulator = context.actorOf(Props(new AccumulatorActor(configuration)), name = "accumulatorActor")

    accumulator ! Initialize
    println("Create workers")

    val workerRouter = context.actorOf(Props(classOf[WorkerActor], configuration)
      .withRouter(BalancingPool(configuration.workers)), name = "workerRouter")

     // Create a Vector of all the pixel locations
    val cartesianPixels = RowPixelSequencer.sequence(configuration.dimensions._1, configuration.dimensions._2)

    // Split the vector into work units
    val pixelMap = NormalPixelDistributor.distribute(configuration.workUnits, cartesianPixels).sortBy(x => (x.size))

    // Allocate each work unit to a worker
    pixelMap.foreach(pixels => workerRouter ! WorkUnit(pixels))
  }

   
  override val supervisorStrategy = OneForOneStrategy(loggingEnabled=false) {
      case _:Exception => Restart
  }  
}
