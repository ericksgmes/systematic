package br.all.domain.model.protocol

import br.all.domain.model.question.QuestionId
import br.all.domain.shared.valueobject.Language
import br.all.domain.shared.valueobject.Language.LangType
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Tag("UnitTest")
class ProtocolTest {
    private lateinit var factory: TestDataFactory

    @BeforeEach
    fun setUp() = run { factory = TestDataFactory() }

    @Nested
    @DisplayName("When writing protocols")
    inner class WhenWritingProtocols {
        @Nested
        @Tag("ValidClasses")
        @DisplayName("And filling out the field correctly")
        inner class AndFillingOutTheFieldCorrectly {
            @Test
            fun `should successfully create a protocol`() {
                assertDoesNotThrow { factory.createProtocol() }
            }
        }
        
        @Nested
        @Tag("InvalidClasses")
        @DisplayName("But providing invalid input")
        inner class ButProvidingInvalidInput {
            @ParameterizedTest(name = "[{index}] goal=\"{0}\"")
            @ValueSource(strings = ["", " ", "   "])
            fun `should throw when the goal is blank`(goal: String) {
                val e = assertThrows<IllegalArgumentException> { factory.createProtocol(goal = goal) }
                assertEquals("The goal cannot be an empty string", e.message)
            }

            @ParameterizedTest(name = "[{index}] justification=\"{0}\"")
            @ValueSource(strings = ["", " ", "   "])
            fun `should throw blank justification`(justification: String) {
                val e = assertThrows<IllegalArgumentException> { factory.createProtocol(justification = justification) }
                assertEquals("The justification cannot be an empty string", e.message)
            }

            @ParameterizedTest(name = "[{index}] searchString=\"{0}\"")
            @ValueSource(strings = ["", " ", "   "])
            fun `should throw if the search string is blank`(searchString: String) {
                val e = assertThrows<IllegalArgumentException> { factory.createProtocol(searchString = searchString) }
                assertEquals("The search string must not be blank!", e.message)
            }

            @ParameterizedTest(name = "[{index}] sourcesSelectionCriteria=\"{0}\"")
            @ValueSource(strings = ["", " ", "   "])
            fun `should not create protocols with blank sources selection criteria`(sourcesSelectionCriteria: String) {
                val e = assertThrows<IllegalArgumentException> {
                    factory.createProtocol(sourcesSelectionCriteria = sourcesSelectionCriteria)
                }
                assertEquals("The sources selection criteria description must not be blank", e.message)
            }

            @ParameterizedTest(name = "[{index}] searchMethod=\"{0}\"")
            @ValueSource(strings = ["", " ", "   "])
            fun `should not the search method be blank`(searchMethod: String) {
                val e = assertThrows<IllegalArgumentException> { factory.createProtocol(searchMethod = searchMethod) }
                assertEquals("The search method description must not be blank", e.message)
            }

            @ParameterizedTest(name = "[{index}] studyTypeDefinition=\"{0}\"")
            @ValueSource(strings = ["", " ", "   "])
            fun `should throw when the provided study type definition is blank`(studyTypeDefinition: String) {
                val e = assertThrows<IllegalArgumentException> {
                    factory.createProtocol(studyTypeDefinition = studyTypeDefinition)
                }
                assertEquals("The study type definition must not be blank", e.message)
            }

            @ParameterizedTest(name = "[{index}] selectionProcess=\"{0}\"")
            @ValueSource(strings = ["", " ", "   "])
            fun `should throw for blank selection process descriptions`(selectionProcess: String) {
                val e = assertThrows<IllegalArgumentException> {
                    factory.createProtocol(selectionProcess = selectionProcess)
                }
                assertEquals("The selection process description must not be blank", e.message)
            }

            @ParameterizedTest(name = "[{index}] dataCollectionProcess=\"{0}\"")
            @ValueSource(strings = ["", " ", "   "])
            fun `should not the data collection process description be blank`(dataCollectionProcess: String) {
                val e = assertThrows<IllegalArgumentException> {
                    factory.createProtocol(dataCollectionProcess = dataCollectionProcess)
                }
                assertEquals("The data collection process description must not be blank", e.message)
            }

            @ParameterizedTest(name = "[{index}] analysisAndSynthesis=\"{0}\"")
            @ValueSource(strings = ["", " ", "   "])
            fun `should not the analysis and synthesis description be blank`(analysisAndSynthesis: String) {
                val e = assertThrows<IllegalArgumentException> {
                    factory.createProtocol(analysisAndSynthesis = analysisAndSynthesis)
                }
                assertEquals("The analysis and synthesis process description must not be blank", e.message)
            }
        }
    }

