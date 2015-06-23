package models.group

import slick.lifted.TableQuery

/**
 * Created by m.cherkasov on 28.05.15.
 */
object GroupTableQueries {
  object groups extends TableQuery(new StudyGroups(_))
}
