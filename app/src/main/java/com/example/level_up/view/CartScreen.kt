package com.example.level_up.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.level_up.data.model.Producto
import com.example.level_up.ui.common.productImageResByName
import com.example.level_up.viewmodel.ProductoViewModel
import com.example.level_up.viewmodel.PedidoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel,
    pedidoViewModel: PedidoViewModel
) {
    val productos by productoViewModel.productos.collectAsState()
    val totalGeneral = productos.sumOf { it.precio.toIntOrNull() ?: 0 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de compras", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            if (productos.isEmpty()) {
                Text(
                    "Tu carrito está vacío",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    "Productos en el carrito",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(8.dp))

                val listaModifier = if (productos.size > 1) {
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                } else {
                    Modifier.fillMaxWidth()
                }

                LazyColumn(
                    modifier = listaModifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productos) { producto ->
                        CartItem(
                            producto = producto,
                            onUpdateCantidad = { p, nuevaCantidad ->
                                productoViewModel.actualizarCantidad(p, nuevaCantidad)
                            },
                            onRemove = { p ->
                                productoViewModel.eliminarProducto(p)
                            }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Total a pagar: $$totalGeneral",
                    color = Color(0xFF39FF14),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.End
                )

                Button(
                    onClick = {
                        if (productos.isNotEmpty()) {
                            // Usamos la dirección del primer producto (todos comparten la misma)
                            val direccion = productos.first().direccion

                            // Crea un pedido con TODOS los productos del carrito
                            pedidoViewModel.crearPedido(
                                productosCarrito = productos,
                                direccion = direccion
                            )

                            // Opcional: limpiar carrito después de confirmar
                            // productoViewModel.limpiarCarrito()

                            // Navegar al historial
                            navController.navigate("historial")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF39FF14),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Confirmar Pedido")
                }
            }
        }
    }
}

@Composable
private fun CartItem(
    producto: Producto,
    onUpdateCantidad: (Producto, Int) -> Unit,
    onRemove: (Producto) -> Unit
) {
    val imageResId = productImageResByName(producto.nombre)
    val cantidadActual = producto.cantidad.toIntOrNull() ?: 1

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Imagen ${producto.nombre}",
                modifier = Modifier
                    .size(72.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(producto.nombre, color = Color.White)
                Text("Dirección: ${producto.direccion}", color = Color.White)
                Text("Total: $${producto.precio}", color = Color(0xFF1E90FF))

                Spacer(Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Cantidad:", color = Color.White)

                    FilledIconButton(
                        onClick = { onUpdateCantidad(producto, cantidadActual - 1) },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color(0xFF39FF14),
                            contentColor = Color.Black
                        )
                    ) { Text("-") }

                    Text(
                        cantidadActual.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )

                    FilledIconButton(
                        onClick = { onUpdateCantidad(producto, cantidadActual + 1) },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color(0xFF39FF14),
                            contentColor = Color.Black
                        )
                    ) { Text("+") }

                    Spacer(Modifier.width(8.dp))

                    TextButton(onClick = { onRemove(producto) }) {
                        Text("Eliminar", color = Color(0xFFFF5555))
                    }
                }
            }
        }
    }
}
