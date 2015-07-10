package models.user

import securesocial.core._

import scala.language.implicitConversions

/**
 * Created by maximcherkasov on 28.04.15.
 */

case class BasicUser(main: BasicProfile, account: Account, identities: List[BasicProfile])

