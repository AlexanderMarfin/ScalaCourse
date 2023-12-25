import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._
import sbtassembly.AssemblyPlugin.autoImport.assemblyOption
import sbtassembly.{ MergeStrategy, PathList }

object ProjectSettings {
  //общие настройки проекта
  val generalSettings = Seq(
    //версия проекта
    version := "0.1",
    //указываются список поддерживаемых версий scala для компиляции
    crossScalaVersions := Version.Scala.supportedScalaVersions,
    //применение настроек компиляции
    scalacOptions ++= CompileOptions.compileOptions(scalaVersion.value),
    //подключение списка зависимостей
    libraryDependencies ++= Dependencies.Dependencies
  )
  //настройки сборки Fat JAR
  val assemblySettings = Seq(
    assembly / assemblyOption := (assembly / assemblyOption).value.copy(includeScala = false),
    //применение стратегий мержа конфликтующих зависимостей
    assembly / assemblyMergeStrategy := {
      case "module-info.class"                                                   => MergeStrategy.discard
      case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
      case PathList("META-INF", xs @ _*)                                         => MergeStrategy.discard
      case x                                                                     => MergeStrategy.first
    }
  )
  //настройки запуска проекта
  val runLocalSettings = Seq(
    Compile / run := Defaults
      .runTask(
        Compile / fullClasspath,
        Compile / run / mainClass,
        Compile / run / runner
      )
      .evaluated
  )
}
