import cn.changhong.lazystore.Start
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future

/**
 * Created by yangguo on 15-2-9.
 */
object StartLazystore extends App{
  Start.main(Array("localhost","10002","lazystore_test_01","10.9.52.31","6379"))

}

