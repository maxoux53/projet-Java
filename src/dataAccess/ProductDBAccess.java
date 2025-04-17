package dataAccess;

import model.Brand;
import model.Category;
import model.Product;
import model.Vat;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProductDBAccess extends DBAccess implements IProductDAO {
    private static final String objectClassName;

    static {
        objectClassName = Product.class.getSimpleName().toLowerCase();
    }

    public int create(Product product) throws InsertionFailedException, DAORetrievalFailedException {
        sqlInstruction = "INSERT INTO product (name, description, amount, is_available, vat_type, category_id, brand_id, excl_vat_price, start_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getAmount());
            preparedStatement.setBoolean(4, product.getAvailable());
            preparedStatement.setString(5, String.valueOf(product.getVatType()));
            preparedStatement.setInt(6, product.getCategoryId());
            preparedStatement.setInt(7, product.getBrandId());
            preparedStatement.setDouble(8, product.getExclVatPrice());
            preparedStatement.setDate(9, Date.valueOf(product.getStartDate()));

            try {
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new InsertionFailedException(objectClassName, product.getBarcode(), e.getMessage());
            }
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public void deleteByBarcode(int barcode) throws DeleteFailedException, DAORetrievalFailedException {
        sqlInstruction = "DELETE FROM product WHERE barcode = ?;";

        nullifyProductReferencesFromOrderLine(barcode);

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, barcode);

            try {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DeleteFailedException(objectClassName, barcode, e.getMessage());
            }
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    private void nullifyProductReferencesFromOrderLine(int barcode) throws DAORetrievalFailedException {
        sqlInstruction = "UPDATE order_line SET product_barcode = NULL WHERE product_barcode = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, barcode);
            preparedStatement.executeUpdate();
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public int edit(Product product) throws UpdateFailedException, DAORetrievalFailedException {
        sqlInstruction = "UPDATE product SET name = ?, description = ?, amount = ?, is_available = ?, vat_type = ?, category_id = ?, brand_id = ?, excl_vat_price = ?, start_date = ? WHERE barcode = ?;";
        int barcode = product.getBarcode();

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getAmount());
            preparedStatement.setBoolean(4, product.getAvailable());
            preparedStatement.setString(5, String.valueOf(product.getVatType()));
            preparedStatement.setInt(6, product.getCategoryId());
            preparedStatement.setInt(7, product.getBrandId());
            preparedStatement.setDouble(8, product.getExclVatPrice());
            preparedStatement.setDate(9, Date.valueOf(product.getStartDate()));
            preparedStatement.setInt(10, barcode);

            try {
                return preparedStatement.executeUpdate();
            } catch (SQLTimeoutException e) {
                throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
            } catch (SQLException e) {
                throw new UpdateFailedException(objectClassName, barcode, e.getMessage());
            }

        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public Product findByBarcode(int barcode) throws NotFoundException, DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM product WHERE barcode = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, barcode);

            data = preparedStatement.executeQuery();

            if (data.next()) {
                String name;
                String description;
                int amount;
                boolean isAvailable;
                char vatType;
                int categoryId;
                int brandId;
                double exclVatPrice;
                Date startDate;

                Product product = new Product(barcode);

                name = data.getString("name");
                if (!data.wasNull()) {
                    product.setName(name);
                }

                description = data.getString("description");
                if (!data.wasNull()) {
                    product.setDescription(description);
                }

                amount = data.getInt("amount");
                if (!data.wasNull()) {
                    product.setAmount(amount);
                }

                isAvailable = data.getBoolean("is_available");
                if (!data.wasNull()) {
                    product.setAvailable(isAvailable);
                }

                vatType = data.getString("vat_type").charAt(0);
                if (!data.wasNull()) {
                    product.setVatType(vatType);
                }

                categoryId = data.getInt("category_id");
                if (!data.wasNull()) {
                    product.setCategoryId(categoryId);
                }

                brandId = data.getInt("brand_id");
                if (!data.wasNull()) {
                    product.setBrandId(brandId);
                }

                exclVatPrice = data.getDouble("excl_vat_price");
                if (!data.wasNull()) {
                    product.setExclVatPrice(exclVatPrice);
                }

                startDate = data.getDate("start_date");
                if (!data.wasNull()) {
                    product.setStartDate(startDate.toLocalDate());
                }

                return product;
            } else {
                throw new NotFoundException(objectClassName, barcode, DBRetrievalFailure.NO_ROW.toString());
            }
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public ArrayList<Product> findByName(String name) throws DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM product WHERE name ILIKE ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, (name + "%"));

            data = preparedStatement.executeQuery();

            ArrayList<Product> products = new ArrayList<>();

            Product product;
            String description;
            int amount;
            boolean isAvailable;
            char vatType;
            int categoryId;
            int brandId;
            double exclVatPrice;
            Date startDate;


            while (data.next()) {
                product = new Product(data.getInt("barcode"));

                name = data.getString("name");
                if (!data.wasNull()) {
                    product.setName(name);
                }

                description = data.getString("description");
                if (!data.wasNull()) {
                    product.setDescription(description);
                }

                amount = data.getInt("amount");
                if (!data.wasNull()) {
                    product.setAmount(amount);
                }

                isAvailable = data.getBoolean("is_available");
                if (!data.wasNull()) {
                    product.setAvailable(isAvailable);
                }

                vatType = data.getString("vat_type").charAt(0);
                if (!data.wasNull()) {
                    product.setVatType(vatType);
                }

                categoryId = data.getInt("category_id");
                if (!data.wasNull()) {
                    product.setCategoryId(categoryId);
                }

                brandId = data.getInt("brand_id");
                if (!data.wasNull()) {
                    product.setBrandId(brandId);
                }

                exclVatPrice = data.getDouble("excl_vat_price");
                if (!data.wasNull()) {
                    product.setExclVatPrice(exclVatPrice);
                }

                startDate = data.getDate("start_date");
                if (!data.wasNull()) {
                    product.setStartDate(startDate.toLocalDate());
                }

                products.add(product);
            }

            return products;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public ArrayList<Product> findAll() throws DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM product;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.executeQuery();

            ArrayList<Product> products = new ArrayList<>();

            Product product;
            int barcode;
            String name;
            String description;
            int amount;
            boolean isAvailable;
            char vatType;
            int categoryId;
            int brandId;
            double exclVatPrice;
            Date startDate;

            while (data.next()) {
                product = new Product(data.getInt("barcode"));

                name = data.getString("name");
                if (!data.wasNull()) {
                    product.setName(name);
                }

                description = data.getString("description");
                if (!data.wasNull()) {
                    product.setDescription(description);
                }

                amount = data.getInt("amount");
                if (!data.wasNull()) {
                    product.setAmount(amount);
                }

                isAvailable = data.getBoolean("is_available");
                if (!data.wasNull()) {
                    product.setAvailable(isAvailable);
                }

                vatType = data.getString("vat_type").charAt(0);
                if (!data.wasNull()) {
                    product.setVatType(vatType);
                }

                categoryId = data.getInt("category_id");
                if (!data.wasNull()) {
                    product.setCategoryId(categoryId);
                }

                brandId = data.getInt("brand_id");
                if (!data.wasNull()) {
                    product.setBrandId(brandId);
                }

                exclVatPrice = data.getDouble("excl_vat_price");
                if (!data.wasNull()) {
                    product.setExclVatPrice(exclVatPrice);
                }

                startDate = data.getDate("start_date");
                if (!data.wasNull()) {
                    product.setStartDate(startDate.toLocalDate());
                }

                products.add(product);
            }

            return products;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public int currentStock(int barcode) throws NotFoundException, DAORetrievalFailedException {
        sqlInstruction = "SELECT amount FROM product WHERE barcode = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, barcode);

            data = preparedStatement.executeQuery();

            if (data.next()) {
                return data.getInt("amount");
            } else {
                throw new NotFoundException(objectClassName, barcode, DBRetrievalFailure.NO_ROW.toString());
            }

        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public ArrayList<Integer> outOfStock() throws DAORetrievalFailedException {
        sqlInstruction = "SELECT barcode FROM product WHERE amount = 0;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            data = preparedStatement.executeQuery();

            ArrayList<Integer> products = new ArrayList<>();
            int barcode;

            while (data.next()) {
                barcode = data.getInt("barcode");
                if (!data.wasNull()) {
                    products.add(barcode);
                }
            }

            return products;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public ArrayList<Category> getAllCategories() throws DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM category;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            ArrayList<Category> categories = new ArrayList<>();
            Category category;
            String label;

            while (data.next()) {
                category = new Category(data.getInt("category_id"));

                label = data.getString("label");
                if (!data.wasNull()) {
                    category.setLabel(label);
                }

                categories.add(category);
            }

            return categories;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public int brand(String brandName) throws DAORetrievalFailedException {
        boolean exists = true;

        do {
            sqlInstruction = "SELECT id FROM brand WHERE name = ?;";

            try {
                preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
                preparedStatement.setString(1, brandName);
                data = preparedStatement.executeQuery();

                exists = data.next();

                if (exists) {
                    return data.getInt("id");
                } else {
                    sqlInstruction = "INSERT INTO brand (name) VALUES (?);";

                    preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
                    preparedStatement.setString(1, brandName);
                    preparedStatement.executeUpdate();
                    data = preparedStatement.getGeneratedKeys();
                }
            } catch (SQLTimeoutException e) {
                throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
            } catch (SQLException e) {
                throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
            }
        } while (!exists);
    }

    public ArrayList<Vat> getAllVatTypes() throws DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM vat_type;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            ArrayList<Vat> vatTypes = new ArrayList<>();
            Vat vatType;
            int rate;

            while (data.next()) {
                vatType = new Vat(data.getString("type").charAt(0));

                rate = data.getInt("rate");
                if (!data.wasNull()) {
                    vatType.setRate(rate);
                }

                vatTypes.add(vatType);
            }

            return vatTypes;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }
}
