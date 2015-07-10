package models.courses

import slick.lifted.TableQuery

/**
 * Created by m.cherkasov on 28.05.15.
 */
object CoursesTableQueries {
  object courses extends TableQuery(new Courses(_))
}
