////////////////////////////////////////////////////////////////////////////
//               Java Implementation of Shorten Decoder                   //
//                 Copyright (c) 2011 Peter McQuillan                     //
//                          All Rights Reserved.                          //
//      Distributed under the BSD Software License (see license.txt)      //
////////////////////////////////////////////////////////////////////////////

This package contains a Java implementation of an Shorten audio decoder.
The code is based on v3.6.1 of the Shorten C code.

It is packaged with a demo command-line program that accepts a
Shorten audio file as input and outputs a RIFF wav file.

The Java source code files can be compiled to class files very simply by going 
to the directory where you have downloaded the .java files and running

javac *.java

To run the demo program, use the following command

java DecoderDemo <input.shn> <output.wav>

where input.shn is the name of the Shorten file you wish to decode to a WAV file.

This implementation accepts Shorten files that have been generated from either WAV
or AIFF files.

The code is based around decoding PCM data, but it can also successfully decode files
using:
TYPE_AU1	original lossless ulaw  (8-bit) 
TYPE_AU2	new ulaw with zero mapping  (8-bit)
TYPE_AU3 	lossless alaw (8-bit)


Please direct any questions or comments to beatofthedrum@gmail.com