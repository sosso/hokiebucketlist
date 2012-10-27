from models import User, Item, ItemCompletion
from pkg_resources import StringIO
from sqlalchemy.sql.functions import random
import dbutils
import os
import tornado

#logger = logging.getLogger('modelhandlers')

class ItemCompletionHandler(tornado.web.RequestHandler):
    @tornado.web.asynchronous
    def post(self):
        username = self.get_argument('username')
        item_id = self.get_argument('item_id')
        file1 = self.request.files['file1'][0]
        original_fname = file1['filename']

        extension = os.path.splitext(original_fname)[1]
        final_filename = username + extension

        session = dbutils.Session()
        try:
            user = dbutils.get_or_create(session, User, username)
            item = dbutils.get_or_create(session, Item, item_id)
            item_completion = ItemCompletion(user.id, item.id)

            output_file = open("uploads/" + item_id + "/" + final_filename, 'w')
            output_file.write(file1['body'])

            session.add(item_completion)
            session.commit()
        except:
            session.rollback()
        dbutils.Session.remove()
        self.finish("You have crossed item " + item_id + " off your bucket list!")

class GetCompletedItemsHandler(tornado.web.RequestHandler):
    @tornado.web.asynchronous
    def get(self):
        username = self.get_argument('username')

        session = dbutils.Session()
        try:
            user = dbutils.get_or_create(session, User, username)
            pass
#            completed_items = session.Query(ItemCompletion).filter()
        except:
            session.rollback()
        dbutils.Session.remove()
        self.finish("")
