package earth.diego.hindsight.mobile.transcribe

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class ForegroundNotificationIdsTest {
    @Test fun `concurrent workers get distinct nonzero notification identities`() {
        val executor = Executors.newFixedThreadPool(8)
        try {
            val ids = executor.invokeAll(List(8) {
                Callable { List(128) { ForegroundNotificationIds.next() } }
            }).flatMap { it.get() }
            assertTrue(ids.all { it > 0 })
            assertEquals(ids.size, ids.toSet().size)
        } finally {
            executor.shutdownNow()
        }
    }
}
