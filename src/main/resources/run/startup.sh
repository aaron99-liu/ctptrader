#!/bin/sh
baseDir=$(cd "$(dirname "$0")"; pwd)
cp=.
for file in $baseDir/libs/*.jar
do
   cp=$cp:$file
done
java -server -Xms512M -Xmx1024M -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=512M -cp $cp com.smart.quant.Application >> $baseDir/stdout.out 2>&1 &
