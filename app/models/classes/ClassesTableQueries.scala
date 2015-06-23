package models.classes

import slick.lifted.TableQuery

/**
 * Created by m.cherkasov on 28.05.15.
 */
object ClassesTableQueries {
  object classes extends TableQuery(new StudyClasses(_))
}
