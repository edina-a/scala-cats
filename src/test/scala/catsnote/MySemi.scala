package catsnote

import org.scalatest.{Matchers, WordSpec}

/**
  * Created by edinakim on 2016. 7. 20..
  */
class MySemi extends WordSpec with Matchers {

  "edina semigroup" should {
    "um" in {
      import cats.Semigroup
      case class Foo(a:Int, b:String)
      implicit val fooSemigroup = new Semigroup[Foo] {
        override def combine(x: Foo, y: Foo): Foo = Foo(x.a + y.a, x.b + y.b)
      }

      val foo1 = Foo(1, "a")
      val foo2 = Foo(2, "b")
      Semigroup[Foo].combine(foo1, foo2) should be(Foo(3, "ab"))

      import cats.implicits._

      foo1 |+| foo2 should be(Foo(3, "ab"))
    }
  }

}
