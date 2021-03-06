name := "ray-tracer"

version := "1.0"

scalaVersion := "2.11.7"

lazy val akkaVersion = "2.4.2"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

/*scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/root-doc.txt")
*/

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.apache.commons" % "commons-math3" % "3.0"
)

