import akka.actor.{Props, ActorSystem}
import com.mildlyskilled._
import com.mildlyskilled.actor.CoordinatorActor
import com.mildlyskilled.protocol._
import com.mildlyskilled.actor.TracerActor

object TraceMain extends App {
  
  val (infile, outfile) = ("src/main/resources/input.dat", "output.png")
  val scene = FileReader.parse(infile)
  val t = new Trace

  render(scene, outfile, t.Width, t.Height)

  def render(scene: Scene, outfile: String, width: Int, height: Int) = {
    
    val image = new Image(width, height)
 
    val system = ActorSystem("RayTracerSystem")
      
    val mastercoordinator = system.actorOf(Props(new CoordinatorActor(outfile,image,scene)), name = "mastercoordinator")
      
    mastercoordinator ! StartMessage
    
    
  }
}
