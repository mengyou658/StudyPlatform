package models.study.cards

import models.study.cards.FlipCardListTableQueries.flipCardList
import models.study.cards.FlipCardsTableQueries.flipCards
import slick.driver.MySQLDriver.api._
/**
 * Created by m.cherkasov on 27.05.15.
 */
case class CardsLists(cardId: Long, listId: Long)


class CardsListsTable(tag: Tag)
  extends Table[CardsLists](tag, "cards_lists") {

  def cardId = column[Long]("card_id")
  def listId = column[Long]("list_id")

  def * = (
    cardId,
    listId) <> (CardsLists.tupled, CardsLists.unapply)

  def eventFk = foreignKey("card_fk", cardId, flipCards)(e => e.id)
  def interestFk = foreignKey("list_fk", listId, flipCardList)(i => i.id)
}