    @Nested
    @DisplayName("When updating the protocol")
    inner class WhenUpdatingTheProtocol {
        @Nested
        @Tag("ValidClasses")
        @DisplayName("And providing valid changes")
        inner class AndProvidingValidChanges {
            @ParameterizedTest
            @CsvSource(",", "Old goal")
            fun `should update the protocol goal`(original: String?) {
                val sut = factory.createProtocol(goal = original)
                val newGoal = factory.text()

                assertAll(
                    { assertDoesNotThrow { sut.goal = newGoal } },
                    { assertEquals(newGoal, sut.goal) },
                )
            }

            @ParameterizedTest
            @CsvSource(",", "Old justification")
            fun `should the justification be updated`(original: String?) {
                val sut = factory.createProtocol(justification = original)
                val newJustification = factory.text()

                assertAll(
                    { assertDoesNotThrow { sut.justification = newJustification } },
                    { assertEquals(newJustification, sut.justification) },
                )
            }

            @ParameterizedTest
            @CsvSource(",", "Old search string")
            fun `should valid new search strings be accepted`(original: String?) {
                val sut = factory.createProtocol(searchString = original)
                val searchString = factory.text()

                assertAll(
                    { assertDoesNotThrow { sut.searchString = searchString } },
                    { assertEquals(searchString, sut.searchString) },
                )
            }

            @ParameterizedTest
            @CsvSource(",", "Old sources selection criteria")
            fun `should valid sources selection criteria be accepted`(original: String?) {
                val sut = factory.createProtocol(sourcesSelectionCriteria = original)
                val sourcesSelectionCriteria = factory.text()

                assertAll(
                    { assertDoesNotThrow { sut.sourcesSelectionCriteria = sourcesSelectionCriteria } },
                    { assertEquals(sourcesSelectionCriteria, sut.sourcesSelectionCriteria) },
                )
            }

            @ParameterizedTest
            @CsvSource(",", "Old search method")
            fun `should search method be updated to valid strings`(original: String?) {
                val sut = factory.createProtocol(searchMethod = original)
                val searchMethod = factory.text()

                assertAll(
                    { assertDoesNotThrow { sut.searchMethod = searchMethod } },
                    { assertEquals(searchMethod, sut.searchMethod) },
                )
            }

            @ParameterizedTest
            @CsvSource(",", "Old study type definition")
            fun `should not throw when providing non blank strings for study type definition`(original: String?) {
                val sut = factory.createProtocol(studyTypeDefinition = original)
                val studyTypeDefinition = factory.text()

                assertAll(
                    { assertDoesNotThrow { sut.studyTypeDefinition = studyTypeDefinition } },
                    { assertEquals(studyTypeDefinition, sut.studyTypeDefinition) },
                )
            }

            @ParameterizedTest
            @CsvSource(",", "Old selection process")
            fun `should valid strings update the protocol selection process`(original: String?) {
                val sut = factory.createProtocol(selectionProcess = original)
                val selectionProcess = factory.text()

                assertAll(
                    { assertDoesNotThrow { sut.selectionProcess = selectionProcess } },
                    { assertEquals(selectionProcess, sut.selectionProcess) },
                )
            }

            @ParameterizedTest
            @CsvSource(",", "Old data collection process")
            fun `should not throw if the provided new data collection process is valid`(original: String?) {
                val sut = factory.createProtocol(dataCollectionProcess = original)
                val dataCollectionProcess = factory.text()

                assertAll(
                    { assertDoesNotThrow { sut.dataCollectionProcess = dataCollectionProcess } },
                    { assertEquals(dataCollectionProcess, sut.dataCollectionProcess) },
                )
            }

            @ParameterizedTest
            @CsvSource(",", "Old data collection process")
            fun `should analysis and synthesis be updated to valid strings`(original: String?) {
                val sut = factory.createProtocol(analysisAndSynthesis = original)
                val analysisAndSynthesis = factory.text()

                assertAll(
                    { assertDoesNotThrow { sut.analysisAndSynthesisProcess = analysisAndSynthesis } },
                    { assertEquals(analysisAndSynthesis, sut.analysisAndSynthesisProcess) },
                )
            }
        }
    }

