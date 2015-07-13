package models.teacher

import slick.lifted.TableQuery

/**
 * Created by m.cherkasov on 28.05.15.
 */
object TeacherTableQueries {
  object teachers extends TableQuery(new Teachers(_))
}
