import happybase

connection = happybase.Connection('192.168.1.108', 9090)
connection.open()
connection.tables()
