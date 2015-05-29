package models.study.flashcards

import slick.lifted.TableQuery

/**
 * Created by m.cherkasov on 28.05.15.
 */
object FlashCardsTableQueries {
  object categories extends TableQuery(new Categories(_))
  object cards extends TableQuery(new FlashCards(_))
  object packs extends TableQuery(new FlashCardsPackTable(_))

}
