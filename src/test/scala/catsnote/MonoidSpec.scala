package catsnote

import org.scalatest.{Matchers, WordSpec}
import cats.implicits._
import cats.kernel.Monoid
/**
  * Created by edinakim on 2016. 7. 20..
  */
class MonoidSpec extends WordSpec with Matchers {
  "monoid group test" should {
    "combineAll" in {
      Monoid[String].empty should be("")
      Monoid[String].combineAll(List("a", "b", "c")) should be("abc")
      Monoid[String].combineAll(List()) should be("")
    }

    "monoid" in {
      Monoid[Map[String, Int]].combineAll(List(Map("a" → 1, "b" → 2), Map("a" → 3))) should be(
        Map("a" -> 4, "b" -> 2)
      )
      Monoid[Map[String, Int]].combineAll(List()) should be(
        Map()
      )
    }
  }

  "foldmap" in {
    val l = List(1, 2, 3, 4, 5)
    l.foldMap(identity) should be(15)
    l.foldMap(i ⇒ i.toString) should be("12345")
  }

  "foldmap2" in {
    val l = List(1, 2, 3, 4, 5)
    l.foldMap(i ⇒ (i, i.toString)) should be(15, "12345")
  }
}