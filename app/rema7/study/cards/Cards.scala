package rema7.study.cards

import models.study.flashcards.{FlashCardJson, FlashCard}
import services.study.cards.FlashCardService

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 26.05.15.
 */
class Cards {
  def create() = {
    println("AAAAAA")
  }
  def create(userId: String, flipCard: FlashCardJson): Future[FlashCard] = {
    FlashCardService.create(userId, flipCard)
  }
}
