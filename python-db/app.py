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

# Gets all prducts
@app.route('/api/products', methods=['GET'])
def get_products():
    products = Product.query.all()
    return jsonify([product.to_dict() for product in products])

# Gets a certain product











# Test route
@app.route('/')
def index():
    return jsonify({'message': 'Inventory API is running'})

# Run the app and test
if __name__ == '__main__':
    app.run(debug=True)



