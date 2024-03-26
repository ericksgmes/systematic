package br.all.protocol.controller

import br.all.application.protocol.find.FindOneProtocolServiceImpl
import br.all.application.protocol.repository.ProtocolRepository
import br.all.application.protocol.update.UpdateProtocolServiceImpl
import br.all.application.researcher.credentials.ResearcherCredentialsService
import br.all.application.review.repository.SystematicStudyRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProtocolServicesConfiguration {
    @Bean
    fun createFindOneProtocolService(
        protocolRepository: ProtocolRepository,
        systematicStudyRepository: SystematicStudyRepository,
        credentialsService: ResearcherCredentialsService,
    ) = FindOneProtocolServiceImpl(protocolRepository, systematicStudyRepository, credentialsService)

    @Bean
    fun updateProtocolService(
        protocolRepository: ProtocolRepository,
        systematicStudyRepository: SystematicStudyRepository,
        credentialsService: ResearcherCredentialsService,
    ) = UpdateProtocolServiceImpl(protocolRepository, systematicStudyRepository, credentialsService)
}
