package me.mudkip.moememos.ui.page.memos

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import me.mudkip.moememos.R
import me.mudkip.moememos.ext.string
import me.mudkip.moememos.ui.page.common.LocalRootNavController
import me.mudkip.moememos.ui.page.common.RouteName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemosHomePage(
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val rootNavController = LocalRootNavController.current
    val scrollState = rememberLazyListState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = R.string.memos.string) },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(Icons.Filled.Menu, contentDescription = R.string.menu.string)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        rootNavController.navigate(RouteName.SEARCH)
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = R.string.search.string)
                    }
                },
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        scope.launch {
                            scrollState.animateScrollToItem(0)
                        }
                    })
                }
            )
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    rootNavController.navigate(RouteName.INPUT)
                },
                text = { Text(R.string.new_memo.string) },
                icon = { Icon(Icons.Filled.Add, contentDescription = R.string.compose.string) }
            )
        },

        content = { innerPadding ->
            MemosList(
                contentPadding = innerPadding,
                lazyListState = scrollState
            )
        }
    )
}