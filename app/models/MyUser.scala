package models

import securesocial.core.BasicProfile

/**
 * Created by maximcherkasov on 28.04.15.
 */
case class MyUser(main: BasicProfile, identities: List[BasicProfile])