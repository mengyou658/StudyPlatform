package models

import models.UserTableQueries.{passwords, oauth1s, oauth2s}
import securesocial.core._
import scala.language.implicitConversions

/**
 * Created by maximcherkasov on 28.04.15.
 */

case class BasicUser(main: BasicProfile, identities: List[BasicProfile])