    @Nested
    @DisplayName("When managing research questions")
    inner class WhenManagingResearchQuestions {
        @Nested
        @Tag("ValidClasses")
        @DisplayName("And performing the updates successfully")
        inner class AndPerformingTheUpdatesSuccessfully {
            @Test
            fun `should add a valid nonexistent research question to protocol`() {
                val sut = factory.createProtocol(researchQuestions = emptySet())
                val question = ResearchQuestion(factory.text())

                sut.addResearchQuestion(question)
                assertEquals(1, sut.researchQuestions.size)
            }

            @Test
            fun `should do nothing when trying to add a duplicated research question`() {
                val sut = factory.createProtocol(researchQuestions = emptySet())
                val question = ResearchQuestion(factory.text())

                sut.addResearchQuestion(question)
                sut.addResearchQuestion(question)

                assertEquals(1, sut.researchQuestions.count { it == question })
            }

            @Test
            fun `should remove existent research questions`() {
                val question = ResearchQuestion(factory.text())
                val sut = factory.createProtocol(researchQuestions = setOf(question))

                sut.removeResearchQuestion(question)
                assertEquals(0, sut.researchQuestions.size)
            }

            @Test
            fun `should replace all research questions of the protocol`() {
                val keepingQuestion = ResearchQuestion(factory.text())
                val oldQuestions = mutableSetOf(keepingQuestion).also { it.addAll(factory.researchQuestions()) }
                val newQuestions = mutableSetOf(keepingQuestion).also { it.addAll(factory.researchQuestions()) }
                val removingElements = oldQuestions - newQuestions
                val sut = factory.createProtocol(researchQuestions = oldQuestions)

                sut.replaceResearchQuestions(newQuestions)

                assertAll(
                    { assertContains(sut.researchQuestions, keepingQuestion) },
                    { assertTrue(sut.researchQuestions.containsAll(newQuestions)) },
                    { assertFalse(sut.researchQuestions.containsAll(removingElements) ) },
                )
            }
        }

        @Nested
        @Tag("InvalidClasses")
        @DisplayName("And being unable to change them")
        inner class AndBeingUnableToChangeThem {
            @Test
            fun `should throw when trying to remove a research questions if none exists`() {
                val sut = factory.createProtocol(researchQuestions = emptySet())
                val question = ResearchQuestion(factory.text())
                assertThrows<IllegalStateException> { sut.removeResearchQuestion(question) }
            }

            @Test
            fun `should thrown when trying to remove a nonexistent research question`() {
                val sut = factory.createProtocol()
                val question = ResearchQuestion("Nonexistent question")
                assertThrows<NoSuchElementException> { sut.removeResearchQuestion(question) }
            }
        }
    }

