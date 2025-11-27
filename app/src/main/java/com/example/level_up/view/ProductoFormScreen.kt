package com.example.level_up.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.level_up.data.model.Producto
import com.example.level_up.ui.common.productImageResByName
import com.example.level_up.viewmodel.ProductoViewModel
import com.example.level_up.viewmodel.PedidoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String,
    productoViewModel: ProductoViewModel,
    pedidoViewModel: PedidoViewModel
) {
    // Usar SIEMPRE el ViewModel que llega por parámetro
    val productos: List<Producto> by productoViewModel.productos.collectAsState()

    // Estados de la pantalla
    var cantidad by remember { mutableStateOf(1) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var direccionError by remember { mutableStateOf<String?>(null) }
    var tipoEntrega by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    val imageResId = productImageResByName(nombre)
    val precioInt = precio.toIntOrNull() ?: 0
    val total = precioInt * cantidad

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del producto", color = Color.White) },
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
        bottomBar = { BottomAppBar(containerColor = Color.Black) {} },
        containerColor = Color.Black
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Imagen del producto: $nombre",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(16.dp))

            Text(nombre, style = MaterialTheme.typography.headlineSmall, color = Color.White)
            Text(
                "Precio Unitario: $$precioInt",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Cantidad:", color = Color.White)

                FilledIconButton(
                    onClick = { if (cantidad > 1) cantidad-- },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color(0xFF39FF14),
                        contentColor = Color.Black
                    )
                ) { Text("-") }

                Text(
                    cantidad.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                FilledIconButton(
                    onClick = { cantidad++ },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color(0xFF39FF14),
                        contentColor = Color.Black
                    )
                ) { Text("+") }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Total: $$total",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF1E90FF)
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = direccion,
                onValueChange = {
                    direccion = it
                    direccionError = null
                    showSuccess = false
                },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth(),
                isError = direccionError != null,
                supportingText = {
                    direccionError?.let { msg ->
                        Text(msg, color = MaterialTheme.colorScheme.error)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = tipoEntrega,
                    onCheckedChange = {
                        tipoEntrega = it
                        showSuccess = false
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF39FF14),
                        checkmarkColor = Color.Black
                    )
                )
                Text("Despacho a domicilio", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (direccion.text.isBlank()) {
                        direccionError = "La dirección es obligatoria"
                        return@Button
                    }

                    val producto = Producto(
                        nombre = nombre,
                        precio = total.toString(),
                        cantidad = cantidad.toString(),
                        direccion = direccion.text,
                        tipoEntrega = tipoEntrega
                    )

                    // Actualiza el carrito compartido
                    productoViewModel.guardarProducto(producto)

                    // Crea el pedido en el historial compartido
                    pedidoViewModel.crearPedido(
                        productosCarrito = listOf(producto),
                        direccion = direccion.text
                    )

                    direccion = TextFieldValue("")
                    tipoEntrega = false
                    cantidad = 1
                    showSuccess = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF39FF14),
                    contentColor = Color.Black
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Text("AGREGAR AL CARRITO")
            }

            AnimatedVisibility(visible = showSuccess) {
                Text(
                    text = "Pedido guardado correctamente",
                    color = Color(0xFF1E90FF)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Pedidos realizados:",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            if (productos.isNotEmpty()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(productos) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF1A1A1A)
                            )
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("${producto.nombre} - ${producto.precio}", color = Color.White)
                                Text("Cantidad: ${producto.cantidad}", color = Color.White)
                                Text(
                                    "Dirección entrega: ${producto.direccion}",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            } else {
                Text(
                    "No hay pedidos realizados",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
