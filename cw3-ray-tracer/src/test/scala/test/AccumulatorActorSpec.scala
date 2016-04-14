package test

import akka.testkit.{TestActorRef}
import akka.actor.{Props, Actor}
import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit,TestProbe}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, Suite, FlatSpecLike}

import worker.Counter
import application.TracerConfiguration
import application.Initialize
import actor.AccumulatorActor



class AccumulatorActorSpec extends TestKit(ActorSystem("test-system"))
                  with FlatSpecLike
                  with BeforeAndAfterAll
                  with MustMatchers {
  
  this: TestKit with Suite =>
  override protected def afterAll(): Unit ={
    super.afterAll()
    system.terminate()
  }
  
  override protected def beforeAll(): Unit ={
     val c = TracerConfiguration()
     c.dimensions = (800,600)
  }
  
  "This xxx" should "say something here" in {
      
    val c = TracerConfiguration()
    c.dimensions = (800,600)   
    
    val sender = TestProbe()
    
    var accumulator = system.actorOf(Props(new AccumulatorActor(c)))
    
    accumulator ! Initialize
    
    //accumulator.
    
  }
  
}