from flask import Flask, jsonify, request

from models.category import Category
from models.product import db, Product
import os

# Initialization of Flask App
app = Flask(__name__)

# Set database
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'inventory.db')
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

# Set database with application
db.init_app(app)

# Create the tables
@app.before_request
def create_tables():
    db.create_all()

# Gets all products
@app.route('/api/products', methods=['GET'])
def get_products():
    # Start with query object, not .all()
    query = Product.query

    try:
        # Search feature
        search = request.args.get('search')
        if search:
            query = query.filter(Product.name.ilike(f'%{search}%'))

        minimum_price = request.args.get('minimum_price')
        if minimum_price:
            query = query.filter(Product.price >= float(minimum_price))

        maximum_price = request.args.get('maximum_price')
        if maximum_price:
            query = query.filter(Product.price <= float(maximum_price))  # Fixed: <= not >=

        # Category filter - Added missing assignment
        category_id = request.args.get('category_id')
        if category_id:
            query = query.filter(Product.category_id == int(category_id))

        # Stock availability
        in_stock = request.args.get('in_stock')
        if in_stock:
            if in_stock.lower() == 'true':
                query = query.filter(Product.quantity > 0)
            elif in_stock.lower() == 'false':
                query = query.filter(Product.quantity == 0)

        # Execute query at the end
        products = query.all()
        return jsonify([product.to_dict() for product in products])

    except ValueError:
        return jsonify({'error': 'Invalid parameter values. Price and ID must be numerical values'}), 400
    except Exception as e:
        return jsonify({'error': 'Filtering products failed'}), 500

# Gets a certain product
@app.route('/api/products/<int:product_id>', methods=['GET'])
def get_product(product_id):
    product = Product.query.get_or_404(product_id)
    return jsonify(product.to_dict())

# Creates a new product
@app.route('/api/products', methods=['POST'])
def create_product():
    data = request.json

    if not data or not data.get('name') or not data.get('sku'):
        return jsonify({'error': 'Name and SKU are required'}), 400

    # Checks if product SKU exists
    existing_product = Product.query.filter_by(sku=data.get('sku')).first()
    if existing_product:
        return jsonify({'error': 'Product with this SKU already exists'}), 400

    # Validate category_id if provided
    category_id = data.get('category_id')
    if category_id is not None:
        category = Category.query.get(category_id)
        if not category:
            return jsonify({'error': 'Category does not exist'}), 400

    # Create the product
    new_product = Product(
        name=data.get('name'),
        description=data.get('description', ''),
        sku=data.get('sku'),
        price=data.get('price', 0.0),
        quantity=data.get('quantity', 0),
        category_id=category_id  # Assign category_id (can be None)
    )

    # Add it to database
    db.session.add(new_product)
    db.session.commit()

    return jsonify(new_product.to_dict()), 201

# Updates product based on its ID and whether it exists
@app.route('/api/products/<int:product_id>', methods=['PUT'])
def update_product(product_id):
    # We get the product that we're updating
    product = Product.query.get(product_id)

    # If product does not exist or is invalid
    if not product:
        return jsonify({'error': 'Product not found'}), 404

    # If product does exist
    data = request.get_json()
    if not data:
        return jsonify({'error': 'No data provided'}), 400

    # Update basic fields
    if 'name' in data:
        product.name = data['name']
    if 'description' in data:
        product.description = data['description']
    if 'sku' in data:
        product.sku = data['sku']
    if 'price' in data:
        product.price = data['price']
    if 'quantity' in data:
        product.quantity = data['quantity']

    # Handle category_id update
    if 'category_id' in data:
        category_id = data['category_id']

        # If category_id is None, remove from category
        if category_id is None:
            product.category_id = None
        else:
            # Validate that the category exists
            category = Category.query.get(category_id)
            if not category:
                return jsonify({'error': 'Category does not exist'}), 400
            product.category_id = category_id

    db.session.commit()
    return jsonify(product.to_dict()), 200