    @Nested
    @DisplayName("When managing keywords")
    inner class WhenManagingKeywords {
        @Nested
        @Tag("ValidClasses")
        @DisplayName("And being able to change them")
        inner class AndBeingAbleToChangeThem {
            @Test
            fun `should add a new keyword if it is not in the protocol yet`() {
                val sut = factory.createProtocol(keywords = emptySet())
                val newKeyword = factory.text()

                sut.addKeyword(newKeyword)

                assertAll(
                    { assertEquals(1, sut.keywords.size) },
                    { assertContains(sut.keywords, newKeyword) }
                )
            }

            @Test
            fun `should do nothing when trying to add a keyword that already is in the protocol`() {
                val keyword = factory.text()
                val sut = factory.createProtocol(keywords = setOf(keyword))

                assertEquals(1, sut.keywords.count { it == keyword })
            }

            @Test
            fun `should successfully remove a keyword if it is not the last one`() {
                val removingKeyword = factory.text()
                val sut = factory.createProtocol(keywords = setOf(removingKeyword))

                sut.removeKeyword(removingKeyword)
                assertEquals(0, sut.keywords.size)
            }

            @Test
            fun `should replace all keywords of the protocol`() {
                val keepingKeyword = factory.text()
                val oldKeywords = mutableSetOf(keepingKeyword).also { it.addAll(factory.keywords(5)) }
                val newKeywords = mutableSetOf(keepingKeyword).also { it.addAll(factory.keywords(5)) }
                val removingElements = oldKeywords - newKeywords
                val sut = factory.createProtocol(keywords = oldKeywords)

                sut.replaceKeywords(newKeywords)

                assertAll(
                    { assertContains(sut.keywords, keepingKeyword) },
                    { assertTrue(sut.keywords.containsAll(newKeywords)) },
                    { assertFalse(sut.keywords.any { it in removingElements }) },
                )
            }
        }

        @Nested
        @DisplayName("And being unable to change any keyword")
        inner class AndBeingUnableToChangeAnyKeyword {
            @ParameterizedTest( name = "[{index}] keyword=\"{0}\"")
            @ValueSource(strings = ["", " ", "   "])
            fun `should blank keywords be not accepted`(keyword: String) {
                val sut = factory.createProtocol(keywords = emptySet())
                assertThrows<IllegalArgumentException> { sut.addKeyword(keyword) }
            }

            @Test
            fun `should throw when trying to remove keywords when none exists`() {
                val sut = factory.createProtocol(keywords = emptySet())
                assertThrows<IllegalStateException> { sut.removeKeyword(factory.text()) }
            }

            @Test
            fun `should throw when trying to remove an nonexistent keyword`() {
                val sut = factory.createProtocol(keywords = setOf("Existent keyword"))
                assertThrows<NoSuchElementException> { sut.removeKeyword(factory.text()) }
            }
        }
    }

