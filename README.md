# spark-education-job

Проект, является учебным, может использоваться в качестве шаблона при разработке spark джобы с использованием языка scala.
В качестве примера, реализована джоба, которая обрабатывает файлы в формате csv и сохраняет результат в том же формате.
Дополнительно, для ознакомления, организован сбор метрик с помошью Prometheus и их визуализация в Grafana. 
А также подключен spline agent для сбора информации по происхождению данных(data lineage).

### Требования
- IDE IntelliJ IDEA (Community Edition)
- Версия sbt - 1.9.7
- Версия JDK - java - 11
- Версия SDK - scala - 2.12
- Docker

### Структура проекта
```
spark-education
--project
--src
  --main
    --resources
    --scala
  --test
    --scala
--.scalafmt.conf
--build.sbt
--README.md
```

### Корень проекта

- __.scalafmt.conf__ - конфигурационный файл для Code Style Formatter в формате [HOCON](https://github.com/lightbend/config/blob/main/HOCON.md?ysclid=lp7zgn5hwp638385798), настраиваются отступы, переносы, длина строки кода и т.д. [docs](https://scalameta.org/scalafmt/docs/configuration.html)
- __build.sbt__ - файл с настройками для сборки проекта сборщиком [sbt](https://scalameta.org/scalafmt/docs/configuration.html)
- __README.md__ - файл с описанием проекта

### Папка project

- __build.properties__ - автосгенерированный файл, с версией сборщика sbt
- __CompileOptions__ - содержит настройки компилятора, например вызывать ошибку компиляции при нахождении неиспользуемых импортов в коде и т.д.
- __Dependencies__ - содержит список зависимостей, используемых в проекте
- __plugins.sbt__ - содержит список подключенных плагинов сборщика sbt(возможно создание собственных плагинов и их использование)
- __ProjectSettings__ - содержит настройки для сборки проекта сборщиком sbt(можно расположить в файле build.sbt)

### Папка src/main/resources

- __application.conf__ - файл с конфигами проекта в формате HOCON, парсится библиотекой [pure-config](https://pureconfig.github.io/docs/)

### Папка src/main/scala

Содержит набор пакетов и классов программы

- __SparkApp.scala__ - файл с точкой входа в программу(метод __main(args: Array[String])__)

### Папка src/test/scala

Содержит тесты и тестовые данные

### Сборка Fat JAR

Для создания Fat JAR необходимо выполнить:
```bash
sbt clean; + assembly
```
Выполнение команды assembly сначала производит компиляцию проекта, если она прошла успешно, то прогоняет все тесты и если они прошли успешно, собирает Fat JAR 

### Пример деплоя джобы(spark-submit)

```bash
spark-submit \
--class SparkApp
--master local \
./target/scala-2.12/spark-education-job-assembly-0.1.jar
```

### Запуск в локальном кластере

В проекте приложен файл docker-compose.yml.
Этапы:
- выполнить сборку Fat JAR и получить spark-education-job-assembly-0.1.jar в папке /target/scala-2.12/
```bash
sbt assembly
```
- перейти в терминал и из корня проекта выполнить команду
```bash
docker compose up
```
- перейти в контейнер spark-master 
  В IDE открыть дополнительнй терминал(Alt+F12) и выполнить следующие команды
```bash
# посмотреть список запущенных контейнеров
docker ps
# скопировать Container ID для spark-master
# перейти в контейнер spark-master и запустить bash
docker exec -it CONTAINER_ID bash
```
 или в Docker Desktop в разделе Containers выбрать контейнер spark-master и войти в терминал
- в терминале выполнить команды
```bash
# перейти в каталог с jar файлом spark-education.jar
cd opt/spark-apps
# выполнить spark-submit
/spark/bin/spark-submit --class SparkApp \
  --master spark://spark-master:7077 \
  --packages za.co.absa.spline.agent.spark:spark-3.3-spline-agent-bundle_2.12:2.0.0 \
  --conf "spark.sql.queryExecutionListeners=za.co.absa.spline.harvester.listener.SplineQueryExecutionListener" \
  --conf "spark.spline.lineageDispatcher.http.producer.url=http://rest-server:8080/producer" \
  --conf spark.metrics.conf=/opt/spark-apps/data/metrics.properties \
  --conf "spark.driver.extraJavaOptions=-javaagent:/opt/spark-apps/data/jmx_prometheus_javaagent-0.19.0.jar=9208:/opt/spark-apps/data/jmx-exporter.yml" \
  spark-education.jar
```
- результат работы джобы будет находиться в каталоге контейнера
```bash
/opt/spark-apps/data/result
```
- ui spark master доступен по адресу
```bash
http://localhost:8080/
```
- метрики доступны по адресу
```bash
Grafana - http://localhost:3000/ 
логин - admin
пароль - grafana
dashboards - Spark Dashboard, Spark Measure Dashboard

Prometheus - http://localhost:9090/
```

- spline ui доступен по адресу
```bash
http://localhost:9092/
```