import job.ProcessingJob

object SparkApp {
  //точка входа в программу
  def main(args: Array[String]): Unit = {
    //создание экземпляра класса джобы и ее запуск
    new ProcessingJob().run()
  }

}
