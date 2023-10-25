package src.main.Scala



object simulation_of_the_experiment extends App {

  val Urn = List(1, 1, 1, 0, 0, 0)
  def takeRandomN[Int](as: List[Int]) = {
    scala.util.Random.shuffle(as).take(2).map(e => if (e == 1) true else false)
  }
  val collection = (1 to 10000).map(_ => takeRandomN(Urn)).filter(_.contains(true))
  val ver = collection.length / 10000f.toDouble
    println(ver)
}