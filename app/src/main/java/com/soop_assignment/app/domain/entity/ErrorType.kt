package com.soop_assignment.app.domain.entity

object ErrorType {
    private val errorMap = mapOf(
        301 to "리다이렉션 오류",
        304 to "캐시된 응답을 사용하세요",
        403 to "접근 권한이 없습니다",
        404 to "요청한 리소스를 찾을 수 없습니다",
        503 to "서비스가 일시적으로 중단되었습니다"
    )

    fun fromCode(code: Int): String = errorMap[code] ?: "예기치 않은 오류가 발생했습니다:("
}
