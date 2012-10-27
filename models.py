from sqlalchemy import Column, Integer, VARCHAR, INTEGER
from sqlalchemy.ext.associationproxy import association_proxy
from sqlalchemy.orm import relationship
from sqlalchemy.schema import ForeignKey
import dbutils

class User(dbutils.Base):
    __tablename__ = 'user'
    #column definitions
    id = Column(u'id', INTEGER(), primary_key=True, nullable=False)
    username = Column(u'username', VARCHAR(length=32), nullable=False)

    item_completions = relationship('ItemCompletion', backref='user') #one to many
#    completed_items = association_proxy('completed_items', 'item', creator=lambda i: ItemCompletion(item=i))


class Item(dbutils.Base):
    __tablename__ = 'item'
    id = Column(Integer, primary_key=True)
    item_id = Column(Integer)
    description = Column(VARCHAR(length=255))

    item_completions = relationship('ItemCompletion', backref='item')
#    users_who_have_completed = association_proxy('users_who_have_completed', 'user', creator=lambda u: ItemCompletion(user=u))

    def __init__(self, item_id, description=''):
        self.item_id = item_id
        self.description = description

    def __repr__(self):
        return '<Item %d>' % self.list_item_number


class ItemCompletion(dbutils.Base):
    __tablename__ = 'itemcompletion'
    id = Column(Integer, primary_key=True)

    user_id = Column(Integer, ForeignKey('user.id'))
#    user = relationship("User", backref="completed_items")

    item_id = Column(Integer, ForeignKey('item.id'))
#    item = relationship("Item", backref="users_who_have_completed")

    def __init__(self, user_id, item_id):
        self.user_id = user_id
        self.item_id = item_id

    def __repr__(self):
        return '<ItemCompletion %d @ %d>' % (self.user_id, self.item_id)
