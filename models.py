from sqlalchemy import Column, Integer, VARCHAR, INTEGER
from sqlalchemy.ext.associationproxy import association_proxy
from sqlalchemy.orm import relationship, backref
from sqlalchemy.schema import ForeignKey
import dbutils



class User(dbutils.Base):
    __tablename__ = 'user'
    #column definitions
    id = Column(u'id', INTEGER(), primary_key=True, nullable=False)
    username = Column(u'username', VARCHAR(length=32), nullable=False)

#    item_completions = relationship('ItemCompletion', backref='user') #one to many
    completed_items = association_proxy('completed_items', 'item')

class ItemCompletion(dbutils.Base):
    __tablename__ = 'itemcompletion'

    user_id = Column(Integer, ForeignKey('user.id'), primary_key=True)
    item_id = Column(Integer, ForeignKey('item.id'), primary_key=True)
    file_path = Column(VARCHAR(255))

    user = relationship(User, backref=backref("completed_items", cascade="all, delete-orphan"))

    item = relationship("Item")

    def __init__(self, user_id, item_id, file_path=None):
        self.user_id = user_id
        self.item_id = item_id
        self.file_path = file_path

    def __repr__(self):
        return '<ItemCompletion %d @ %d>' % (self.user_id, self.item_id)


class Item(dbutils.Base):
    __tablename__ = 'item'
    id = Column(Integer, primary_key=True)
    item_id = Column(Integer)
    description = Column(VARCHAR(length=255))

    def __init__(self, item_id, description=''):
        self.item_id = item_id
        self.description = description

    def __repr__(self):
        return '<Item %d>' % self.item_id

    def serialize(self):
        return {'Item number': self.item_id,
                'Description': self.description
                }




