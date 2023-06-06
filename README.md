                                      
                       _      ____    
                      | |    |___ \   
         ___   _ __   | |_     __) |  
        / _ \ | '_ \  |  _|   |__ <   
       |  __/ | | | | | |_    ___) |  
        \___| |_| |_|  \__|  |____/   
                                  

    A new randomness testing suite for TRNG Makers generating in
    the sub 1 MB space.  Successor to John Walker’s venerable **ent**.
     Call as:  java -jar ent3000.jar [options] [input-file]

     Options:     --help   Print this message.
     input-file:           The file of samples to be tested.  If no
                           input file is supplied, an internal Java
                           PRNG (L64X256MixRandom) will be tested.



ent3000 will only test file sizes of 25 kB, 50 kB, 75 kB, 100 kB, 150 kB, 
200 kB, 300 kB, 400 kB, 500 kB, 600 kB, 700 kB, 800 kB, 900 kB and 
1 MB.  The input file will be truncated to the greatest smaller size. 
The orphaned bytes will not be tested.

A p value will be returned for every test performed, and a PASS/FAIL
determination made.  The main tests’ level of significance (α) is 0.05.  Each
test may be interpreted as either a one sided or two sided test.  α = 0.001 
for the sanity (Monobit) test.  This test must be passed first before the
others can be run.

Occasionally, the test determination will be OoC (Out of Calibration).  This
implies that the test file is so poor that a p value cannot be ascertained
from the internal empirical calibration data.  Such a result is equivalent to
p < 0.01 or p > 0.99.

Read rational, quality analyses and much more at www.reallyreallyrandom.com.

This suite is intended for Java 17.