package models.system

import com.github.tototoshi.slick.MySQLJodaSupport._
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._

import scala.language.implicitConversions


case class Lang(id: Long,
                    userId: Long,
                    name: String,
                    code: String,
                    created: DateTime,
                    updated: DateTime)

class Langs(tag: Tag) extends Table[Lang](tag, "languages") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def name = column[String]("name")
  def code = column[String]("code")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (id, userId, name, code, created, updated) <> (Lang.tupled, Lang.unapply)
}

case class LangJson(id: Long,
                    name: String,
                    code: String
                     )