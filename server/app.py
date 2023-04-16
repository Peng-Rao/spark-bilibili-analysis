from flask import Flask, jsonify
from flask_mysqldb import MySQL
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

# MySQL配置
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = '123456'
app.config['MYSQL_DB'] = 'e_commerce'
app.config['MYSQL_CURSORCLASS'] = 'DictCursor'

mysql = MySQL(app)


@app.route('/OrderPriceRange', methods=['GET'])
def get_OrderPriceRange_data():
    cur = mysql.connection.cursor()
    cur.execute("SELECT price_range, count FROM tb_price_range")
    data = cur.fetchall()
    cur.close()
    return jsonify(data)


@app.route('/SucceedGoodsTop', methods=['GET'])
def get_SucceedGoodsTop_data():
    cur = mysql.connection.cursor()
    cur.execute("SELECT item_name, count FROM succeed_goods_top20")
    data = cur.fetchall()
    cur.close()
    return jsonify(data)


@app.route('/ProvinceOrder', methods=['GET'])
def get_ProvinceOrder_data():
    cur = mysql.connection.cursor()
    cur.execute("SELECT province_name, count FROM tb_province_order")
    data = cur.fetchall()
    cur.close()
    return jsonify(data)
