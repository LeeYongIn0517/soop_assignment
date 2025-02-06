package com.soop_assignment.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.soop_assignment.app.R
import com.soop_assignment.app.domain.model.User
import com.soop_assignment.app.presentation.contract.RepositoryEffect
import com.soop_assignment.app.presentation.contract.RepositoryEvent
import com.soop_assignment.app.presentation.viewmodel.RepositoryViewModel
import com.soop_assignment.app.ui.component.CircularProgressItem
import com.soop_assignment.app.ui.component.ErrorItem
import com.soop_assignment.app.ui.theme.Typography

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RepositoryScreen(
    userName: String,
    repository: String,
    navigateToBack: () -> Unit,
    viewModel: RepositoryViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val modalBottomSheet = rememberStandardBottomSheetState(initialValue = SheetValue.Hidden, skipHiddenState = false)
    var showBottomSheet by remember { mutableStateOf(uiState.value.isModalExpanded) }

    LaunchedEffect(Unit) {
        viewModel.handleEvent(RepositoryEvent.GetRepository(userName = userName, repository = repository))

        viewModel.effects.collect { effect ->
            when (effect) {
                RepositoryEffect.NavigateToBack -> navigateToBack()
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(1f)) { innerPadding ->
        Column(
            modifier = Modifier.padding(horizontal = 15.dp).fillMaxSize(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        tint = Color.Black,
                        contentDescription = null,
                        modifier = Modifier.clickable { viewModel.handleEvent(RepositoryEvent.ClickBackButton) }
                            .fillMaxSize(0.06f)
                    )
                })
            if (uiState.value.isLoading) {
                //로딩 화면
                CircularProgressItem()
            } else if (uiState.value.isError) {
                //에러 메세지 화면
                ErrorItem(code = uiState.value.errorMessage?.code, message = uiState.value.errorMessage?.message)
            } else {
                //정상적인 화면
                if (showBottomSheet) {
                    ModalBottomSheet(
                        modifier = Modifier.padding(innerPadding),
                        onDismissRequest = { viewModel.handleEvent(RepositoryEvent.ClickUserMore) },
                        sheetState = modalBottomSheet
                    ) {
                        UserInfoBottomSheetContent(uiState.value.user)
                    }
                }
                Column {
                    Text(
                        text = uiState.value.repository?.repositoryName ?: "",
                        style = Typography.titleLarge,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    Spacer(
                        Modifier.fillMaxWidth().background(color = Color.LightGray).height(1.dp).padding(top = 20.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(1f).padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Star",
                                style = Typography.titleLarge,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = "${uiState.value.repository?.stars}",
                                style = Typography.bodyLarge,
                                color = Color.DarkGray
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Watchers",
                                style = Typography.titleLarge,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = "${uiState.value.repository?.watchers}",
                                style = Typography.bodyLarge,
                                color = Color.DarkGray
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Forks",
                                style = Typography.titleLarge,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = "${uiState.value.repository?.forks}",
                                style = Typography.bodyMedium,
                                color = Color.DarkGray
                            )
                        }
                    }
                    Spacer(Modifier.fillMaxWidth().background(color = Color.LightGray).height(1.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(1f).padding(vertical = 20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            GlideImage(
                                model = uiState.value.repository?.userImageUrl,
                                contentDescription = null,
                                modifier = Modifier.background(color = Color.Transparent, shape = CircleShape)
                                    .fillMaxWidth(0.2f).padding(end = 16.dp).clip(CircleShape)
                            )
                            Text(text = uiState.value.user?.userName ?: "", style = Typography.bodyLarge)
                        }
                        Button(
                            onClick = { viewModel.handleEvent(RepositoryEvent.ClickUserMore) },
                            content = {
                                Text(
                                    text = "more",
                                    style = Typography.labelLarge,
                                    color = Color.White
                                )
                            })
                    }
                    Spacer(Modifier.fillMaxWidth().background(color = Color.LightGray).height(1.dp))
                    Column(Modifier.padding(vertical = 20.dp)) {
                        Text(
                            text = "Description",
                            style = Typography.titleLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "${uiState.value.repository?.description}",
                            style = Typography.bodyLarge,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Content() {

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserInfoBottomSheetContent(user: User?) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 36.dp)) {
            GlideImage(
                model = user?.userImageUrl ?: "",
                contentDescription = null,
                modifier = Modifier.background(color = Color.Transparent, shape = CircleShape)
                    .fillMaxWidth(0.3f).padding(end = 16.dp).clip(CircleShape)
            )
            Text(text = user?.userName ?: "", style = Typography.titleLarge)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 36.dp)) {
            Text(text = "Followers", style = Typography.titleMedium, modifier = Modifier.padding(end = 16.dp))
            Text(text = "${user?.followers}", style = Typography.bodyMedium, color = Color.DarkGray)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 36.dp)) {
            Text(text = "Following", style = Typography.titleMedium, modifier = Modifier.padding(end = 16.dp))
            Text(text = "${user?.following}", style = Typography.bodyMedium, color = Color.DarkGray)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 36.dp)) {
            Text(text = "Language", style = Typography.titleMedium, modifier = Modifier.padding(end = 16.dp))
            Text(text = "${user?.languages}", style = Typography.bodyMedium, color = Color.DarkGray)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 36.dp)) {
            Text(text = "Bio", style = Typography.titleMedium, modifier = Modifier.padding(end = 16.dp))
            Text(text = "${user?.bio}", style = Typography.bodyMedium, color = Color.DarkGray)
        }
    }
}
