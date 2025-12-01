package com.example.level_up.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.level_up.data.model.PedidoConItems
import com.example.level_up.data.model.PedidoEstado
import com.example.level_up.viewmodel.PedidoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialPedidosScreen(
    navController: NavController,
    pedidoViewModel: PedidoViewModel
) {
    val pedidos by pedidoViewModel.pedidos.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis pedidos",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        containerColor = Color.Black
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (pedidos.isEmpty()) {
                Text(
                    text = "Aún no tienes pedidos",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(pedidos) { pedidoConItems ->
                        PedidoCard(
                            pedidoConItems = pedidoConItems,
                            onAvanzar = {
                                pedidoViewModel.avanzarEstado(pedidoConItems.pedido)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PedidoCard(
    pedidoConItems: PedidoConItems,
    onAvanzar: () -> Unit
) {
    val pedido = pedidoConItems.pedido

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Detalle opcional */ },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Pedido #${pedido.id}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Text(
                text = "Total: $${pedido.total}",
                color = Color(0xFF1E90FF)
            )
            Text(
                text = "Estado: ${pedido.estado}",
                color = Color.White
            )



            Text(
                text = "Productos: ${pedidoConItems.items.size}",
                color = Color.White
            )

            if (pedido.estado != PedidoEstado.ENTREGADO) {
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = onAvanzar,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF39FF14),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text("Avanzar estado (Demostración)")
                }
            }
        }
    }
}

