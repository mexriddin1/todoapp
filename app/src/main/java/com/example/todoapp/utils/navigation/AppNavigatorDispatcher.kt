package com.example.todoapp.utils.navigation

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigatorDispatcher @Inject constructor() : AppNavigator, AppNavigatorHandler {
    override val navigation = MutableSharedFlow<NavigatorArgs>()

    private suspend fun navigator(args: NavigatorArgs) {
        navigation.emit(args)
    }

    override suspend fun navigateTo(screen: AppScreen) = navigator {
        push(screen)
    }

    override suspend fun navigateTo(screens: List<AppScreen>) = navigator {
        push(screens)
    }


    override suspend fun navigateToSingleTop(screen: AppScreen) = navigator {
        if (lastItem::class.java.name != screen::class.java.name) {
            push(screen)
        }
    }

    override suspend fun replaceAll(screen: AppScreen) = navigator {
        replaceAll(screen)
    }

    override suspend fun replaceAll(screen: List<AppScreen>) = navigator {
        replaceAll(screen)
    }

    override suspend fun replace(screen: AppScreen) = navigator {
        replace(screen)
    }

    override suspend fun back() = navigator {
        pop()
    }

    override suspend fun backAll() = navigator {
        popUntilRoot()
    }


    override suspend fun <T : AppScreen> backUntil(clazz: Class<T>) = navigator {
        popUntil { it::class.java == clazz }
    }

    override suspend fun backUntil(predicate: (AppScreen) -> Boolean): Boolean {
        val deferred = CompletableDeferred<Boolean>()
        navigation.emit {
            deferred.complete(popUntil(predicate))
        }
        return deferred.await()
    }
}