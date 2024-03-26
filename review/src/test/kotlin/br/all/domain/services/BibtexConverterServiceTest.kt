package br.all.domain.services

import br.all.domain.model.review.SystematicStudyId
import br.all.domain.model.study.*
import br.all.domain.services.BibtexTestData.testInputs
import org.junit.jupiter.api.*
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BibtexConverterServiceTest {

    private lateinit var sut: BibtexConverterService
    private lateinit var idGeneratorService: IdGeneratorService

    @BeforeEach fun setup() {
        val systematicStudyId = SystematicStudyId(UUID.randomUUID())
        idGeneratorService = FakeIdGeneratorService
        sut = BibtexConverterService(idGeneratorService)
    }

    @AfterEach fun teardown() {
        val fake = idGeneratorService as FakeIdGeneratorService
        fake.reset()
    }

    @Nested inner class ValidClasses(){

        @Test fun `Should create a StudyReview list from multiple bibtex entries as input`() {
            val bibtex = testInputs["multiple bibtex entries"]!!
            val studyReviewList = sut.convertManyToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)
            assertEquals(7, studyReviewList.size)
        }

        @Test fun `should create article from valid input`() {
            val bibtex = testInputs["valid article"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.ARTICLE, studyReview.studyType) },
                { assertEquals("Non-cooperative Games", studyReview.title) },
                { assertEquals(1951, studyReview.year) },
                { assertEquals("Nash, John", studyReview.authors) },
                { assertEquals("Annals of Mathematics", studyReview.venue) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertTrue("keyword1" in studyReview.keywords) },
                { assertTrue("keyword2" in studyReview.keywords) },
                { assertEquals(2, studyReview.references.size) },
                { assertEquals("https://doi.org/10.1234/doi", studyReview.doi?.value) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create inproceedings from valid input`() {
            val bibtex = testInputs["valid inproceedings"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)
            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.INPROCEEDINGS, studyReview.studyType) },
                { assertEquals("Onofre {Trindade Júnior}", studyReview.authors) },
                { assertEquals("{Using SOA in Critical-Embedded Systems}", studyReview.title) },
                { assertEquals("Proceedings of the 4${'$'}^{th}${'$'}  IEEE (CPSCom'11)", studyReview.venue) },
                { assertEquals(2011, studyReview.year) },
                { assertEquals("Lorem ipsum", studyReview.abstract) },
                { assertEquals(listOf("ref3", "ref4"), studyReview.references) },
                { assertEquals(Doi("https://doi.org/10.1021/ci025584y"), studyReview.doi) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create techreport from valid input`() {
            val bibtex = testInputs["valid techreport"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)
            val expected = "Uma abordagem apoiada por linguagens específicas de domínio " +
            "para a criação de linhas de produto de software embarcado"

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.TECHREPORT, studyReview.studyType) },
                { assertEquals("Rafael Serapilha Durelli", studyReview.authors) },
                { assertEquals(expected, studyReview.title) },
                { assertEquals("Universidade Federal de São Carlos (UFSCar)", studyReview.venue) },
                { assertEquals(2011, studyReview.year) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create book from valid input`() {
            val bibtex = testInputs["valid book"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.BOOK, studyReview.studyType) },
                { assertEquals("Len Bass and Paul Clements and Rick Kazman", studyReview.authors) },
                { assertEquals("Software Architecture in Practice", studyReview.title) },
                { assertEquals("Addison-Wesley", studyReview.venue) },
                { assertEquals(2012, studyReview.year) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create proceedings from valid input`() {
            val bibtex = testInputs["valid proceedings"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)

            val expected = "Proceedings of the 17th International Conference on Computation " +
                    "and Natural Computation, Fontainebleau, France"

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.PROCEEDINGS, studyReview.studyType) },
                { assertEquals("Susan Stepney and Sergey Verlan", studyReview.authors) },
                { assertEquals(expected, studyReview.title) },
                { assertEquals("Springer", studyReview.venue) },
                { assertEquals(2018, studyReview.year) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create phdthesis from valid input`() {
            val bibtex = testInputs["valid phdthesis"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.PHDTHESIS, studyReview.studyType) },
                { assertEquals("Rempel, Robert Charles", studyReview.authors) },
                { assertEquals("Relaxation Effects for Coupled Nuclear Spins", studyReview.title) },
                { assertEquals("Stanford University", studyReview.venue) },
                { assertEquals(1956, studyReview.year) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create mastersthesis from valid input`() {
            val bibtex = testInputs["valid mastersthesis"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.MASTERSTHESIS, studyReview.studyType) },
                { assertEquals("Jian Tang", studyReview.authors) },
                { assertEquals("Spin structure of the nucleon in the asymptotic limit", studyReview.title) },
                { assertEquals("Massachusetts Institute of Technology", studyReview.venue) },
                { assertEquals(1996, studyReview.year) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create inbook from valid input`() {
            val bibtex = testInputs["valid inbook"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)

            val expected = "Lisa A. Urry and Michael L. Cain and Steven A. Wasserman " +
                "and Peter V. Minorsky and Jane B. Reece"

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.INBOOK, studyReview.studyType) },
                { assertEquals(expected, studyReview.authors ) },
                { assertEquals("Photosynthesis", studyReview.title) },
                { assertEquals("Campbell Biology", studyReview.venue) },
                { assertEquals(2016, studyReview.year) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create booklet from valid input`() {
            val bibtex = testInputs["valid booklet"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.BOOKLET, studyReview.studyType) },
                { assertEquals("Maria Swetla", studyReview.authors) },
                { assertEquals("Canoe tours in {S}weden", studyReview.title) },
                { assertEquals("Distributed at the Stockholm Tourist Office", studyReview.venue) },
                { assertEquals(2015, studyReview.year) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create manual from valid input`() {
            val bibtex = testInputs["valid manual"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.MANUAL, studyReview.studyType) },
                { assertEquals("{R Core Team}", studyReview.authors) },
                { assertEquals("{R}: A Language and Environment for Statistical Computing", studyReview.title) },
                { assertEquals("R Foundation for Statistical Computing", studyReview.venue) },
                { assertEquals(2018, studyReview.year) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create misc from valid input`() {
            val bibtex = testInputs["valid misc"]!!
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.MISC, studyReview.studyType) },
                { assertEquals("{NASA}", studyReview.authors) },
                { assertEquals("Pluto: The 'Other' Red Planet", studyReview.title) },
                { assertEquals("\\url{https://www.nasa.gov/nh/pluto-the-other-red-planet}", studyReview.venue) },
                { assertEquals(2015, studyReview.year) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }

        @Test fun `should create unpublished from valid input`() {
            val bibtex = testInputs["valid unpublished"].toString()
            val studyReview = sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)

            assertAll(
                "bibtex",
                { assertEquals("1", studyReview.id.toString()) },
                { assertEquals(StudyType.UNPUBLISHED, studyReview.studyType) },
                { assertEquals("Mohinder Suresh", studyReview.authors) },
                { assertEquals("Evolution: a revised theory", studyReview.title) },
                { assertEquals("Lorem Ipsum", studyReview.venue) },
                { assertEquals(2006, studyReview.year) },
                { assertEquals("Lorem Ipsum", studyReview.abstract) },
                { assertEquals(1, studyReview.searchSources.size) },
                { assertTrue(studyReview.criteria.isEmpty()) },
                { assertTrue(studyReview.formAnswers.isEmpty()) },
                { assertTrue(studyReview.robAnswers.isEmpty()) },
                { assertEquals("", studyReview.comments) },
                { assertEquals(ReadingPriority.LOW, studyReview.readingPriority) },
                { assertEquals(SelectionStatus.UNCLASSIFIED, studyReview.selectionStatus) },
                { assertEquals(ExtractionStatus.UNCLASSIFIED, studyReview.extractionStatus) }
            )
        }
    }

    @Nested inner class InvalidClasses() {
        @Test fun `convertManyToStudyReview should not accept a blank bibtex entry as input`() {
            assertThrows<IllegalArgumentException> {
                sut.convertManyToStudyReview(SystematicStudyId(UUID.randomUUID()), "")
            }
        }

        @Test fun `convertToStudyReview should not accept a blank bibtex entry as input`() {
            assertThrows<IllegalArgumentException> {
                sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), "")
            }
        }

        @Test fun `should throw IllegalArgumentException for unknown type entry`() {
            val bibtex = testInputs["unknown type of bibtex"]!!
            assertThrows<IllegalArgumentException> {
                sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)
            }
        }

        @Test fun `should throw IllegalArgumentException for invalid title entry`() {
            val bibtex = testInputs["invalid title"].toString()
            assertThrows<IllegalArgumentException> {
                sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)
            }
        }

        @Test fun `should throw IllegalArgumentException for invalid author entry`() {
            val bibtex = testInputs["invalid authors"]!!
            assertThrows<IllegalArgumentException> {
                sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)
            }
        }

        @Test fun `should throw IllegalArgumentException for invalid year entry`() {
            val bibtex = testInputs["invalid year"]!!
            assertThrows<IllegalArgumentException> {
                sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)
            }
        }

        @Test fun `should throw IllegalArgumentException for invalid venue entry`() {
            val bibtex = testInputs["invalid venue"]!!
            assertThrows<IllegalArgumentException> {
                sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)
            }
        }

        @Test fun `should throw IllegalArgumentException for invalid abstract entry`() {
            val bibtex = testInputs["invalid abstract"]!!
            assertThrows<IllegalArgumentException> {
                sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)
            }
        }

        @Test fun `should throw IllegalArgumentException for invalid doi`() {
            val bibtex = testInputs["invalid doi"]!!
            assertThrows<IllegalArgumentException> {
                sut.convertToStudyReview(SystematicStudyId(UUID.randomUUID()), bibtex)
            }
        }
    }
}
