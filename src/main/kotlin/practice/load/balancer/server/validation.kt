package practice.money.transfer.server

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import practice.load.balancer.server.NodeInfo

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<NodeInfo> {
            with(mutableListOf<String>()) {
                addIf("Host must not be blank") { it.host.isBlank() }
                addIf("Port must be greater than zero") { it.port > 0 }
                validationResult(this)
            }
        }
    }
}

private fun <T> MutableList<T>.addIf(element: T, predicate: () -> Boolean) {
    if (predicate.invoke()) this.add(element)
}

private fun validationResult(reasons: List<String>): ValidationResult =
    if (reasons.isNotEmpty()) ValidationResult.Invalid(reasons)
    else ValidationResult.Valid
