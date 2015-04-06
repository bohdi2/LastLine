name := """LastLine"""

version := "1.0"

scalaVersion := "2.11.5"

scalacOptions += "-target:jvm-1.7"


libraryDependencies ++= Seq(
  // Uncomment to use Akka
  //"com.typesafe.akka" % "akka-actor_2.11" % "2.3.9",
  "junit"             % "junit"           % "4.12"  % "test",
  "com.novocode"      % "junit-interface" % "0.11"  % "test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-a")

