package models.study.flashcards

import slick.driver.MySQLDriver.api._
/**
 * Created by m.cherkasov on 28.05.15.
 */
object FlashCardsTableQueries {
  object cards extends TableQuery(new FlashCards(_))
  object cardsSets extends TableQuery(new CardsSets(_))
}
