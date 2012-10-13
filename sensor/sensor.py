import metrics
import socket

BUFFER_SIZE = 2048
address = '127.0.0.1'
port = 12087
message = 'message from sensor'

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((address, port))
print 'Connected to server'
s.send(message)
s.close()

print 'Message sent'

print metrics.cpu_stat.procs_running()
