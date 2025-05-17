package dataAccess;

import exceptions.*;
import interfaces.ProductDAO;
import model.Brand;
import model.Category;
import model.Product;
import model.Vat;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class ProductDBAccess extends DBAccess implements ProductDAO {
    private static final String objectClassName;

    static {
        objectClassName = Product.class.getSimpleName().toLowerCase();
    }
    
    // Public methods

    public int create(Product product) throws InsertionFailedException, DAORetrievalFailedException {
        sqlInstruction = "INSERT INTO product VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setLong(1, product.getBarcode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getAmount());
            preparedStatement.setBoolean(5, product.getAvailable());
            preparedStatement.setString(6, String.valueOf(product.getVatType()));
            preparedStatement.setInt(7, product.getCategoryId());
            preparedStatement.setInt(8, product.getBrandId());
            preparedStatement.setBigDecimal(9, product.getExclVatPrice());
            preparedStatement.setDate(10, Date.valueOf(product.getStartDate()));

            try {
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new InsertionFailedException(objectClassName, (Long)null, e.getMessage());
            }
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public int remove(Long barcode) throws DeleteFailedException, DAORetrievalFailedException {
        sqlInstruction = "DELETE FROM product WHERE barcode = ?;";

        removeOrderLineByBarcode(barcode);

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setLong(1, barcode);

            try {
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DeleteFailedException(objectClassName, barcode, e.getMessage());
            }
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    private void removeOrderLineByBarcode(Long barcode) throws DAORetrievalFailedException {
        sqlInstruction = "DELETE FROM order_line WHERE product_barcode = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setLong(1, barcode);
            preparedStatement.executeUpdate();
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public int update(Product product) throws UpdateFailedException, DAORetrievalFailedException {
        sqlInstruction = "UPDATE product SET name = ?, description = ?, amount = ?, is_available = ?, vat_type = ?, category_id = ?, brand_id = ?, excl_vat_price = ?, start_date = ? WHERE barcode = ?;";
        Long barcode = product.getBarcode();

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getAmount());
            preparedStatement.setBoolean(4, product.getAvailable());
            preparedStatement.setString(5, String.valueOf(product.getVatType()));
            preparedStatement.setInt(6, product.getCategoryId());
            preparedStatement.setInt(7, product.getBrandId());
            preparedStatement.setBigDecimal(8, product.getExclVatPrice());
            preparedStatement.setDate(9, Date.valueOf(product.getStartDate()));
            preparedStatement.setLong(10, barcode);

            try {
                return preparedStatement.executeUpdate();
            } catch (SQLTimeoutException e) {
                throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
            } catch (SQLException e) {
                throw new UpdateFailedException(objectClassName, barcode, e.getMessage());
            }

        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public Product getByBarcode(Long barcode) throws NotFoundException, DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM product WHERE barcode = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setLong(1, barcode);

            ResultSet data = preparedStatement.executeQuery();

            if (data.next()) {
                return resultSetToProductObject(data);
            } else {
                throw new NotFoundException(objectClassName, barcode, DBRetrievalFailure.NO_ROW);
            }
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public ArrayList<Product> searchByName(String name) throws DAORetrievalFailedException, NotFoundException {
        sqlInstruction = "SELECT * FROM product WHERE name ILIKE ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, (name + "%"));

            ResultSet data = preparedStatement.executeQuery();

            ArrayList<Product> products = new ArrayList<>();

            while (data.next()) {
                products.add(resultSetToProductObject(data));
            }

            return products;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public Category getCategoryById(Integer categoryId) throws NotFoundException, DAORetrievalFailedException {
        sqlInstruction = "SELECT name FROM category WHERE id = ?";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, categoryId);

            ResultSet data = preparedStatement.executeQuery();
            

            if (data.next()) {
                return new Category(data.getInt("id"), data.getString("name"));
            }
            else {
                throw new NotFoundException(objectClassName, categoryId, DBRetrievalFailure.NO_ROW);
            }
            
        } catch (DAORetrievalFailedException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public Brand getBrandById(Integer brandId) throws NotFoundException, DAORetrievalFailedException {
        sqlInstruction = "SELECT name FROM brand WHERE id = ?";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, brandId);

            ResultSet data = preparedStatement.executeQuery();

            if (data.next()) {
                return  new Brand(data.getInt("id"), data.getString("name"));
            }
            else {
                throw new NotFoundException(objectClassName, brandId, DBRetrievalFailure.NO_ROW);
            }
        } catch (DAORetrievalFailedException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }

    }

    public ArrayList<Product> getAll() throws DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM product;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();

            ArrayList<Product> products = new ArrayList<>();

            while (data.next()) {
                products.add(resultSetToProductObject(data));
            }

            return products;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public Integer getOrCreateBrandByName(String brandName) throws DAORetrievalFailedException {
        sqlInstruction = "SELECT id FROM brand WHERE name = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, brandName);
            ResultSet data = preparedStatement.executeQuery();
            
            if (data.next()) {
                return data.getInt("id");
            } else {
                sqlInstruction = "INSERT INTO brand (name) VALUES (?) RETURNING id;";

                preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
                preparedStatement.setString(1, brandName);
                ResultSet generatedKey = preparedStatement.executeQuery();
                
                return (generatedKey.next() ? generatedKey.getInt("id") : getOrCreateBrandByName(brandName));
            }
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public ArrayList<Category> getAllCategories() throws DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM category;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();

            ArrayList<Category> categories = new ArrayList<>();

            while (data.next()) {
                categories.add(new Category(
                        data.getInt("id"), 
                        data.getString("name")
                        )
                );
            }

            return categories;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public ArrayList<Vat> getAllVats() throws DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM vat;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();

            ArrayList<Vat> vats = new ArrayList<>();

            while (data.next()) {
                vats.add(new Vat(
                        data.getString("type").charAt(0),
                        data.getInt("rate")
                        )
                );
            }

            return vats;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public int getCurrentStock(Long barcode) throws NotFoundException, DAORetrievalFailedException {
        sqlInstruction = "SELECT amount FROM product WHERE barcode = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setLong(1, barcode);

            ResultSet data = preparedStatement.executeQuery();

            if (data.next()) {
                return data.getInt("amount");
            } else {
                throw new NotFoundException(objectClassName, barcode, DBRetrievalFailure.NO_ROW);
            }

        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public ArrayList<Product> getOutOfStock() throws DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM product WHERE amount = 0;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            ResultSet data = preparedStatement.executeQuery();

            ArrayList<Product> products = new ArrayList<>();

            while (data.next()) {
                products.add(resultSetToProductObject(data));
            }

            return products;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        } 
    }
    
    public boolean exists(long barcode) throws DAORetrievalFailedException {
        sqlInstruction = "SELECT barcode FROM product WHERE barcode = ?";
        
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setLong(1, barcode);
            
            return preparedStatement.executeQuery().next();
            
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        } 
    }

    // Private methods

    private Product resultSetToProductObject(ResultSet data) throws DAORetrievalFailedException {
        Product product;
        
        try {
            product = new Product(
                    data.getLong("barcode"),
                    data.getString("name"),
                    data.getInt("amount"),
                    data.getBoolean("is_available"),
                    data.getString("vat_type").charAt(0),
                    data.getInt("category_id"),
                    data.getInt("brand_id"),
                    data.getBigDecimal("excl_vat_price"),
                    data.getDate("start_date").toLocalDate()
            );

            String description = data.getString("description");
            if (!data.wasNull()) {
                product.setDescription(description);
            }

        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }

        return product;
    }
}