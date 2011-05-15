/*
** Defines.java
**
** Copyright (c) 2011 Peter McQuillan
**
** All Rights Reserved.
**                       
** Distributed under the BSD Software License (see license.txt)  
**
*/

package com.beatofthedrum.shortendecoder;

public class Defines
{
	static int BYTES_TO_DECODE	= 4096;				// This is the minimum number of bytes to decode - it may return more

	static int MAX_VERSION = 7;
	static int MAX_SUPPORTED_VERSION = 2;
	static int[] MAGIC = {97,106,107,103,0};			// ASCII values for "ajkg"
	static int UNDEFINED_UINT = -1;
	static int DEFAULT_V0NMEAN = 0;
	static int DEFAULT_V2NMEAN = 4;
	static int MASKTABSIZE  = 33;

	static int DEFAULT_BLOCK_SIZE = 256;
	static int DEFAULT_NSKIP = 0;
	static int DEFAULT_MAXNLPC = 0;
	static int DEFAULT_NCHAN = 1;

	static int CHANSIZE = 0;
	static int ENERGYSIZE = 3;
	static int BITSHIFTSIZE = 2;
	static int NWRAP = 3;

	static int FNSIZE = 2;
	static final int FN_DIFF0 = 0;
	static final int FN_DIFF1 = 1;
	static final int FN_DIFF2 = 2;
	static final int FN_DIFF3 = 3;
	static final int FN_QUIT = 4;
	static final int FN_BLOCKSIZE = 5;
	static final int FN_BITSHIFT = 6;
	static final int FN_QLPC = 7;
	static final int FN_ZERO = 8;
	static final int FN_VERBATIM = 9;

	static int VERBATIM_CKSIZE_SIZE = 5;	/* a var_put code size */
	static int VERBATIM_BYTE_SIZE = 8;		/* code size 8 on single bytes means
											* no compression at all */
	static int VERBATIM_CHUNK_MAX = 256;	/* max. size of a FN_VERBATIM chunk */

	static int ULONGSIZE = 2;
	static int NSKIPSIZE = 1;
	static int LPCQSIZE = 2;
	static int LPCQUANT = 5;
	static int XBYTESIZE = 7;

	static int TYPESIZE = 		4;
	public static final int TYPE_AU1 = 	0;			/* original lossless ulaw  (8-bit)           */
	public static final int TYPE_S8 = 		1;		/* signed 8 bit characters                   */
	public static final int TYPE_U8 = 		2;		/* unsigned 8 bit characters                 */
	public static final int TYPE_S16HL = 	3;		/* signed 16 bit shorts: high-low            */
	public static final int TYPE_U16HL = 	4;		/* unsigned 16 bit shorts: high-low          */
	public static final int TYPE_S16LH = 	5;		/* signed 16 bit shorts: low-high            */
	public static final int TYPE_U16LH = 	6;		/* unsigned 16 bit shorts: low-high          */
	public static final int TYPE_ULAW = 	7;		/* lossy ulaw: internal conversion to linear */
	public static final int TYPE_AU2 = 	8;			/* new ulaw with zero mapping  (8-bit)       */
	public static final int TYPE_AU3 = 	9;			/* lossless alaw (8-bit)                     */
	public static final int TYPE_ALAW = 	10;		/* lossy alaw: internal conversion to linear */
	public static int TYPE_RIFF_WAVE = 11;			/* Microsoft .WAV files                      */
	public static int TYPE_AIFF = 		12;			/* Apple .AIFF files                         */
	public static int TYPE_EOF = 		13;
	public static int TYPE_GENERIC_ULAW = 128;
	public static int TYPE_GENERIC_ALAW = 129;

	static int POSITIVE_ULAW_ZERO = 0xff;
	static int NEGATIVE_ULAW_ZERO = 0x7f;
	
	static int BUFSIZ = 512;
	
	static double M_LN2 = 0.69314718055994530942;
	
	static int V2LPCQOFFSET = (1 << LPCQUANT);

}