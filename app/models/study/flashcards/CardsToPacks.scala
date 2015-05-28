package models.study.flashcards

import models.study.flashcards.FlashCardsTableQueries.{packs, cards}
import slick.driver.MySQLDriver.api._
/**
 * Created by m.cherkasov on 27.05.15.
 */
case class CardsToPacks(cardId: Long, packId: Long)

class CardsToPacksTable(tag: Tag)
  extends Table[CardsToPacks](tag, "flashcards_packs") {

  def cardId = column[Long]("card_id")
  def packId = column[Long]("pack_id")

  def * = (
    cardId,
    packId) <> (CardsToPacks.tupled, CardsToPacks.unapply)

  def eventFk = foreignKey("card_fk", cardId, cards)(e => e.id)
  def interestFk = foreignKey("pack_fk", packId, packs)(i => i.id)
}

