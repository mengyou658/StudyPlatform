package services.business

import models.WithDefaultSession
import models.business.BusinessProfile
import securesocial.core.{BasicProfile, PasswordInfo}
import securesocial.core.providers.MailToken
import securesocial.core.services.{SaveMode, UserService}

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 08.05.15.
 */
class BusinessUserService  with  WithDefaultSession{
//  override def find(providerId: String, userId: String): Future[Option[BasicProfile]] = ???
//
//  override def findByEmailAndProvider(email: String, providerId: String): Future[Option[BasicProfile]] = ???
//
//  override def deleteToken(uuid: String): Future[Option[MailToken]] = ???
//
//  override def link(current: BusinessProfile, to: BasicProfile): Future[BusinessProfile] = ???
//
//  override def passwordInfoFor(user: BusinessProfile): Future[Option[PasswordInfo]] = ???
//
//  override def save(profile: BasicProfile, mode: SaveMode): Future[BusinessProfile] = ???
//
//  override def findToken(token: String): Future[Option[MailToken]] = ???
//
//  override def deleteExpiredTokens(): Unit = ???
//
//  override def updatePasswordInfo(user: BusinessProfile, info: PasswordInfo): Future[Option[BasicProfile]] = ???
//
//  override def saveToken(token: MailToken): Future[MailToken] = ???
}
