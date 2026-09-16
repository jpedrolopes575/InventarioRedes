package com.joaopedro.inventarioredes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joaopedro.inventarioredes.ui.screens.CadastroScreen
import com.joaopedro.inventarioredes.ui.screens.DetalhesScreen
import com.joaopedro.inventarioredes.ui.screens.ListaScreen
import com.joaopedro.inventarioredes.viewmodel.EquipamentoViewModel

/**
 * Navigation Compose: cada "rota" é uma tela.
 */
object Rotas {
    const val LISTA = "lista"
    const val CADASTRO = "cadastro"
    const val DETALHES = "detalhes"
}

@Composable
fun AppNavigation(viewModel: EquipamentoViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rotas.LISTA) {

        // Tela 1 - lista
        composable(Rotas.LISTA) {
            ListaScreen(
                viewModel = viewModel,
                aoClicarNovo = { navController.navigate("${Rotas.CADASTRO}/0") },
                aoClicarItem = { id -> navController.navigate("${Rotas.DETALHES}/$id") }
            )
        }

        // Tela 2 - cadastro/edição (id = 0 significa "novo equipamento")
        composable(
            route = "${Rotas.CADASTRO}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            CadastroScreen(
                viewModel = viewModel,
                idEquipamento = id,
                aoVoltar = { navController.popBackStack() }
            )
        }

        // Tela 3 - detalhes
        composable(
            route = "${Rotas.DETALHES}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetalhesScreen(
                viewModel = viewModel,
                idEquipamento = id,
                aoVoltar = { navController.popBackStack() },
                aoEditar = { navController.navigate("${Rotas.CADASTRO}/$id") }
            )
        }
    }
}
