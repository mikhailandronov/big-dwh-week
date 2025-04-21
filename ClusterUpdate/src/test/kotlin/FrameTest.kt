import org.ama.contest.Frame
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class FrameTest {

    @Test
    fun `Should return true if two frames have intersection`() {
        // Given
        val first = Frame(1, 4)
        val second = Frame(4, 11)
        // When
        // Then
        assertTrue(first.intersects(second))
        assertTrue(second.intersects(first))
    }

    @Test
    fun `Should return false if two frames have no intersection`() {
        // Given
        val first = Frame(8, 3)
        val second = Frame(11, 5)
        // When
        // Then
        assertFalse(first.intersects(second))
        assertFalse(second.intersects(first))
    }

    @Test
    fun `Should return true if frame has intersection with set of frames`() {
        // Given
        val theFrame = Frame(1, 4)
        val theSet = setOf(
            Frame(4, 11),
            Frame(8, 3),
            Frame(12, 5)
        )
        // When
        // Then
        assertTrue(theFrame.intersects(theSet))
    }

    @Test
    fun `Should return false if frame has no intersections with set of frames`() {
        // Given
        val theFrame = Frame(1, 4)
        val theSet = setOf(
            Frame(5, 2),
            Frame(8, 3),
            Frame(12, 5)
        )
        // When
        // Then
        assertFalse(theFrame.intersects(theSet))
    }

    @Test
    fun `Should return false if set of frames is empty`() {
        // Given
        val theFrame = Frame(1, 4)
        val theSet = setOf<Frame>()
        // When
        // Then
        assertFalse(theFrame.intersects(theSet))
    }
}