# Deletes a certain product based on the ID chosen
@app.route('/api/products/<int:product_id>', methods=['DELETE'])
def delete_product(product_id):
    # Get the product we're deleting
    product = Product.query.get(product_id)

    if not product:
        return jsonify({'error': 'product not found'}), 404

    # Remove product from the database
    db.session.delete(product)
    # Commit changes
    db.session.commit()

    return jsonify({'message': f'Product: {product_id} deleted successfully'}), 200

# Gets all products that do not belong to a category
@app.route('/api/products/uncategorized', methods=['GET'])
def get_uncategorized_products():
    uncategorized_products = Product.query.filter(Product.category_id == None).all()
    # Fixed variable name in list comprehension
    return jsonify([product.to_dict() for product in uncategorized_products])

# Test route
@app.route('/')
def index():
    return jsonify({'message': 'Inventory API is running'})

# Gets all categories
@app.route('/api/categories', methods=['GET'])
def get_categories():
    # Start with query object, not .all()
    query = Category.query

    try:
        # Search feature
        search = request.args.get('search')
        if search:
            query = query.filter(Category.name.ilike(f'%{search}%'))

        # Execute query at the end
        categories = query.all()
        return jsonify([category.to_dict() for category in categories])
    except Exception as e:
        return jsonify({'error': 'Failed filtering categories'}), 500  # Added missing return

# Gets certain category
@app.route('/api/categories/<int:category_id>', methods=['GET'])
def get_category(category_id):
    category = Category.query.get_or_404(category_id)

    included_products = request.args.get('include_products', '').lower() == 'true'

    result = category.to_dict()

    # Add products if requested
    if included_products:
        products = Product.query.filter_by(category_id=category_id).all()
        result['products'] = [product.to_dict() for product in products]

    return jsonify(result)

# Gets all products in a category
@app.route('/api/categories/<int:category_id>/products', methods=['GET'])
def get_category_products(category_id):
    # Error check to see if category exists
    category = Category.query.get_or_404(category_id)

    # Gets all the products in the category
    products = Product.query.filter_by(category_id=category_id).all()

    return jsonify({
        'category': category.to_dict(),
        'products': [product.to_dict() for product in products]
    })

# Creates a new category
@app.route('/api/categories', methods=['POST'])
def create_category():
    data = request.json

    if not data or not data.get('name'):
        return jsonify({'error': 'Name is required'}), 400

    existing_category = Category.query.filter_by(name=data.get('name')).first()
    if existing_category:
        return jsonify({'error': 'Category with that name already exists'}), 400

    new_category = Category(
        name=data.get('name'),
        description=data.get('description', '')
    )

    db.session.add(new_category)
    db.session.commit()

    return jsonify(new_category.to_dict()), 201

# Updates existing category
@app.route('/api/categories/<int:category_id>', methods=['PUT'])
def update_category(category_id):
    # Gets category we're trying to update
    category = Category.query.get(category_id)
    # Error check
    if not category:
        return jsonify({'error': 'Category does not exist'}), 404

    # For if category exists
    data = request.get_json()
    if not data:
        return jsonify({'error': 'No data used or provided'}), 400

    if 'name' in data and data['name'] != category.name:
        existing_category = Category.query.filter_by(name=data['name']).first()
        if existing_category and existing_category.id != category_id:
            return jsonify({'error': 'Category with that name already exists'}), 400
        category.name = data['name']

    if 'description' in data:
        category.description = data['description']

    db.session.commit()
    return jsonify(category.to_dict()), 200

# Delete a category
@app.route('/api/categories/<int:category_id>', methods=['DELETE'])
def delete_category(category_id):
    # Find the Category ID
    category = Category.query.get(category_id)
    if not category:
        return jsonify({'error': 'Category not found'}), 404

    # Check if Category has products connected to it
    connected_products = Product.query.filter_by(category_id=category_id).all()

    # Sets category_id to NULL if they are connected
    if connected_products:
        for product in connected_products:
            product.category_id = None

    # Remove the category from the db - Fixed typo
    db.session.delete(category)

    # Commit all changes made
    db.session.commit()

    return jsonify({'message': f'Category {category_id} deleted successfully. {len(connected_products)} products were updated'}), 200

# Run the app and test
if __name__ == '__main__':
    app.run(debug=True)