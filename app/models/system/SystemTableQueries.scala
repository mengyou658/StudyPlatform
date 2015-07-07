package models.system

/**
 * Created by m.cherkasov on 19.06.15.
 */
import slick.lifted.TableQuery


object SystemTableQueries {
  object langs extends TableQuery(new Langs(_))
  object chineseDictionary extends TableQuery(new ChineseWords(_))
}
