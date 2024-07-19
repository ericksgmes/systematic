package br.all.study.presenter

import br.all.application.study.find.presenter.FindAllStudyReviewsBySourcePresenter
import br.all.application.study.find.service.FindAllStudyReviewsBySourceService.ResponseModel
import br.all.application.study.repository.StudyReviewDto
import br.all.shared.error.createErrorResponseFrom
import br.all.study.controller.StudyReviewController
import br.all.study.requests.PostStudyReviewRequest
import br.all.utils.LinksFactory
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class RestfulFindAllStudyReviewsBySourcePresenter(
    private val linksFactory: LinksFactory
) : FindAllStudyReviewsBySourcePresenter {

    var responseEntity: ResponseEntity<*>? = null

    override fun prepareSuccessView(response: ResponseModel) {
        val (_, systematicStudyId, searchSource, studyReviews) = response
        val restfulResponse = ViewModel(systematicStudyId, searchSource, studyReviews.size, studyReviews)

        val selfRef = linksFactory.findAllStudiesBySource(response.systematicStudyId, response.searchSource)
        val allStudyReview = linksFactory.findAllStudies(response.systematicStudyId)
        val createStudyReview = linksFactory.createStudy(response.systematicStudyId)
        restfulResponse.add(selfRef, allStudyReview, createStudyReview)
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(restfulResponse)
    }

    override fun prepareFailView(throwable: Throwable) = run { responseEntity = createErrorResponseFrom(throwable) }

    override fun isDone() = responseEntity != null

    private data class ViewModel(
        val systematicStudyId: UUID,
        val searchSource: String,
        val size: Int,
        val studyReviews: List<StudyReviewDto>,
    ) : RepresentationModel<ViewModel>()
}
