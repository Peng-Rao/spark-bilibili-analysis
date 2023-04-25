import happybase
from flask import Flask, jsonify
from flask_mysqldb import MySQL
from flask_cors import CORS

app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "*"}})  # 允许所有域名跨域

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


# @app.route('/ProvinceOrder', methods=['GET'])
# def get_ProvinceOrder_data():
#     cur = mysql.connection.cursor()
#     cur.execute("SELECT province_name, count FROM tb_province_order")
#     data = cur.fetchall()
#     cur.close()
#     return jsonify(data)


@app.route('/StatusContrast', methods=['GET'])
def get_StatusContrast_data():
    cur = mysql.connection.cursor()
    cur.execute("SELECT order_status, count FROM tb_status_contrast")
    data = cur.fetchall()
    cur.close()
    return jsonify(data)


@app.route('/TimeOrder', methods=['GET'])
def get_TimeOrder_data():
    cur = mysql.connection.cursor()
    cur.execute("SELECT time_point, count FROM tb_time_order")
    data = cur.fetchall()
    cur.close()
    return jsonify(data)


@app.route('/OrderDetail', methods=['GET'])
def get_OrderDetail_data():
    cur = mysql.connection.cursor()
    cur.execute("SELECT order_id, sku_name, order_price, sku_num, create_time FROM order_detail")
    data = cur.fetchall()
    cur.close()
    return jsonify(data)


@app.route('/ProvinceOrder', methods=['GET'])
def get_test_data():
    conn = happybase.Connection('192.168.1.108')
    table = conn.table('tb_province_order')
    # 获取所有数据
    data = table.scan()
    # 遍历数据
    result = []
    for key, value in data:
        value1 = value[b'city:province_name']
        value1_str = value1.decode('utf-8')
        value2 = value[b'city:count']
        value2_str = value2.decode('utf-8')
        result.append({'province_name': value1_str, 'count': value2_str})
    # 将result转换为jsonify格式
    result = jsonify(result)
    # 关闭连接
    conn.close()
    return result


if __name__ == '__main__':
    app.run()
