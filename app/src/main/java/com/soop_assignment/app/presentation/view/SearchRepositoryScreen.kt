package com.soop_assignment.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.soop_assignment.app.presentation.contract.SearchRepositoryEffect
import com.soop_assignment.app.presentation.contract.SearchRepositoryEvent
import com.soop_assignment.app.presentation.viewmodel.SearchViewModel
import com.soop_assignment.app.ui.component.CircularProgressItem
import com.soop_assignment.app.ui.component.ErrorItem
import com.soop_assignment.app.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun SearchRepositoryScreen(
    navigateToRepository: (userName: String, repository: String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val searchBarText = remember { mutableStateOf(uiState.value.searchInput) }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is SearchRepositoryEffect.NavigateToRepository -> navigateToRepository(effect.user, effect.repository)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        SearchBar(
            query = searchBarText.value,
            onQueryChange = {
                searchBarText.value = it
                viewModel.handleEvent(SearchRepositoryEvent.ChangeSearchWord(it))
            },
            shadowElevation = 5.dp,
            onSearch = { viewModel.handleEvent(SearchRepositoryEvent.ChangeSearchWord(it)) },
            active = true,
            onActiveChange = {},
            modifier = Modifier.fillMaxWidth(1f),
            enabled = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
            },
        ) {
            if (uiState.value.isLoading) {
                //로딩 화면
                CircularProgressItem()
            } else if (uiState.value.isError) {
                //에러 메세지 화면
                ErrorItem(code = uiState.value.errorMessage?.code, message = uiState.value.errorMessage?.message)
            } else { //정상 화면
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    items(items = uiState.value.searchResult ?: emptyList(), key = { it.id }) {
                        Column(
                            Modifier.fillMaxWidth(1f).padding(16.dp).clickable {
                                viewModel.handleEvent(
                                    SearchRepositoryEvent.ClickRepositoryItem(
                                        user = it.userName,
                                        repository = it.repositoryName
                                    )
                                )
                            }) {
                            Row {
                                GlideImage(
                                    model = it.userImageUrl,
                                    contentDescription = null,
                                    modifier = Modifier.background(color = Color.Transparent, shape = CircleShape)
                                        .fillMaxWidth(0.1f).padding(end = 16.dp).clip(CircleShape)
                                )
                                Text(
                                    text = it.userName,
                                    style = Typography.bodySmall,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                            }
                            Text(
                                text = it.repositoryName,
                                style = Typography.titleMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = it.description,
                                style = Typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                SuggestionChip(
                                    onClick = {},
                                    label = { Text(text = "${it.stars}", style = Typography.bodySmall) },
                                    icon = {
                                        Icon(
                                            painter = painterResource(R.drawable.ic_star),
                                            tint = Color.Yellow,
                                            contentDescription = null
                                        )
                                    }, modifier = Modifier.padding(end = 12.dp)
                                )
                                Text(text = it.language, style = Typography.bodySmall)
                            }
                            Spacer(
                                modifier = Modifier.fillMaxWidth(1f).background(color = Color.LightGray).height(1.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
