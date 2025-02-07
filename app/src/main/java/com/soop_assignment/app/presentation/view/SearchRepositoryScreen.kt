package com.soop_assignment.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.soop_assignment.app.R
import com.soop_assignment.app.presentation.contract.SearchRepositoryEffect
import com.soop_assignment.app.presentation.contract.SearchRepositoryEvent
import com.soop_assignment.app.presentation.viewmodel.SearchViewModel
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
    val searchResult = viewModel.getSearchPagingResult(uiState.value.searchInput).collectAsLazyPagingItems()

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
            when (searchResult.loadState.refresh) {
                is LoadState.Error -> {
                    ErrorItem(
                        code = uiState.value.errorMessage?.code,
                        message = uiState.value.errorMessage?.message
                    )
                }

                else -> {}
            }
            when (searchResult.loadState.append) {
                is LoadState.Error -> {
                    ErrorItem(
                        code = uiState.value.errorMessage?.code,
                        message = uiState.value.errorMessage?.message
                    )
                }

                else -> {}
            }
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(
                    searchResult.itemCount,
                    key = { index -> "${searchResult[index]?.userName}/${searchResult[index]?.repositoryName}" }) { index ->
                    val item = searchResult[index]
                    if (item != null) {
                        Column(
                            Modifier.fillMaxWidth(1f).padding(16.dp).clickable {
                                viewModel.handleEvent(
                                    SearchRepositoryEvent.ClickRepositoryItem(
                                        user = item.userName,
                                        repository = item.repositoryName
                                    )
                                )
                            }) {
                            Row {
                                GlideImage(
                                    model = item.userImageUrl,
                                    contentDescription = null,
                                    modifier = Modifier.background(color = Color.Transparent, shape = CircleShape)
                                        .fillMaxWidth(0.1f).padding(end = 16.dp).clip(CircleShape)
                                )
                                Text(
                                    text = item.userName,
                                    style = Typography.bodySmall,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                            }
                            Text(
                                text = item.repositoryName,
                                style = Typography.titleMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = item.description,
                                style = Typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                SuggestionChip(
                                    onClick = {},
                                    label = { Text(text = "${item.stars}", style = Typography.bodySmall) },
                                    icon = {
                                        Icon(
                                            painter = painterResource(R.drawable.ic_star),
                                            tint = Color.Yellow,
                                            contentDescription = null
                                        )
                                    }, modifier = Modifier.padding(end = 12.dp)
                                )
                                Text(text = item.language, style = Typography.bodySmall)
                            }
                            Spacer(
                                modifier = Modifier.fillMaxWidth(1f).background(color = Color.LightGray)
                                    .height(1.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
