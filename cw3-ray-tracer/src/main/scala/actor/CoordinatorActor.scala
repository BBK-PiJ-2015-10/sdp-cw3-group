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

/**
  * Creates the actors and coordinates the messages between them.
  */
class CoordinatorActor(configuration: TracerConfiguration) extends Actor {

  // Reference to accumulator actor.
  private var accumulator: ActorRef = _
   
  // Keeps track of messages.
  private var waiting: Int = _

  // For logging purposes.
  private var startTime = System.currentTimeMillis()

  /**
    * Handles the following messages:
    * - Initialize: Generates actors and start work distribution.
    * - SetPixel: Passes SetPixel to accumulator and updates waiting counter.
    * - Finalize: Shut down all actors and shut down system.
    */
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

  /**
    * Creates accumulator actor and set of worker actors, generates a set of pixel coordinates
    * which are distributed among the worker actors.
    */
  def initializeSystem(configuration: TracerConfiguration) = {

    waiting = configuration.workUnits
    
    accumulator = context.actorOf(Props(new AccumulatorActor(configuration)), name = "accumulatorActor")

    println("Create workers")
    
    accumulator ! Initialize

    val workerRouter = context.actorOf(Props(classOf[WorkerActor], configuration)
      .withRouter(BalancingPool(configuration.workers)), name = "workerRouter")

     // Create a Vector of all the pixel locations
    val cartesianPixels = RowPixelSequencer.sequence(configuration.dimensions._1, configuration.dimensions._2)

    // Split the vector into work units
    val pixelMap = NormalPixelDistributor.distribute(configuration.workUnits, cartesianPixels).sortBy(x => (x.size))

    // Allocate each work unit to a worker
    pixelMap.foreach(pixels => workerRouter ! WorkUnit(pixels))
  }

  /**
    * Restarts actors on failure.
    */
  override val supervisorStrategy = OneForOneStrategy(loggingEnabled=false) {
      case _:Exception => Restart
  }  
}
