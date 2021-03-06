package objsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {
  trait TestSets {
    val set1 = new Empty
    val set2 = set1.incl(new Tweet("a", "a body", 20))
    val set3 = set2.incl(new Tweet("b", "b body", 20))
    val c = new Tweet("c", "c body", 7)
    val d = new Tweet("d", "d body", 9)
    val set4c = set3.incl(c)
    val set4d = set3.incl(d)
    val set5 = set4c.incl(d)
    
    val e = new Tweet("e", "e body", 9)
    val f = new Tweet("f", "f body", 9)
    val g = new Tweet("g", "g body", 9)
    val h = new Tweet("h", "h body", 9)
    val i = new Tweet("i", "i body", 9)
    val j = new Tweet("j", "j body", 9)
    val k = new Tweet("k", "k body", 9)
    val l = new Tweet("l", "l body", 9)
    val m = new Tweet("m", "m body", 9)
    val n = new Tweet("n", "n body", 9)
    val o = new Tweet("o", "o body", 9)
    val p = new Tweet("p", "p body", 9)
    val q = new Tweet("q", "q body", 9)
    val r = new Tweet("r", "r body", 9)
    val s = new Tweet("s", "s body", 9)
    val t = new Tweet("t", "t body", 9)
    val u = new Tweet("u", "u body", 9)
    val v = new Tweet("v", "v body", 9)
    val w = new Tweet("w", "w body", 9)
    val x = new Tweet("x", "x body", 9)
    val y = new Tweet("y", "y body", 9)
    val z = new Tweet("z", "z body", 9)
    val set6 = set5.incl(e).incl(f).incl(g).incl(h).incl(i).incl(j).incl(k).incl(l).incl(m).incl(n).incl(o)
    val set7 = set5.incl(p).incl(q).incl(r).incl(s).incl(t).incl(u).incl(v).incl(w).incl(x).incl(y).incl(z)
    
    
    val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus") // 40
    val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad") //185

    lazy val googleTweets: TweetSet = getRelevantTweets(google)
    lazy val appleTweets: TweetSet = getRelevantTweets(apple)
  
    /**
     * A list of all tweets mentioning a keyword from either apple or google,
     * sorted by the number of retweets.
     */
    lazy val trending: TweetList = googleTweets.union(appleTweets).descendingByRetweet

    lazy val preFilledAllTweets = TweetReader.allTweets

    def getRelevantTweets(list: List[String]): TweetSet = {
            preFilledAllTweets.filter(p => containsWord(p.text, list))
    }

    def containsWord(text: String, list: List[String]): Boolean = {
            if (list.isEmpty) false
            else if (text.contains(list.head)) true
            else containsWord(text, list.tail)
    }
  }

  def asSet(tweets: TweetSet): Set[Tweet] = {
    var res = Set[Tweet]()
    tweets.foreach(res += _)
    res
  }

  def size(set: TweetSet): Int = asSet(set).size

  test("filter: on empty set") {
    new TestSets {
      assert(size(set1.filter(tw => tw.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.user == "a")) === 1)
    }
  }

  test("filter: 20 on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.retweets == 20)) === 2)
    }
  }

  test("filter: 150 apple tweets") {
    new TestSets {
      assert(size(appleTweets) === 150)
    }
  }

  test("filter: 38 google tweets") {
    new TestSets {
      assert(size(googleTweets) === 38)
    }
  }

  test("filter: 9 on alphabet") {
    new TestSets {
      assert(size(set6.union(set7).filter(tw => tw.retweets == 9)) === 23)
    }
  }

  test("union: set4c and set4d") {
    new TestSets {
      assert(size(set4c.union(set4d)) === 4)
    }
  }

  test("union: set4c and set6") {
    new TestSets {
      assert(size(set4c.union(set6)) === 15)
    }
  }

  test("union: set6 and set7") {
    new TestSets {
      assert(size(set7.union(set6)) === 26)
    }
  }

  test("union: with empty set (1)") {
    new TestSets {
      assert(size(set5.union(set1)) === 4)
    }
  }

  test("union: with empty set (2)") {
    new TestSets {
      assert(size(set1.union(set5)) === 4)
    }
  }

  test("descending: set5") {
    new TestSets {
      val trends = set5.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "a" || trends.head.user == "b")
    }
  }

  }
