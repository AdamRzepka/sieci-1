#!/bin/bash

#java -jar monitor/bin/monitor.jar &
sleep 1
python sensor/sensor.py --resource host1 --metric cpu-usage &
python sensor/sensor.py --resource host1 --metric mem-usage &
python sensor/sensor.py --resource host1 --metric mem-total &
python sensor/sensor.py --resource host2 --metric cpu-usage &
python sensor/sensor.py --resource host2 --metric mem-usage &
sleep 1
#java -jar client/bin/client.jar