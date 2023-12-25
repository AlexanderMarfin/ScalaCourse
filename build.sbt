import sbt._
lazy val sparkApp ={
  //создаем проект с именем spark-education-job
  Project(id = "spark-education-job", base = file("."))
    //применяем настройки для сборки, компиляции и запуска
    .settings(
      ProjectSettings.generalSettings,
      ProjectSettings.assemblySettings,
      ProjectSettings.runLocalSettings
    )
}
