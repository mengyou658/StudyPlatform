package models

/**
 * Created by m.cherkasov on 22.04.15.
 */

object User {
  val users = List(
    User("bob@tunnelbear.com", "password", isPremium = true, 10),
    User("alice@tunnelbear.com", "password", isPremium = true, 0),
    User("jane@tunnelbear.com", "password", isPremium = false, 10)
  )
  def find(username: String):Option[User] = users.find(_.username == username)
}

case class User(username:String, password:String, isPremium:Boolean, balance:Int) {
  def checkPassword(password: String): Boolean = this.password == password
}