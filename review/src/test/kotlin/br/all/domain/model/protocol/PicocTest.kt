package br.all.domain.model.protocol

import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

@Tag("UnitTest")
class PicocTest {
    @Nested
    @Tag("ValidClasses")
    @DisplayName("When providing valid entries")
    inner class WhenProvidingValidEntries {
        @Test
        fun `should create a valid PICOC without context`() {
            assertDoesNotThrow {
                Picoc("Population", "Intervention", "Control", "Outcome")
            }
        }

        @Test
        fun `should create a valid PICOC with a specified context`() {
            assertDoesNotThrow {
                Picoc(
                    population = "Population",
                    intervention = "Intervention",
                    control = "Control",
                    outcome = "Outcome",
                    context = "Context",
                )
            }
        }
    }

    @Nested
    @Tag("InvalidClasses")
    @DisplayName("When invalid arguments are provided")
    inner class WhenInvalidArgumentsAreProvided {
        @ParameterizedTest(name = "[{index}] population=\"{0}\"")
        @ValueSource(strings = ["", " ", "   "])
        fun `should not create a PICOC with blank population`(population: String) {
            val exception = assertThrows<IllegalArgumentException> {
                Picoc(population, "Intervention", "Control", "Outcome", "Context")
            }
            assertEquals("The population described in the PICOC must not be blank!", exception.message)
        }

        @ParameterizedTest(name = "[{index}] intervention=\"{0}\"")
        @ValueSource(strings = ["", " ", "   "])
        fun `should not create a PICOC with blank intervention`(intervention: String) {
            val exception = assertThrows<IllegalArgumentException> {
                Picoc("Population", intervention, "Control", "Outcome", "Context")
            }
            assertEquals("The intervention described in the PICOC must not be blank!", exception.message)
        }

        @ParameterizedTest(name = "[{index}] control=\"{0}\"")
        @ValueSource(strings = ["", " ", "   "])
        fun `should not create a PICOC with blank control`(control: String) {
            val exception = assertThrows<IllegalArgumentException> {
                Picoc("Population", "Intervention", control, "Outcome", "Context")
            }
            assertEquals("The control described in the PICOC must not be blank!", exception.message)
        }

        @ParameterizedTest(name = "[{index}] outcome=\"{0}\"")
        @ValueSource(strings = ["", " ", "   "])
        fun `should not create a PICOC with blank outcome`(outcome: String) {
            val exception = assertThrows<IllegalArgumentException> {
                Picoc("Population", "Intervention", "Control", outcome, "Context")
            }
            assertEquals("The outcome described in the PICOC must not be blank!", exception.message)
        }

        @ParameterizedTest(name = "[{index}] context=\"{0}\"")
        @ValueSource(strings = ["", " ", "   "])
        fun `should not create a PICOC providing a invalid context`(context: String) {
            val exception = assertThrows<IllegalArgumentException> {
                Picoc("Population", "Intervention", "Control", "Outcome", context)
            }
            assertEquals("The context, when provided, must not be blank!", exception.message)
        }
    }
}
