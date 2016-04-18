package backend.base.util

import java.util.concurrent.{TimeUnit, SynchronousQueue, ThreadPoolExecutor, Executors}

import backend.base.init.GlobalConfigFactory
import com.twitter.util.FuturePool

/**
 *  14-12-12.
 */
object ActionExecutorProvider {
 lazy val futurePool=FuturePool(Executors.newFixedThreadPool(4))
}
