package br.all.domain.model.review

import br.all.domain.shared.ddd.Identifier
import br.all.domain.shared.ddd.Notification
import java.util.*

@JvmInline
value class SystematicStudyId(private val value: UUID) : Identifier<UUID> {
    init {
        val notification = validate()
        require(notification.hasNoErrors()) { notification.message() }
    }

    override fun validate() = Notification()

    override fun value() = value

    override fun toString() = value.toString()
}

fun UUID.toSystematicStudyId() = SystematicStudyId(this)
