package test

import application.TracerConfiguration
import accumulator.ImageAccumulator

import com.mildlyskilled.harness.DefaultHarness

import com.mildlyskilled._

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageAccumulatorSpecTest extends DefaultHarness {

  val c = TracerConfiguration()

  c.dimensions = (800, 600)

  var imageAccumulator = new ImageAccumulator(c)

  behavior of "ImageAccumulatorSpec"

  it should "have a null image before initialization" in {
    imageAccumulator.image should be(null)
  }

  it should "throw a NullPointerException for a null image dimensions" in {
    intercept[NullPointerException] {
      imageAccumulator.image.im.getHeight should be(c.dimensions._2)
      imageAccumulator.image.im.getWidth should be(c.dimensions._1)
    }
  }

  it should "have a height and width equal to the configuration dimensions after initialiation" in {
    imageAccumulator.initialize()
    imageAccumulator.image.im.getHeight should be(c.dimensions._2)
    imageAccumulator.image.im.getWidth should be(c.dimensions._1)
  }

  it should "set a color in an image coordindate when instructed" in {

    val expected = Color.fromRGB(Color.blue.rgb)
    imageAccumulator.initialize()
    imageAccumulator.accumulate(1, 1, Color.blue)
    Color.fromRGB(imageAccumulator.image.im.getRGB(1, 1)) should be(expected)
  }

  it should "set the correct colour from a vector of (Int,Int,Color)" in {
    imageAccumulator.initialize()
    val expectedColor = Color.fromRGB(Color.red.rgb)
    val row = 0
    val col = 0
    val d = scala.collection.immutable.Vector((row, col, Color.red))
    imageAccumulator.accumulate(d)
    Color.fromRGB(imageAccumulator.image.im.getRGB(row,col)) should be(expectedColor)
  }

  it should "set the correct colour from a vector of two tuples(Int,Int,Color)" in {
    imageAccumulator.initialize()
    val expectedColor1 = Color.fromRGB(Color.red.rgb)
    val expectedColor2 = Color.fromRGB(Color.green.rgb)
    val row = 0
    val col = 0
    val d = scala.collection.immutable.Vector((row, col, Color.red),(row+1,col+1,Color.green))
    imageAccumulator.accumulate(d)
    Color.fromRGB(imageAccumulator.image.im.getRGB(row,col)) should be(expectedColor1)
    Color.fromRGB(imageAccumulator.image.im.getRGB(row+1,col+1)) should be(expectedColor2)
  }
  
  


}