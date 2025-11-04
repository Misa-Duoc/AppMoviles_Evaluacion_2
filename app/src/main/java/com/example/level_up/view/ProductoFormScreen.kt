package com.example.level_up.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.level_up.data.model.Producto
import com.example.level_up.ui.common.productImageResByName
import com.example.level_up.viewmodel.ProductoViewModel
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String
) {

    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var tipo by remember { mutableStateOf(TextFieldValue("")) }
    var tipoEntrega by remember { mutableStateOf(false) }

    // errores visibles
    var cantidadError by remember { mutableStateOf<String?>(null) }
    var tipoError by remember { mutableStateOf<String?>(null) }

    // feedback de guardado
    var showSuccess by remember { mutableStateOf(false) }

    val viewModel: ProductoViewModel = viewModel()
    val productos: List<Producto> by viewModel.productos.collectAsState()

    val imageResId = productImageResByName(nombre)

    Scaffold(
        bottomBar = {
            BottomAppBar {}
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = nombre, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Precio: $precio", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // CANTIDAD
            OutlinedTextField(
                value = cantidad,
                onValueChange = {
                    cantidad = it
                    cantidadError = null
                    showSuccess = false
                },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth(),
                isError = cantidadError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                supportingText = {
                    cantidadError?.let { msg ->
                        Text(msg, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            // DIRECCIÓN
            OutlinedTextField(
                value = tipo,
                onValueChange = {
                    tipo = it
                    tipoError = null
                    showSuccess = false
                },
                label = { Text("Direccion") },
                modifier = Modifier.fillMaxWidth(),
                isError = tipoError != null,
                supportingText = {
                    tipoError?.let { msg ->
                        Text(msg, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = tipoEntrega,
                    onCheckedChange = {
                        tipoEntrega = it
                        showSuccess = false
                    }
                )
                Text("Despacho a domicilio")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 👇 botón siempre habilitado, validamos adentro
            Button(
                onClick = {
                    var valido = true

                    if (cantidad.text.isBlank()) {
                        cantidadError = "La cantidad es obligatoria"
                        valido = false
                    } else if (cantidad.text.toIntOrNull() == null || cantidad.text.toInt() <= 0) {
                        cantidadError = "Ingresa una cantidad válida"
                        valido = false
                    }

                    if (tipo.text.isBlank()) {
                        tipoError = "La dirección es obligatoria"
                        valido = false
                    }

                    if (valido) {
                        val producto = Producto(
                            nombre = nombre,
                            precio = precio,
                            cantidad = cantidad.text,
                            direccion = tipo.text,
                            tipoEntrega = tipoEntrega
                        )
                        viewModel.guardarProducto(producto)

                        // limpiar
                        cantidad = TextFieldValue("")
                        tipo = TextFieldValue("")
                        tipoEntrega = false

                        showSuccess = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar Pedido")
            }

            AnimatedVisibility(visible = showSuccess) {
                Text(
                    text = "Pedido guardado correctamente ✅",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Pedidos realizados: ", style = MaterialTheme.typography.headlineSmall)

            if (productos.isNotEmpty()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(productos) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = "${producto.nombre} - ${producto.precio}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Cantidad: ${producto.cantidad}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Direccion entrega: ${producto.direccion}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            } else {
                Text(
                    "No hay pedidos realizados",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductoFormScreen() {
    ProductoFormScreen(
        navController = rememberNavController(),
        nombre = "Producto Ejemplo",
        precio = "10000"
    )
}
