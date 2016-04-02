package application

import com.mildlyskilled.FileReader
import akka.actor.ActorSystem
import akka.actor.Props
import actor.CoordinatorActor

object TraceMain extends App {
  
  val c = TracerConfiguration()
  
  c.infile = "src/main/resources/input.dat"
  
  c.outfile = "output.png"
  
  c.scene = FileReader.parse(c.infile)
  
  c.workers = Runtime.getRuntime().availableProcessors()
  
  c.workUnits = 240
  
  c.dimensions = (800,600)
  
  c.antiAliasingFactor = 4
  
  runRayTracer(c)
  
  
  def runRayTracer(configuration : TracerConfiguration ) {
    
     val system = ActorSystem("RayTracerSystem")
     
     val coordinator = system.actorOf(Props(new CoordinatorActor(configuration)), name = "coordinator")

     println(s"Trace ${c.dimensions._1}x${c.dimensions._2} image with ${c.workers} workers and ${c.workUnits} work units")
    
     coordinator ! Initialize
     
  }  
}
