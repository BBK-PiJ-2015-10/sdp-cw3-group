package com.mildlyskilled

import akka.testkit.{TestActorRef}
import akka.actor.{Props, Actor}

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit,TestProbe}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, Suite, FlatSpecLike}

import worker.Counter

class CounterSpec extends TestKit(ActorSystem("sample-system"))
                  with FlatSpecLike
                  with BeforeAndAfterAll
                  with MustMatchers {
  
  override def afterAll = {
     TestKit.shutdownActorSystem(system)
  }
  
  "Counter Actor" should "handle GetCount message with using TestProbe" in {
    
    val sender = TestProbe()
    
    val counter = system.actorOf(Props[Counter])
    
    sender.send(counter,Counter.Increment)
    
    sender.send(counter,Counter.GetCount)
    
    val state = sender.expectMsgType[Int]
    
    state must equal (2)
    
    
  }
  
  //it should "handle Increment message" in {
    
    //val counter = system.actorOf(Props[Counter])
    
    //counter ! Counter.Increment
    
    //expectNoMsg(1.second)
    
  //}
  
}