    @Nested
    @DisplayName("When managing the protocol information sources")
    inner class WhenManagingTheProtocolInformationSources {
        @Nested
        @Tag("ValidClasses")
        @DisplayName("And being able to update them")
        inner class AndBeingAbleToUpdateThem {
            @Test
            fun `should add a information source if it is not in the protocol yet`() {
                val sut = factory.createProtocol(informationSources = emptySet())
                val newSearchSource = SearchSource(factory.text())

                sut.addInformationSource(newSearchSource)

                assertAll(
                    { assertEquals(1, sut.informationSources.size) },
                    { assertContains(sut.informationSources, newSearchSource) }
                )
            }

            @Test
            fun `should do nothing when trying to add a information source that already is in the protocol`() {
                val informationSource = SearchSource(factory.text())
                val sut = factory.createProtocol(informationSources = setOf(informationSource))

                sut.addInformationSource(informationSource)
                assertEquals(1, sut.informationSources.count { it == informationSource })
            }

            @Test
            fun `should successfully remove a information source`() {
                val removingSource = SearchSource(factory.text())
                val sut = factory.createProtocol(informationSources = setOf(removingSource))

                sut.removeInformationSource(removingSource)
                assertEquals(0, sut.informationSources.size)
            }

            @Test
            fun `should replaced every information source in the protocol`() {
                val keepingSource = SearchSource(factory.text())
                val oldSources = mutableSetOf(keepingSource).also { it.addAll(factory.informationSources()) }
                val newSources = mutableSetOf(keepingSource).also { it.addAll(factory.informationSources()) }
                val removingElements = oldSources - newSources
                val sut = factory.createProtocol(informationSources = oldSources)

                sut.replaceInformationSources(newSources)

                assertAll(
                    { assertContains(sut.informationSources, keepingSource) },
                    { assertTrue { sut.informationSources.containsAll(newSources) } },
                    { assertFalse { sut.informationSources.containsAll(removingElements) } },
                )
            }
        }

        @Nested
        @DisplayName("And failing to perform changes")
        inner class AndFailingToPerformChanges {
            @Test
            fun `should not be possible to remove information sources when none exists`() {
                val sut = factory.createProtocol(informationSources = emptySet())
                val source = SearchSource(factory.text())
                assertThrows<IllegalStateException> { sut.removeInformationSource(source) }
            }

            @Test
            fun `should throw when trying to remove a information source that does not exist`() {
                val sut = factory.createProtocol()
                val nonexistentSource = SearchSource("Nonexistent Source")

                assertThrows<NoSuchElementException> { sut.removeInformationSource(nonexistentSource) }
            }
        }
    }

    @Nested
    @DisplayName("When managing the studies languages")
    inner class WhenManagingTheStudiesLanguages {
        @Nested
        @Tag("ValidClasses")
        @DisplayName("And performing changes successfully")
        inner class AndPerformingChangesSuccessfully {
            @Test
            fun `should add a new language if it is not in the protocol`() {
                val sut = factory.createProtocol(languages = emptySet())
                val newLanguage = Language(LangType.PORTUGUESE)

                sut.addLanguage(newLanguage)

                assertAll(
                    { assertEquals(1, sut.studiesLanguages.size) },
                    { assertContains(sut.studiesLanguages, newLanguage) },
                )
            }

            @Test
            fun `should do nothing when trying to add a language that already is in the protocol`() {
                val existentLanguage = Language(LangType.ENGLISH)
                val sut = factory.createProtocol(languages = setOf(existentLanguage))

                sut.addLanguage(existentLanguage)
                assertEquals(1, sut.studiesLanguages.count { it == existentLanguage })
            }

            @Test
            fun `should successfully remove a language if it exists`() {
                val removingLanguage = Language(LangType.ENGLISH)
                val sut = factory.createProtocol(languages = setOf(removingLanguage))

                sut.removeLanguage(removingLanguage)
                assertEquals(0, sut.studiesLanguages.size)
            }

            @Test
            fun `should replace all languages defined in the protocol`() {
                val keepingLanguage = Language(LangType.ENGLISH)
                val oldLanguages = mutableSetOf(keepingLanguage).also { it.addAll(factory.languages()) }
                val newLanguages = mutableSetOf(keepingLanguage).also { it.addAll(factory.languages(10)) }
                val removingLanguages = oldLanguages - newLanguages
                val sut = factory.createProtocol(languages = oldLanguages)

                sut.replaceLanguages(newLanguages)

                assertAll(
                    { assertContains(sut.studiesLanguages, keepingLanguage) },
                    { assertTrue { sut.studiesLanguages.containsAll(newLanguages) } },
                    { assertFalse { sut.studiesLanguages.containsAll(removingLanguages) } },
                )
            }
        }

        @Nested
        @Tag("InvalidClasses")
        @DisplayName("And failing to update them")
        inner class AndFailingToUpdateThem {
            @Test
            fun `should thrown when trying to remove languages if none exists`() {
                val sut = factory.createProtocol(languages = emptySet())
                assertThrows<IllegalStateException> { sut.removeLanguage(Language(LangType.ENGLISH)) }
            }

            @Test
            fun `should throw when trying to remove a nonexistent language from protocol`() {
                val sut = factory.createProtocol(languages = setOf(Language(LangType.ENGLISH)))
                val nonexistentLanguage = Language(LangType.PORTUGUESE)

                assertThrows<NoSuchElementException> { sut.removeLanguage(nonexistentLanguage) }
            }
        }
    }

