package com.soop_assignment.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soop_assignment.app.R
import com.soop_assignment.app.presentation.contract.SearchRepositoryEffect
import com.soop_assignment.app.presentation.contract.SearchRepositoryEvent
import com.soop_assignment.app.presentation.viewmodel.SearchViewModel
import com.soop_assignment.app.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRepositoryScreen(
    navigateToRepository: (userName: String, repository: String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val searchBarText = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is SearchRepositoryEffect.NavigateToRepository -> navigateToRepository(effect.user, effect.repository)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
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
            LazyColumn {
                items(items = uiState.value.searchResult, key = { it.id }) {
                    Column(Modifier.fillMaxWidth(1f).padding(16.dp)) {
                        Text(
                            text = it.repositoryName,
                            style = Typography.bodySmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
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
                        Spacer(modifier = Modifier.fillMaxWidth(1f).background(color = Color.LightGray).height(1.dp))
                    }
                }
            }
        }
    }
}
