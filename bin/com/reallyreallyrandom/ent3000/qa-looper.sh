#!/bin/bash
for i in {1..10000}
do
    java -jar ent3000.jar >> $1
    echo "$i"
done
