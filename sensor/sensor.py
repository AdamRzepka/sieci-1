#!/usr/bin/python

import metrics
import socket
import time

BUFFER_SIZE = 2048
address = '127.0.0.1'
port = 12087
message = 'message from sensor'

class DataCollector:
    def __init__(self):
        self.readers = []

    def addSensor(self, resource, reader):
        self.readers.append((resource, reader))

    def collectData(self):
        return map(lambda r: (r[0], r[1]()), self.readers)
        
def makemessage(data):
    def joiner(record):
        return '<record>\n<resource>' + record[0] + '</resource>\n'\
            + '<value>' + str(record[1]) + '</value>\n</record>\n'

    return '<measurement>\n<timestamp>' + time.ctime() + '</timestamp>\n'\
        + '<data>\n' + ''.join(map(joiner, data)) + '</data>\n</measurement>'
    
    

def main():
    collector = DataCollector()
    collector.addSensor('cpu-usage',
                        lambda:
                            100 - metrics.cpu_stat.cpu_percents(sample_duration=0.05)['idle'])
    collector.addSensor('mem-usage', lambda: metrics.mem_stat.mem_stats()[0])
    collector.addSensor('mem-total', lambda: metrics.mem_stat.mem_stats()[1])

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((address, port))
    print 'Connected to server'

    while True:
        data = collector.collectData()
        s.send(makemessage(data))
        time.sleep(1)

    print 'Closing'
    s.close()

    
if __name__ == '__main__':
    main()
