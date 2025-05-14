from flask_sqlalchemy import SQLAlchemy
from datetime import datetime

db = SQLAlchemy()

class Product(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    description = db.Column(db.String(50), unique=True)
    sku = db.Column(db.String(50), unique=True)
    price = db.Column(db.Float, default=0.0)
    quantity = db.Column(db.Integer, default=0.0)
    created_at = db.Column(db.DateTime, default=datetime.utcnow)

    # Add foreign key to link to category
    category_id = db.Column(db.Integer, db.ForeignKey('category.id'), nullable=True)

    def __repr__(self):
        return f'<Product {self.name}>'

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'description': self.description,
            'sku': self.sku,
            'price': self.price,
            'quantity': self.quantity,
            'category_id': self.category_id,
            'category_name': self.category.name if self.category else None
        }