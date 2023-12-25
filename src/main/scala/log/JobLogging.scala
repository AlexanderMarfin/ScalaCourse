package log

import org.slf4j.{ Logger, LoggerFactory }

trait JobLogging {
  //получаем объект логгера
  protected val log: Logger = LoggerFactory.getLogger(this.getClass)

}
