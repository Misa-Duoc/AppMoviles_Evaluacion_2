import com.example.level_up.data.dao.ProductoDao
import com.example.level_up.data.model.Producto

class ProductoRepository(private val dao: ProductoDao) {

    fun obtenerProductos() = dao.getProductos()          // Flow<List<Producto>>

    suspend fun insertarProducto(producto: Producto) = dao.insert(producto)

    suspend fun eliminarProducto(producto: Producto) = dao.delete(producto)

    suspend fun actualizarProducto(producto: Producto) = dao.update(producto)
}
