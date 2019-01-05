package functionaltest.async

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

//TODO Write functional tests that proves that complete stack is async
class AsyncApplication {

    @Test
    fun `Given stuff`() = runBlocking {
        val executionTimeMultipleCallsAtTheSameTime = async {
            //Start a timer?
            makeMultipleCallsAtTheSameTime()
            return@async 1
        }
        val executionTimeMultipleCallsSequentially = async {
            //Start a timer?
            makeMultipleCallsSequentially()
            return@async 3
        }

        Assertions.assertThat(executionTimeMultipleCallsAtTheSameTime.await()).`as`("Execution time of reactive streams driver is less then execution time of none async driver.").isLessThan(executionTimeMultipleCallsSequentially.await())
        Unit
    }

    fun makeMultipleCallsAtTheSameTime() {

    }

    fun makeMultipleCallsSequentially() {

    }
}
