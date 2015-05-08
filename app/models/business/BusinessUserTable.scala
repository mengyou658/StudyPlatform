package models.business

import models.WithDefaultSession

import scala.slick.lifted.TableQuery

/**
 * Created by m.cherkasov on 08.05.15.
 */
object BusinessUserTable extends WithDefaultSession {
  object profiles extends TableQuery(new BusinessProfiles(_))
}
