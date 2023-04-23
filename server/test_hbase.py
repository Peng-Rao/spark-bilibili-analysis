import happybase

connection = happybase.Connection('localhost', 9090)
connection.open()
# 获取 hbase 里的所有表
tables = connection.tables()
print(tables)
