# scala-cats

> https://www.scala-exercises.org/cats


## Semigroup
결합법칙이 성립되는 연산  
![semigroup](https://gift-s.kakaocdn.net/dn/gift/playground/semigroup.png)
```scala
Semigroup[Option[Int]].combine(Option(1), Option(2)) should be(Option(3))
```

## Monoid
Semigroup의 combine에서 Empty를 가짐  
![monoid](https://gift-s.kakaocdn.net/dn/gift/playground/monoid.png)
```scala
Monoid[String].combineAll(List()) should be("")
```

## Functor
map(값을 꺼내어 f를 적용하고 다시 넣는 과정) 을 가짐   
![functor](https://gift-s.kakaocdn.net/dn/gift/playground/functor.png)
```scala
val intToString: Int ⇒ String = _.toString
Apply[Option].map(Some(1))(intToString) should be(Some("1"))
```

## Apply
Functor형 클래스를 확장   
![apply](https://gift-s.kakaocdn.net/dn/gift/playground/apply.png)
```scala
val intToString: Int ⇒ String = _.toString
Apply[Option].ap(Some(intToString))(Some(1)) should be(Some("1"))

val addArity2 = (a: Int, b: Int) ⇒ a + b
Apply[Option].ap2(Some(addArity2))(Some(1), Some(2)) should be(Some(3))
```


