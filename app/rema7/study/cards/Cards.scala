package rema7.study.cards

import models.study.flashcards.FlashCard
import services.study.cards.FlipCardService

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 26.05.15.
 */
class Cards {
  def create() = {
    println("AAAAAA")
  }
  def create(userId: String, flipCard: FlashCard): Future[FlashCard] = {
    FlipCardService.create(userId, flipCard)
  }
}
