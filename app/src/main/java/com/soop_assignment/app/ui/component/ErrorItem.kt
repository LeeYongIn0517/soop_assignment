package com.soop_assignment.app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soop_assignment.app.ui.theme.Typography

@Composable
fun ErrorItem(code: Int?, message: String?) {
    val errorCode = code ?: "400"
    val errorMessage = message ?: "예기치 않은 오류가 발생했습니다."
    Column(
        modifier = Modifier.padding(horizontal = 15.dp).fillMaxSize(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "${errorCode}",
            style = Typography.titleLarge,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Text(errorMessage, style = Typography.bodyLarge, color = Color.Gray)
    }
}

@Preview
@Composable
fun ErrorPreview() {
    ErrorItem(code = 503, message = "Service unavailable")
}
