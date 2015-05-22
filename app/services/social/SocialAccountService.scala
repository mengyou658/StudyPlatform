package services.social

import models.WithDefaultSession
import models.social.SocialAccount
import models.social.SocialAccountsTableQueries.socialAccounts
import play.api.Logger
import scala.slick.driver.MySQLDriver.simple._
import scala.concurrent.Future

/**
 * Created by m.cherkasov on 22.05.15.
 */
class SocialAccountService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def findByUserId(userId: String): Future[List[SocialAccount]] = withSession {
    implicit session =>
      Future successful {
        logger.info("Find all social accounts for current user")

        socialAccounts
          .filter(sa => sa.userId === userId)
          .list
      }
  }

  def findByUserIdAndProvider(userId: String, provider: String): Future[List[SocialAccount]] = withSession {
    implicit session =>
      Future successful {
        logger.info("Find all social accounts with provider for current user")

        socialAccounts
          .filter(sa => sa.userId === userId)
          .list
      }
  }

  def save(account: SocialAccount): Future[SocialAccount] = withSession {
    implicit session =>
      Future successful {
        val exitstsocialAccount = socialAccounts.filter(a => a.accountId === account.accountId).firstOption

        exitstsocialAccount match {
          case Some(ea) =>
            val acc = account.copy(id = ea.id)
            socialAccounts.filter(_.accountId === account.accountId).update(acc)
            acc
          case None =>
            socialAccounts += SocialAccount(
              None,
              account.userId,
              account.provider,
              account.accountId,
              account.accessToken,
              account.username,
              account.profilePicture
            )
            socialAccounts.filter(_.accountId === account.accountId).first
        }
      }
  }

}
