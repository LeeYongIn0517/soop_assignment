package com.soop_assignment.app.domain.model

enum class ErrorType(val code: Int, val message: String) {
    E301(301, "요청하신 페이지가 새 위치로 이동되었습니다."), E304(304, "변경된 내용이 없습니다."), E403(
        403,
        "접근 권한 없음 또는 보조 트래픽률 제한을 초과했습니다."
    ),
    E404(404, "요청하신 정보를 찾을 수 없습니다."), E422(422, "요청을 처리할 수 없습니다. 입력값을 확인해주세요."), E503(
        503,
        "현재 서비스를 이용할 수 없습니다. 잠시 후 다시 시도해주세요."
    )
}
