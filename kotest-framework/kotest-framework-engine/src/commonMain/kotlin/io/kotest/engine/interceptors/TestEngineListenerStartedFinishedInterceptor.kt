package io.kotest.engine.interceptors

import io.kotest.engine.EngineResult
import io.kotest.engine.TestSuite
import io.kotest.mpp.log

/**
 * Notifies the test listener that the engine is ready to execute tests, and the final [TestSuite]
 * is ready to be used, and that all tests have completed, with any unexpected errors.
 */
internal object TestEngineListenerStartedFinishedInterceptor : EngineInterceptor {

   override suspend fun intercept(
      context: EngineContext,
      execute: suspend (EngineContext) -> EngineResult
   ): EngineResult {

      context.listener.engineStarted(context.suite.specs.map { it.kclass })
      val result = execute(context)

      result.errors.forEach {
         log(it) { "TestEngineListenerStartedFinishedInterceptor: Error during test engine run" }
         it.printStackTrace()
      }

      context.listener.engineFinished(result.errors)
      return result
   }
}
