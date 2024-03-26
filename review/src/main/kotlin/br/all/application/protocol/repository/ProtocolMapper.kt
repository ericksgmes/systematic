package br.all.application.protocol.repository

import br.all.application.protocol.update.UpdateProtocolService
import br.all.domain.model.protocol.*
import br.all.domain.model.protocol.Criterion.CriterionType
import br.all.domain.model.question.QuestionId
import br.all.domain.model.review.SystematicStudyId
import br.all.domain.shared.valueobject.Language
import br.all.domain.shared.valueobject.Language.LangType

fun Protocol.toDto() = ProtocolDto(
    id = id.value(),
    systematicStudy = systematicStudyId.value(),

    goal = goal,
    justification = justification,

    researchQuestions = researchQuestions.map { it.toString() }.toSet(),
    keywords = keywords,
    searchString = searchString,
    informationSources = informationSources.map { it.toString() }.toSet(),
    sourcesSelectionCriteria = sourcesSelectionCriteria,

    searchMethod = searchMethod,
    studiesLanguages = studiesLanguages.map { it.langType.name }.toSet(),
    studyTypeDefinition = studyTypeDefinition,

    selectionProcess = selectionProcess,
    eligibilityCriteria = eligibilityCriteria.map { it.toDto() }
        .toSet(),

    dataCollectionProcess = dataCollectionProcess,
    analysisAndSynthesisProcess = analysisAndSynthesisProcess,

    extractionQuestions = extractionQuestions.map { it.value }.toSet(),
    robQuestions = robQuestions.map { it.value}.toSet(),

    picoc = picoc?.toDto(),
)

fun Picoc.toDto() = PicocDto(population, intervention, control, outcome, context)

fun Criterion.toDto() = CriterionDto(description, type.name)

fun Protocol.Companion.fromDto(dto: ProtocolDto) = write(SystematicStudyId(dto.systematicStudy), dto.keywords)
    .researchesFor(dto.goal).because(dto.justification)
    .toAnswer(
        dto.researchQuestions
            .map { it.toResearchQuestion() }
            .toSet(),
    ).followingSearchProcess(dto.searchMethod, dto.searchString)
    .inSearchSources(
        dto.informationSources
            .map { it.toSearchSource() }
            .toSet(),
    ).searchingStudiesIn(
        dto.studiesLanguages
            .map { Language(LangType.valueOf(it)) }
            .toSet(),
        dto.studyTypeDefinition,
    ).followingSelectionProcess(dto.selectionProcess)
    .withEligibilityCriteria(
        dto.eligibilityCriteria
            .map { Criterion.fromDto(it) }
            .toSet(),
    ).followingDataCollectionProcess(dto.dataCollectionProcess)
    .extractDataByAnswering(
        dto.extractionQuestions
            .map { QuestionId(dto.id) }
            .toSet(),
    ).followingSynthesisProcess(dto.analysisAndSynthesisProcess)
    .qualityFormConsiders(
        dto.robQuestions
            .map { QuestionId(dto.id) }
            .toSet(),
    ).withPICOC(dto.picoc?.let { Picoc.fromDto(it) })
    .build()

fun Picoc.Companion.fromDto(dto: PicocDto) = Picoc(
    dto.population,
    dto.intervention,
    dto.control,
    dto.outcome,
    dto.context,
)

fun Criterion.Companion.fromDto(dto: CriterionDto) = Criterion(dto.description, CriterionType.valueOf(dto.type))

fun Protocol.copyUpdates(request: UpdateProtocolService.RequestModel) = apply {
    goal = request.goal ?: goal
    justification = request.justification ?: justification
    request.researchQuestions
        .map { it.toResearchQuestion() }
        .toSet()
        .let { replaceResearchQuestions(it) }
    replaceKeywords(request.keywords)

    searchString = request.searchString ?: searchString
    request.informationSources
        .map { it.toSearchSource() }
        .toSet()
        .let { replaceInformationSources(it) }
    sourcesSelectionCriteria = request.sourcesSelectionCriteria ?: sourcesSelectionCriteria
    searchMethod = request.searchMethod ?: searchMethod

    request.studiesLanguages
        .map { Language(LangType.valueOf(it)) }
        .toSet()
        .let { replaceLanguages(it) }
    studyTypeDefinition = request.studyTypeDefinition ?: studyTypeDefinition

    selectionProcess = request.selectionProcess ?: selectionProcess
    request.eligibilityCriteria
        .map { Criterion.fromDto(it) }
        .toSet()
        .let { replaceEligibilityCriteria(it) }

    dataCollectionProcess = request.dataCollectionProcess ?: dataCollectionProcess
    analysisAndSynthesisProcess = request.analysisAndSynthesisProcess ?: analysisAndSynthesisProcess

    picoc = request.picoc?.let { Picoc.fromDto(it) }
}
