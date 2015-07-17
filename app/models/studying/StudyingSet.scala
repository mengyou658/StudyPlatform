package models.studying
/**
 * Created by m.cherkasov on 17.07.15.
 */

import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

case class StudyingSet(id: Option[Long] = None,
                       userId: Long,
                       setId: Long,
                       created: DateTime,
                       updated: DateTime)

class StudyingSets(tag: Tag) extends Table[StudyingSet](tag, "studying_sets") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def setId = column[Long]("setId")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (id.?, userId, setId, created, updated) <> (StudyingSet.tupled, StudyingSet.unapply)
}

case class FlashcardsInStudyingSet(flashCardId: Long,
                                    setId: Long,
                                    status: Boolean,
                                    created: DateTime,
                                    updated: DateTime)

class FlashcardsInStudyingSets(tag: Tag) extends Table[FlashcardsInStudyingSet](tag, "flashcards_in_studying_sets") {
  def flashCardId = column[Long]("flashCardId")
  def setId = column[Long]("setId")
  def status = column[Boolean]("status")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (flashCardId, setId, status, created, updated) <>
    (FlashcardsInStudyingSet.tupled, FlashcardsInStudyingSet.unapply)
}

object studyingSets extends TableQuery(new StudyingSets(_))
object cardsSets extends TableQuery(new FlashcardsInStudyingSets(_))
