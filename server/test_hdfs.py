from hdfs import *
client = Client("localhost:9000")
status = client.status(
    "/",
)
print(status)
