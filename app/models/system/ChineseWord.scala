package models.system

/**
 * Created by m.cherkasov on 07.07.15.
 */
import com.github.tototoshi.slick.MySQLJodaSupport._
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._

import scala.language.implicitConversions


case class ChineseWord(id: Long,
                             traditional: String,
                             simplified: String,
                             pinyin: String,
                             pinyin_search: String)

class ChineseWords(tag: Tag) extends Table[ChineseWord](tag, "chinese_dictionary") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def traditional = column[String]("traditional")
  def simplified = column[String]("simplified")
  def pinyin = column[String]("pinyin")
  def pinyin_search = column[String]("pinyin_search")


  def * = (id, traditional, simplified, pinyin, pinyin_search) <>(ChineseWord.tupled, ChineseWord.unapply)
}

