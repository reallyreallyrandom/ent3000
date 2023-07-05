#!/bin/bash

echo "Ran:" $(grep "ent3000" $1  | wc -l)
echo "Insanes: " $(grep "Insane" $1  | wc -l)

echo "Entropy: " $(grep "Entropy" $1  | grep "FAIL" | wc -l)
echo "Compression: " $(grep "Compression" $1  | grep "FAIL" | wc -l)
echo "Chi: " $(grep "Chi" $1  | grep "FAIL" | wc -l)
echo "Mean: " $(grep "Mean" $1  | grep "FAIL" | wc -l)
echo "Pi: " $(grep "Pi" $1  | grep "FAIL" | wc -l)
echo "UnCorrelation: " $(grep "UnCorrelation" $1  | grep "FAIL" | wc -l)
