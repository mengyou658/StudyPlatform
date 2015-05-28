package models.study.flashcards

import models.study.flashcards.FlashCardsTableQueries.{categories, packs}
import slick.driver.MySQLDriver.api._
/**
 * Created by m.cherkasov on 27.05.15.
 */
case class PacksToCategories(packId: Long, categoryId: Long)

class PacksToCategoriesTable(tag: Tag)
  extends Table[PacksToCategories](tag, "flashcards_packs_categories") {

  def packId = column[Long]("pack_id")
  def categoryId = column[Long]("category_id")

  def * = (
    packId,
    categoryId) <> (PacksToCategories.tupled, PacksToCategories.unapply)

  def eventFk = foreignKey("pack_fk", packId, packs)(e => e.id)
  def interestFk = foreignKey("category_fk", categoryId, categories)(i => i.id)
}

