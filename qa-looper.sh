#!/bin/bash

for i in {1..15000}
do
    java -jar ent3000-0.3.0-beta.jar >> $1
    echo "$i"
done
