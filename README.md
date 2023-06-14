                                                           
                     _      ____     ___     ___     ___   
                    | |    |___ \   / _ \   / _ \   / _ \  
       ___   _ __   | |_     __) | | | | | | | | | | | | | 
      / _ \ | '_ \  |  _|   |__ <  | | | | | | | | | | | | 
     |  __/ | | | | | (_    ___) | | |_| | | |_| | | |_| | 
      \___| |_| |_|  \__|  |____/   \___/   \___/   \___/  
                                                           



    A new randomness testing suite for TRNG Makers generating in
    the sub 1 MB space.  Successor to John Walker’s venerable `ent`.
        Call as:  java -jar ent3000xxx.jar [options] [input-file]

        Options:     --help   Print this message.
        input-file:           The file of binary samples to be tested.  
                              If no input file is supplied, an internal 
                              native CSPRNG will be tested.


ent3000 will only test binary file sizes of 25 kB, 50 kB, 75 kB, 100 kB, 150 kB, 200 kB, 300 kB, 400 kB, 500 kB, 600 kB, 700 kB, 800 kB, 900 kB and 1 MB.  The input file will be truncated to the greatest smaller size.  The orphaned bytes will not be tested.

A p value is returned for every test performed, and a PASS/FAIL determination made.  The main tests’ level of significance (α) is 0.05.  Each test may be interpreted as either a one sided or two sided test.  α = 0.001 for the sanity (Monobit) test.  This test must be passed first before the others can be run.  The sanity test has 1/50th of the strength of the main tests.  If it fails, there’s really no point in running the others.

Note that randomness is pesky, thus any individual main test will fail 5% of the time when testing even the most random of samples.  This is a (better to be safe than sorry) security feature to minimise type II errors (false-negatives).

Occasionally (2% of the time), the test determination will be OoC (Out of Calibration).  This implies that the test file is so poor that a p value cannot be ascertained from the internal empirical calibration data.  Such a result is equivalent to p < 0.01 or p > 0.99.

Read rational, quality analyses and much more at http://www.reallyreallyrandom.com.  Our PGP key is [here](http://www.reallyreallyrandom.com/contact-us/pgp-key/) for verification.

----------------------------------------------------------------------------

MIT License:

Copyright (c) 2023 Paul Uszak. Email: paul.uszak (at) gmail.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
