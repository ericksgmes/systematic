package br.all.study.presenter

import br.all.application.study.find.presenter.FindAllStudyReviewsBySessionPresenter
import br.all.application.study.find.service.FindAllStudyReviewsBySessionService
import br.all.application.study.find.service.FindAllStudyReviewsBySourceService
import br.all.application.study.repository.StudyReviewDto
import br.all.shared.error.createErrorResponseFrom
import br.all.utils.LinksFactory
import org.springframework.hateoas.RepresentationModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

class RestfulFindAllStudyReviewsBySessionPresenter (
    private val linksFactory: LinksFactory
): FindAllStudyReviewsBySessionPresenter {

    var responseEntity: ResponseEntity<*>? = null

    override fun prepareSuccessView(response: FindAllStudyReviewsBySessionService.ResponseModel) {
        val (_, systematicStudyId, searchSessionId, studyReviews) = response
        val restfulResponse = ViewModel(systematicStudyId, searchSessionId, studyReviews.size, studyReviews)

        responseEntity = ResponseEntity.status(HttpStatus.OK).body(restfulResponse)
    }

    override fun prepareFailView(throwable: Throwable) = run { responseEntity = createErrorResponseFrom(throwable) }

    override fun isDone() = responseEntity != null

    private data class ViewModel(
        val systematicStudyId: UUID,
        val searchSessionId: UUID,
        val size: Int,
        val studyReviews: List<StudyReviewDto>,
    ) : RepresentationModel<ViewModel>()
}