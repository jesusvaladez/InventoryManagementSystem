from flask import Flask, jsonify, request
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
    products = Product.query.all()
    return jsonify([product.to_dict() for product in products])

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
        return jsonify(({'error': 'Product with this SKU already exists'})), 400

    # Finally, create the product
    new_product = Product(
        name=data.get('name'),
        description=data.get('description', ''),
        sku=data.get('sku'),
        price=data.get('price', 0.0),
        quantity=data.get('quantity', 0)
    )

    # Add it to database
    db.session.add(new_product)
    db.session.commit()

    return jsonify(new_product.to_dict()), 201

@app.route('/api/products/<int:product_id>', methods=['PUT'])
def update_product(product_id):

    # We get the product that we're updating
    product = Product.query.get(product_id)

    # If product does not exist or is invalid
    if not product:
        return jsonify({'error': 'Product does not already exist'}), 404

    # If product does exist
    data = request.get_json()
    if not data:
        return jsonify({'error': 'No data used or provided'}), 400

    if 'name' in data:
        product.name = data['name']
    if 'description' in data:
        product.description =data['description']
    if 'sku' in data:
        product.sku = data['sku']
    if 'price' in data:
        product.price = data['price']
    if 'quantity' in data:
        product.quantity = data['quantity']

    db.session.commit()
    return jsonify(product.to_dict()), 200

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

# Test route
@app.route('/')
def index():
    return jsonify({'message': 'Inventory API is running'})

# Run the app and test
if __name__ == '__main__':
    app.run(debug=True)



