#!/bin/bash
echo "-----start-----"

process_id=`ps -ef | grep im-netty.jar | grep -v grep |awk '{print $2}'`
if [ $process_id ] ; then
sudo kill -9 $process_id
fi

source /etc/profile
nohup java -jar -Dspring.profiles.active=prod ~/im-netty/im-netty.jar > /dev/null 2>&1 &

echo "-----end-----"
