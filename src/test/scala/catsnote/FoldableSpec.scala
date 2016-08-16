package catsnote

import cats.Foldable
import org.scalatest.{Matchers, WordSpec}
import cats.implicits._

/**
  * Created by edinakim on 2016. 8. 11..
  */
class FoldableSpec extends WordSpec with Matchers {
  "fold left" in {
    Foldable[List].foldLeft(List(1, 2, 3), 0)(_ + _) should be(6)
    Foldable[List].foldLeft(List("a", "b", "c"), "")(_ + _) should be("abc")
  }

  "foldable" in {
    import cats.std.list.listInstance

    // foldLeft(연산 대상, 초기값){case x => x}
    val fold = Foldable[List].foldLeft(List(1, 2, 3, 4, 5), 0)(_ + _)
    val fold1 = Foldable[List].foldLeft((1 to 5).toList, 0) {
      case (acc, x) => acc + x
    }
    println(fold)
    println(fold1)

    // 1~5의 리스트가 있고 이중에 짝수의 값만 추려서 list 로 뽑기
    Foldable[List].foldLeft(List(1, 2, 3, 4, 5), List[Int]()) {
      case (acc, x) => if(x % 2 == 0) acc ::: List(x) else acc
    }

    // 1~5의 리스트가 있고 + 10 => List(11,12,13,14,15)
    def myMap(list: List[Int])(f: Int => Int):List[Int] = {
      Foldable[List].foldLeft(list, List[Int]()) {
        case (acc, x) => acc ::: List(f(x))
      }
    }

    println(myMap(List(1,2,3,4,5))((x:Int) => x + 10))



  }
}
