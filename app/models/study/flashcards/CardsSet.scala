package models.study.flashcards

import models.system.LangJson
import models.system.SystemTableQueries.langs
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 26.05.15.
 */
case class CardsSet(id: Option[Long] = None,
                         userId: Long,
                         name: String,
                         description: Option[String] = None,
                         termsLangId: Long,
                         definitionsLangId: Long,
                         created: DateTime,
                         updated: DateTime)

class CardsSets(tag: Tag) extends Table[CardsSet](tag, "cards_sets") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def name = column[String]("name")
  def description = column[String]("description")
  def termsLangId = column[Long]("termsLangId")
  def definitionsLangId = column[Long]("definitionsLangId")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (id.?, userId, name, description.?, termsLangId, definitionsLangId,created, updated) <> (CardsSet.tupled, CardsSet.unapply)

  def termsLangFk = foreignKey("term_lang_fk", termsLangId, langs)(l => l.id)
  def definitionsLangFk = foreignKey("definition_lang_fk", definitionsLangId, langs)(l => l.id)

}

case class CardsSetJson(id: Option[Long] = None,
                        name: String,
                        description: Option[String] = None,
                        termsLang: LangJson,
                        definitionsLang: LangJson
                         )






