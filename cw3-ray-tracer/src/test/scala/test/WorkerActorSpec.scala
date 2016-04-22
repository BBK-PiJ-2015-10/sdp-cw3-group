package test

import akka.testkit.{TestActorRef}
import akka.actor.{Props, Actor}
import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit,TestProbe}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, Suite, FlatSpecLike}

import worker.Counter
import application.TracerConfiguration
import application.Initialize
import actor.WorkerActor
import com.mildlyskilled.FileReader



class WorkerActorSpec extends TestKit(ActorSystem("test-system"))
                  with FlatSpecLike
                  with BeforeAndAfterAll
                  with MustMatchers {
  
  this: TestKit with Suite =>
  override protected def afterAll(): Unit ={
    super.afterAll()
    system.terminate()
  }
  
  "This xxx" should "say something here" in {
      
    val c = TracerConfiguration()
    
    c.infile = "src/main/resources/input.dat"
    c.scene = FileReader.parse(c.infile)
    c.dimensions = (800,600)
    c.antiAliasingFactor = 4
    
    val sender = TestProbe()
    
    var testWorker = system.actorOf(Props(new WorkerActor(c)))
    
    
    
    
    
    
    testWorker ! Initialize
    
    //accumulator.
    
  }
  
  
}