    @Nested
    @DisplayName("When managing eligibility criteria")
    inner class WhenManagingEligibilityCriteria {
        @Nested
        @Tag("ValidClasses")
        @DisplayName("And performing changes")
        inner class AndPerformingChanges {
            @Test
            fun `should add a new criteria if it is not in the protocol`() {
                val sut = factory.createProtocol(eligibilityCriteria = emptySet())
                val newCriterion = Criterion.toInclude(factory.text())

                sut.addEligibilityCriterion(newCriterion)

                assertAll(
                    { assertEquals(1, sut.eligibilityCriteria.size) },
                    { assertContains(sut.eligibilityCriteria, newCriterion) },
                )
            }

            @Test
            fun `should do nothing when trying to add a repeated criteria`() {
                val repeatedCriterion = Criterion.toInclude(factory.text())
                val sut = factory.createProtocol(eligibilityCriteria = setOf(repeatedCriterion))

                sut.addEligibilityCriterion(repeatedCriterion)
                assertEquals(1, sut.eligibilityCriteria.count { it == repeatedCriterion })
            }

            @Test
            fun `should remove a existent selection criterion`() {
                val criterion = Criterion.toInclude(factory.text())
                val sut = factory.createProtocol(eligibilityCriteria = setOf(criterion))

                sut.removeEligibilityCriterion(criterion)
                assertTrue(sut.eligibilityCriteria.isEmpty())
            }

            @Test
            fun `should replace all eligibility criteria in the protocol`() {
                val keepingCriterion = Criterion.toInclude(factory.text())
                val oldCriteria = mutableSetOf(keepingCriterion).also { it.addAll(factory.eligibilityCriteria()) }
                val newCriteria = mutableSetOf(keepingCriterion).also { it.addAll(factory.eligibilityCriteria()) }
                val removingCriteria = oldCriteria - newCriteria
                val sut = factory.createProtocol(eligibilityCriteria = oldCriteria)

                sut.replaceEligibilityCriteria(newCriteria)

                assertAll(
                    { assertContains(sut.eligibilityCriteria, keepingCriterion) },
                    { assertTrue { sut.eligibilityCriteria.containsAll(newCriteria) } },
                    { assertFalse { sut.eligibilityCriteria.containsAll(removingCriteria) } },
                )
            }
        }

        @Nested
        @Tag("InvalidClasses")
        @DisplayName("And failing to update them")
        inner class AndFailingToUpdateThem {
            @Test
            fun `should throw when trying to remove eligibility criteria but none exists`() {
                val sut = factory.createProtocol(eligibilityCriteria = emptySet())
                val criterion = Criterion.toInclude(factory.text())
                assertThrows<IllegalStateException> { sut.removeEligibilityCriterion(criterion) }
            }

            @Test
            fun `should throw when trying to remove a criteria that is not defined in the protocol`() {
                val sut = factory.createProtocol()
                val nonexistentCriterion = Criterion.toInclude("Nonexistent criterion")
                assertThrows<NoSuchElementException> { sut.removeEligibilityCriterion(nonexistentCriterion) }
            }
        }
    }

