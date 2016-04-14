package com.mildlyskilled.test

import application.TracerConfiguration
import accumulator.ImageAccumulator

import com.mildlyskilled.harness.DefaultHarness

import com.mildlyskilled._

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class ImageAccumulatorSpec extends DefaultHarness {
  

  val c = TracerConfiguration()
  
  c.dimensions = (800,600)
  
  var imageAccumulator = new ImageAccumulator(c)
  
  behavior of "ImageAccumulatorSpec"
  
  
  it should "have a null image before initialization" in {
    imageAccumulator.image should be (null)
  }
  
  
  it should "throw a NullPointerException for a null image dimensions" in {
    intercept[NullPointerException]{
    imageAccumulator.image.im.getHeight should be(c.dimensions._2) 
    imageAccumulator.image.im.getWidth should be(c.dimensions._1)} 
  }
  
  
  it should "have a height and width equal to the configuration dimensions after initialiation" in {
    imageAccumulator.initialize()
    imageAccumulator.image.im.getHeight should be(c.dimensions._2)
    imageAccumulator.image.im.getWidth should be(c.dimensions._1)
  }
  
  
  it should "set a color in an image coordindate when instructed" in {
     imageAccumulator.initialize()
     imageAccumulator.accumulate(1, 1, Color.black)
     Color.fromRGB(imageAccumulator.image.im.getRGB(1, 1))should be (Color.black)
     Color.fromRGB(imageAccumulator.image.im.getRGB(1, 1))should not be (Color.blue)
  }
  

  

  
  
  
}