package catsnote

import cats.{Apply, Functor}
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by edinakim on 2016. 7. 28..
  */
class ApplySpec extends WordSpec with Matchers {
  "apply" should {
    "test" in {
      import cats.std.list.listInstance
      println(Functor[List].map(List(1,2,3))(x=>List(x)))

      // F[A] map A => B > F[B]
      // F[A] ap F[A => B] > F[B]

      def foo(a:Option[Int]):Option[String] = a match {
        case Some(x) => Some(x.toString + "!!!")
        case None => None
      }
      println(foo(Option(10)))

      def foo1:Option[Int=>String] =
        Option(x => x.toString + "!!!")

      import cats.std.option.optionInstance
      println(Apply[Option].ap(foo1)(Option(10)))
    }

    "apply map" in {
      val intToString: Int => String = _.toString
      val double: Int => Int = _ * 2
      val addTwo: Int => Int = _ + 2

      import cats.std.option.optionInstance
      Apply[Option].map(Some(1))(intToString) should be(Some("1"))
      Apply[Option].map(Some(1))(double) should be(Some(2))
      Apply[Option].map(None)(double) should be(None)
    }

    "apply ap" in {
      val intToString: Int => String = _.toString
      val double: Int => Int = _ * 2
      val addTwo: Int => Int = _ + 2

      import cats.std.option.optionInstance
      Apply[Option].ap(Some(intToString))(Some(1)) should be(Some("1"))
      Apply[Option].ap(Some(double))(Some(1)) should be(Some(2))
      Apply[Option].ap(Some(double))(None) should be(None)
      Apply[Option].ap(None)(Some(1)) should be(None)
      Apply[Option].ap(None)(None) should be(None)
    }

    "ap2 ap3" in {
      import cats.std.option.optionInstance

      val addArity2 = (a: Int, b: Int) => a + b
      Apply[Option].ap2(Some(addArity2))(Some(1), Some(2)) should be(Some(3))
      Apply[Option].ap2(Some(addArity2))(Some(1), None) should be(None)

      val addArity3 = (a: Int, b: Int, c: Int) => a + b + c
      Apply[Option].ap3(Some(addArity3))(Some(1), Some(2), Some(3)) should be(Some(6))
    }

    "apply list instance" in {
      implicit val listApplyInstance = new Apply[List] {
        override def ap[A, B](ff: List[(A) => B])(fa: List[A]): List[B] = {
          fa.flatMap(a => ff.map(f => f(a)))
        }

        override def map[A, B](fa: List[A])(f: (A) => B): List[B] = {
          fa map f
        }

        import cats.std.list.listInstance
        println(Apply[List].ap(List((x:Int)=>x.toString + "!!"))(List(1,2,3)))
      }
    }
  }
}