    @Nested
    @DisplayName("When managing extraction questions")
    inner class WhenManagingExtractionQuestions {
        @Nested
        @Tag("ValidClasses")
        @DisplayName("And being able to update them")
        inner class AndBeingAbleToUpdateThem {
            @Test
            fun `should add a new extraction question if it is not defined yet`() {
                val sut = factory.createProtocol(extractionQuestions = emptySet())
                val newExtractionQuestion = QuestionId(UUID.randomUUID())

                sut.addExtractionQuestion(newExtractionQuestion)

                assertAll(
                    { assertEquals(1, sut.extractionQuestions.size) },
                    { assertContains(sut.extractionQuestions, newExtractionQuestion) },
                )
            }

            @Test
            fun `should do nothing when trying to add a extraction question that has already been defined`() {
                val question = QuestionId(UUID.randomUUID())
                val sut = factory.createProtocol(extractionQuestions = setOf(question))

                sut.addExtractionQuestion(question)
                assertEquals(1, sut.extractionQuestions.count{ it == question })
            }

            @Test
            fun `should remove a extraction question if its present`() {
                val removingQuestion = QuestionId(UUID.randomUUID())
                val sut = factory.createProtocol(extractionQuestions = setOf(removingQuestion))

                sut.removeExtractionQuestion(removingQuestion)

                assertEquals(0, sut.extractionQuestions.size)
            }
        }

        @Nested
        @Tag("InvalidClasses")
        @DisplayName("And being unable to perform changes")
        inner class AndBeingUnableToPerformChanges {
            @Test
            fun `should not be possible to remove extractions from empty set`() {
                val sut = factory.createProtocol(extractionQuestions = emptySet())
                val question = QuestionId(UUID.randomUUID())
                assertThrows<IllegalStateException> { sut.removeExtractionQuestion(question) }
            }

            @Test
            fun `should throw when trying to remove a question that does not belongs to the protocol`() {
                val sut = factory.createProtocol()
                val removingQuestion = QuestionId(UUID.randomUUID())

                assertThrows<NoSuchElementException> { sut.removeExtractionQuestion(removingQuestion) }
            }
        }
    }

    @Nested
    @DisplayName("When managing risk of bias questions")
    inner class WhenManagingRiskOfBiasQuestions {
        @Nested
        @Tag("ValidClasses")
        @DisplayName("And providing valid changes")
        inner class AndProvidingValidChanges {
            @Test
            fun `should a new rob question successfully if it is not defined`() {
                val sut = factory.createProtocol(robQuestions = emptySet())
                val newRobQuestion = QuestionId(UUID.randomUUID())

                sut.addRobQuestion(newRobQuestion)

                assertAll(
                    { assertEquals(1, sut.robQuestions.size) },
                    { assertContains(sut.robQuestions, newRobQuestion) },
                )
            }

            @Test
            fun `should do nothing when trying to add repeated rob questions`() {
                val repeatedQuestion = QuestionId(UUID.randomUUID())
                val sut = factory.createProtocol(robQuestions = setOf(repeatedQuestion))

                sut.addRobQuestion(repeatedQuestion)
                assertEquals(1, sut.robQuestions.count { it == repeatedQuestion })
            }

            @Test
            fun `should existent rob questions be successfully removed`() {
                val robQuestion = QuestionId(UUID.randomUUID())
                val sut = factory.createProtocol(robQuestions = setOf(robQuestion))

                sut.removeRobQuestion(robQuestion)

                assertEquals(0, sut.robQuestions.size)
            }
        }

        @Nested
        @Tag("InvalidClasses")
        @DisplayName("And being unable to perform changes")
        inner class AndBeingUnableToPerformChanges {
            @Test
            fun `should thrown when trying to remove rob questions but none exists`() {
                val sut = factory.createProtocol(robQuestions = emptySet())
                val question = QuestionId(UUID.randomUUID())
                assertThrows<IllegalStateException> { sut.removeRobQuestion(question) }
            }

            @Test
            fun `should throw when trying to remove nonexistent rob questions`() {
                val sut = factory.createProtocol()
                val removingQuestion = QuestionId(UUID.randomUUID())

                assertThrows<NoSuchElementException> { sut.removeRobQuestion(removingQuestion) }
            }
        }
    }
}
