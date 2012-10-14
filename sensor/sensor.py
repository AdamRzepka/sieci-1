#!/usr/bin/python

import metrics
import socket
import time
import argparse

BUFFER_SIZE = 2048
DEFAULT_ADDRESS = '127.0.0.1'
DEFAULT_PORT = 12087
DEFAULT_RESOURCE = 'host'
DEFAULT_METRIC = 'cpu-usage'

METRICS = {'cpu-usage': lambda: 100 - metrics.cpu_stat.cpu_percents(sample_duration=0.05)['idle'],
           'mem-usage': lambda: metrics.mem_stat.mem_stats()[0],
           'mem-total': lambda: metrics.mem_stat.mem_stats()[1]}

class DataCollector:
    def __init__(self):
        self.readers = []

    def addSensor(self, resource, reader):
        self.readers.append((resource, reader))

    def collectData(self):
        return map(lambda r: (r[0], r[1]()), self.readers)
        
def makemessage(data, resource, metric):
    def joiner(record):
        return '<record>\n<resource>' + record[0] + '</resource>\n'\
            + '<value>' + str(record[1]) + '</value>\n</record>\n'
    return '%s#%s#%f' % (resource, metric, data)
    

def main(monitor_address, monitor_port, resource, metric):

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        s.connect((monitor_address, monitor_port))
    except:
        print 'Unable to connect to %s:%d' % (monitor_address, monitor_port)
        return 1
    print 'Connected to server'

    while True:
        try:
            s.send(makemessage(METRICS[metric](), resource, metric))
        except:
            print 'Server is no longer available'
            return 1

        time.sleep(1)

    print 'Closing'
    s.close()

    
if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Spawns sensor.')
    parser.add_argument('--monitor-address', '-a', type=str, default=DEFAULT_ADDRESS,
                        help='address of monitor')
    parser.add_argument('--monitor-port', '-p', type=int, default=DEFAULT_PORT,
                        help='port on monitor')
    parser.add_argument('--resource', '-r', type=str, default=DEFAULT_RESOURCE,
                        help='resource name')
    parser.add_argument('--metric', '-m', type=str, default=DEFAULT_METRIC,
                        help='metric name', choices=METRICS.keys())
    args = parser.parse_args()
    main(args.monitor_address, args.monitor_port, args.resource, args.metric)
