/*
** ShortenUtils.java
**
** Copyright (c) 2011 Peter McQuillan
**
** All Rights Reserved.
**                       
** Distributed under the BSD Software License (see license.txt)  
**
*/
package com.beatofthedrum.shortendecoder;

public class ShortenUtils
{

	static int[] getbufp = new int[Defines.BUFSIZ];
	static int getbufpIdx = 0;
	static int    nbyteget;
	static long  gbuffer;
	static int    nbitget;
	static int[] sizeof_sample = {1,1,1,2,2,2,2,1,1,1,1};
	static int  nwritebuf;
	static int blocksize = Defines.DEFAULT_BLOCK_SIZE;
	static int nskip = Defines.DEFAULT_NSKIP;
	static int maxnlpc = Defines.DEFAULT_MAXNLPC;
	static int internal_ftype;
	static long[] masktab = new long[Defines.MASKTABSIZE];
	static int nwrap;
	static int chan;
	
	static int[] bufferIdx = new int[2];
	static long currentBuffered;
	static int cmd;
	static long[][] buffer;
	static long[][] offset;
	static int bitshift;
	static int nmean;
	static int[]  qlpc;
	static long  lpcqoffset;
	
	static long[][] oldvalues = new long[2][64];
	
	//size of ulaw_outward array is 13, 256
	static int[][] ulaw_outward = {
{127,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,255,254,253,252,251,250,249,248,247,246,245,244,243,242,241,240,239,238,237,236,235,234,233,232,231,230,229,228,227,226,225,224,223,222,221,220,219,218,217,216,215,214,213,212,211,210,209,208,207,206,205,204,203,202,201,200,199,198,197,196,195,194,193,192,191,190,189,188,187,186,185,184,183,182,181,180,179,178,177,176,175,174,173,172,171,170,169,168,167,166,165,164,163,162,161,160,159,158,157,156,155,154,153,152,151,150,149,148,147,146,145,144,143,142,141,140,139,138,137,136,135,134,133,132,131,130,129,128},
{112,114,116,118,120,122,124,126,127,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,113,115,117,119,121,123,125,255,253,251,249,247,245,243,241,239,238,237,236,235,234,233,232,231,230,229,228,227,226,225,224,223,222,221,220,219,218,217,216,215,214,213,212,211,210,209,208,207,206,205,204,203,202,201,200,199,198,197,196,195,194,193,192,191,190,189,188,187,186,185,184,183,182,181,180,179,178,177,176,175,174,173,172,171,170,169,168,167,166,165,164,163,162,161,160,159,158,157,156,155,154,153,152,151,150,149,148,147,146,145,144,143,142,141,140,139,138,137,136,135,134,133,132,131,130,129,128,254,252,250,248,246,244,242,240},
{96,98,100,102,104,106,108,110,112,113,114,116,117,118,120,121,122,124,125,126,127,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,97,99,101,103,105,107,109,111,115,119,123,255,251,247,243,239,237,235,233,231,229,227,225,223,222,221,220,219,218,217,216,215,214,213,212,211,210,209,208,207,206,205,204,203,202,201,200,199,198,197,196,195,194,193,192,191,190,189,188,187,186,185,184,183,182,181,180,179,178,177,176,175,174,173,172,171,170,169,168,167,166,165,164,163,162,161,160,159,158,157,156,155,154,153,152,151,150,149,148,147,146,145,144,143,142,141,140,139,138,137,136,135,134,133,132,131,130,129,128,254,253,252,250,249,248,246,245,244,242,241,240,238,236,234,232,230,228,226,224},
{80,82,84,86,88,90,92,94,96,97,98,100,101,102,104,105,106,108,109,110,112,113,114,115,116,117,118,120,121,122,123,124,125,126,127,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,81,83,85,87,89,91,93,95,99,103,107,111,119,255,247,239,235,231,227,223,221,219,217,215,213,211,209,207,206,205,204,203,202,201,200,199,198,197,196,195,194,193,192,191,190,189,188,187,186,185,184,183,182,181,180,179,178,177,176,175,174,173,172,171,170,169,168,167,166,165,164,163,162,161,160,159,158,157,156,155,154,153,152,151,150,149,148,147,146,145,144,143,142,141,140,139,138,137,136,135,134,133,132,131,130,129,128,254,253,252,251,250,249,248,246,245,244,243,242,241,240,238,237,236,234,233,232,230,229,228,226,225,224,222,220,218,216,214,212,210,208},
{64,66,68,70,72,74,76,78,80,81,82,84,85,86,88,89,90,92,93,94,96,97,98,99,100,101,102,104,105,106,107,108,109,110,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,65,67,69,71,73,75,77,79,83,87,91,95,103,111,255,239,231,223,219,215,211,207,205,203,201,199,197,195,193,191,190,189,188,187,186,185,184,183,182,181,180,179,178,177,176,175,174,173,172,171,170,169,168,167,166,165,164,163,162,161,160,159,158,157,156,155,154,153,152,151,150,149,148,147,146,145,144,143,142,141,140,139,138,137,136,135,134,133,132,131,130,129,128,254,253,252,251,250,249,248,247,246,245,244,243,242,241,240,238,237,236,235,234,233,232,230,229,228,227,226,225,224,222,221,220,218,217,216,214,213,212,210,209,208,206,204,202,200,198,196,194,192},
{49,51,53,55,57,59,61,63,64,66,67,68,70,71,72,74,75,76,78,79,80,81,82,84,85,86,87,88,89,90,92,93,94,95,96,97,98,99,100,101,102,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,50,52,54,56,58,60,62,65,69,73,77,83,91,103,255,231,219,211,205,201,197,193,190,188,186,184,182,180,178,176,175,174,173,172,171,170,169,168,167,166,165,164,163,162,161,160,159,158,157,156,155,154,153,152,151,150,149,148,147,146,145,144,143,142,141,140,139,138,137,136,135,134,133,132,131,130,129,128,254,253,252,251,250,249,248,247,246,245,244,243,242,241,240,239,238,237,236,235,234,233,232,230,229,228,227,226,225,224,223,222,221,220,218,217,216,215,214,213,212,210,209,208,207,206,204,203,202,200,199,198,196,195,194,192,191,189,187,185,183,181,179,177},
{32,34,36,38,40,42,44,46,48,49,51,52,53,55,56,57,59,60,61,63,64,65,66,67,68,70,71,72,73,74,75,76,78,79,80,81,82,83,84,85,86,87,88,89,90,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,33,35,37,39,41,43,45,47,50,54,58,62,69,77,91,255,219,205,197,190,186,182,178,175,173,171,169,167,165,163,161,159,158,157,156,155,154,153,152,151,150,149,148,147,146,145,144,143,142,141,140,139,138,137,136,135,134,133,132,131,130,129,128,254,253,252,251,250,249,248,247,246,245,244,243,242,241,240,239,238,237,236,235,234,233,232,231,230,229,228,227,226,225,224,223,222,221,220,218,217,216,215,214,213,212,211,210,209,208,207,206,204,203,202,201,200,199,198,196,195,194,193,192,191,189,188,187,185,184,183,181,180,179,177,176,174,172,170,168,166,164,162,160},
{16,18,20,22,24,26,28,30,32,33,34,36,37,38,40,41,42,44,45,46,48,49,50,51,52,53,55,56,57,58,59,60,61,63,64,65,66,67,68,69,70,71,72,73,74,75,76,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,17,19,21,23,25,27,29,31,35,39,43,47,54,62,77,255,205,190,182,175,171,167,163,159,157,155,153,151,149,147,145,143,142,141,140,139,138,137,136,135,134,133,132,131,130,129,128,254,253,252,251,250,249,248,247,246,245,244,243,242,241,240,239,238,237,236,235,234,233,232,231,230,229,228,227,226,225,224,223,222,221,220,219,218,217,216,215,214,213,212,211,210,209,208,207,206,204,203,202,201,200,199,198,197,196,195,194,193,192,191,189,188,187,186,185,184,183,181,180,179,178,177,176,174,173,172,170,169,168,166,165,164,162,161,160,158,156,154,152,150,148,146,144},
{2,4,6,8,10,12,14,16,17,18,20,21,22,24,25,26,28,29,30,32,33,34,35,36,37,38,40,41,42,43,44,45,46,48,49,50,51,52,53,54,55,56,57,58,59,60,61,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,0,1,3,5,7,9,11,13,15,19,23,27,31,39,47,62,255,190,175,167,159,155,151,147,143,141,139,137,135,133,131,129,254,253,252,251,250,249,248,247,246,245,244,243,242,241,240,239,238,237,236,235,234,233,232,231,230,229,228,227,226,225,224,223,222,221,220,219,218,217,216,215,214,213,212,211,210,209,208,207,206,205,204,203,202,201,200,199,198,197,196,195,194,193,192,191,189,188,187,186,185,184,183,182,181,180,179,178,177,176,174,173,172,171,170,169,168,166,165,164,163,162,161,160,158,157,156,154,153,152,150,149,148,146,145,144,142,140,138,136,134,132,130,128},
{1,2,4,5,6,8,9,10,12,13,14,16,17,18,19,20,21,22,24,25,26,27,28,29,30,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,0,3,7,11,15,23,31,47,255,175,159,151,143,139,135,131,254,253,252,251,250,249,248,247,246,245,244,243,242,241,240,239,238,237,236,235,234,233,232,231,230,229,228,227,226,225,224,223,222,221,220,219,218,217,216,215,214,213,212,211,210,209,208,207,206,205,204,203,202,201,200,199,198,197,196,195,194,193,192,191,190,189,188,187,186,185,184,183,182,181,180,179,178,177,176,174,173,172,171,170,169,168,167,166,165,164,163,162,161,160,158,157,156,155,154,153,152,150,149,148,147,146,145,144,142,141,140,138,137,136,134,133,132,130,129,128},
{1,2,3,4,5,6,8,9,10,11,12,13,14,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,0,7,15,31,255,159,143,135,254,253,252,251,250,249,248,247,246,245,244,243,242,241,240,239,238,237,236,235,234,233,232,231,230,229,228,227,226,225,224,223,222,221,220,219,218,217,216,215,214,213,212,211,210,209,208,207,206,205,204,203,202,201,200,199,198,197,196,195,194,193,192,191,190,189,188,187,186,185,184,183,182,181,180,179,178,177,176,175,174,173,172,171,170,169,168,167,166,165,164,163,162,161,160,158,157,156,155,154,153,152,151,150,149,148,147,146,145,144,142,141,140,139,138,137,136,134,133,132,131,130,129,128},
{1,2,3,4,5,6,7,8,9,10,11,12,13,14,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,0,15,255,143,254,253,252,251,250,249,248,247,246,245,244,243,242,241,240,239,238,237,236,235,234,233,232,231,230,229,228,227,226,225,224,223,222,221,220,219,218,217,216,215,214,213,212,211,210,209,208,207,206,205,204,203,202,201,200,199,198,197,196,195,194,193,192,191,190,189,188,187,186,185,184,183,182,181,180,179,178,177,176,175,174,173,172,171,170,169,168,167,166,165,164,163,162,161,160,159,158,157,156,155,154,153,152,151,150,149,148,147,146,145,144,142,141,140,139,138,137,136,135,134,133,132,131,130,129,128},
{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,0,255,254,253,252,251,250,249,248,247,246,245,244,243,242,241,240,239,238,237,236,235,234,233,232,231,230,229,228,227,226,225,224,223,222,221,220,219,218,217,216,215,214,213,212,211,210,209,208,207,206,205,204,203,202,201,200,199,198,197,196,195,194,193,192,191,190,189,188,187,186,185,184,183,182,181,180,179,178,177,176,175,174,173,172,171,170,169,168,167,166,165,164,163,162,161,160,159,158,157,156,155,154,153,152,151,150,149,148,147,146,145,144,143,142,141,140,139,138,137,136,135,134,133,132,131,130,129,128}
};

	
    public static ShortenContext ShortenOpenFileInput(java.io.DataInputStream input_stream)
    {
		ShortenContext sc = new ShortenContext();
		
		sc.error = false;
		
		sc.original_ftype = 0;	// WAVE
		sc.firstBufferDecoded = false;	// have not decoded first buffer yet
		
		sc.shn_stream.stream = input_stream;
		sc.shn_stream.currentPos = 0;
		
		/*
		** Start initial decode of Shorten file, get information about file - sample rate etc. and
		** leave file in state ready to be decoded
		*/
		
		DoInitialDecode(sc);
		
		// There are certain file types not yet supported by this code
		
		if(sc.ftype == Defines.TYPE_ALAW || sc.ftype == Defines.TYPE_ULAW)
		{
			sc.error = true;
			sc.error_message = "This file uses an unsupported encoding type";
		}
		
		return (sc);
			
	}
	
	public static void ShortenCloseFile(ShortenContext sc)
	{
		if(null != sc.shn_stream.stream)
		{
			try
			{
				sc.shn_stream.stream.close();
			}
			catch(java.io.IOException ioe)
			{
			}
		}
	}
	
	public static boolean DecodeHeader(int[] tempHeader, ShortenContext sc)
	{
		// The Shorten file could be an encoded WAV file or AIFF file. First thing we do
		// is the determine the file type
		
		int filetype = 0;
		int encodingtype = 0;
		
		int internal_nNumChannels = 0;
		int internal_nSampleRate = 0;
		int internal_nBitsPerSample = 0;
		int arrayIdx = 0;
		
		if(tempHeader.length < 12)
		{
			return(false);
		}
		
		// RIFF
		if(tempHeader[0]==82 && tempHeader[1]==73 && tempHeader[2]==70 && tempHeader[3]==70)
		{
			filetype = Defines.TYPE_RIFF_WAVE;
			sc.original_ftype = 0;	// WAVE
		}
		else if(tempHeader[0]==70 && tempHeader[1]==79 && tempHeader[2]==82 && tempHeader[3]==77)
		{	// AIFF files start with magic word 'FORM'
			filetype = Defines.TYPE_AIFF;
			sc.original_ftype = 1;	// AIFF
		}
		
		if(filetype == Defines.TYPE_RIFF_WAVE)
		{
			int datasize = 0;
			
			arrayIdx = 8;
			// "WAVE" ascii values
			if(tempHeader[arrayIdx]!=87 || tempHeader[arrayIdx+1]!=65 || tempHeader[arrayIdx+2]!=86 || tempHeader[arrayIdx+3]!=69)
			{
				return(false);
			}

			arrayIdx += 4;

			//
			// We need to find the fmt chunk, which contains the metadata
			//

			boolean fmtFound = false;
			boolean dataFound = false;
			long nChunkSize;

			while(!dataFound)
			{
				// new chunk; read tag and chunk size
				if(tempHeader.length < arrayIdx + 8)
				{
					return(false);
				}
				nChunkSize = (tempHeader[arrayIdx+7] << 24) + (tempHeader[arrayIdx+6] << 16) + (tempHeader[arrayIdx+5] << 8) + tempHeader[arrayIdx+4];
				
				// examine tag
		
				// "fmt " ascii values		
				if(tempHeader[arrayIdx]==102 && tempHeader[arrayIdx+1]==109 && tempHeader[arrayIdx+2]==116 && tempHeader[arrayIdx+3]==32)
				{
					fmtFound = true;
				
					// now we either have WAVEFORMAT or WAVEFORMATEX
					// based on the nChunkSize

					// The encoding type in a WAV file is as follows:
					// 1 - PCM
					// 3 - IEEE Float
					// 6 - ALAW
					// 7 - ULAW

					arrayIdx += 8;
			
					if (14 == nChunkSize)
					{
						// WAVEFORMAT
						encodingtype = (tempHeader[arrayIdx+1] << 8) + tempHeader[arrayIdx];		

						if(encodingtype!= 1 && encodingtype != 6 && encodingtype != 7)
						{
							// Not a valid encoding type, return false
							return(false);
						}
						
						internal_nNumChannels = (tempHeader[arrayIdx+3] << 8) + tempHeader[arrayIdx+2];
						internal_nSampleRate = (tempHeader[arrayIdx+7] << 24) + (tempHeader[arrayIdx+6] << 16) + (tempHeader[arrayIdx+5] << 8) + tempHeader[arrayIdx+4];
						internal_nBitsPerSample = (tempHeader[arrayIdx+13] << 8) + tempHeader[arrayIdx+12];			//nBlockAlign
						internal_nBitsPerSample *= 8;
						internal_nBitsPerSample /= internal_nNumChannels;
						
						sc.sample_rate = internal_nSampleRate;
						sc.sample_size = internal_nBitsPerSample;						
		
					}
					else
					{
						// WAVEFORMATEX
						encodingtype = (tempHeader[arrayIdx+1] << 8) + tempHeader[arrayIdx];
						
						if(encodingtype!= 1 && encodingtype != 6 && encodingtype != 7)
						{
							// Not a valid encoding type, return false
							return(false);
						}

						internal_nNumChannels = (tempHeader[arrayIdx+3] << 8) + tempHeader[arrayIdx+2];
						internal_nSampleRate = (tempHeader[arrayIdx+7] << 24) + (tempHeader[arrayIdx+6] << 16) + (tempHeader[arrayIdx+5] << 8) + tempHeader[arrayIdx+4];
						internal_nBitsPerSample = (tempHeader[arrayIdx+15] << 8) + tempHeader[arrayIdx+14];
						
						sc.sample_rate = internal_nSampleRate;
						sc.sample_size = internal_nBitsPerSample;
								
					}

					arrayIdx += nChunkSize;
				}
				else if(tempHeader[arrayIdx]==100 && tempHeader[arrayIdx+1]==97 && tempHeader[arrayIdx+2]==116 && tempHeader[arrayIdx+3]==97)
				{
					// "data" ascii values
					// If we find the data header before we find "fmt " - thats bad
					
					arrayIdx += 4;
					
					sc.datasize = (tempHeader[arrayIdx+3] << 24) + (tempHeader[arrayIdx+2] << 16) + (tempHeader[arrayIdx+1] << 8) + tempHeader[arrayIdx];
											
					dataFound = true;
				}
				else
				{
					// some chunk other than fmt, skip past it
					arrayIdx += (8 + nChunkSize);
				}
			}
			return(true);
		}	
		
		if(filetype == Defines.TYPE_AIFF)
		{
			arrayIdx = 8;
			
			// "AIFF" ascii values
			if(tempHeader[arrayIdx]!=65 || tempHeader[arrayIdx+1]!=73 || tempHeader[arrayIdx+2]!=70 || tempHeader[arrayIdx+3]!=70)
			{
				return(false);
			}

			arrayIdx += 4;
			
			//
			// We need to find the common chunk (COMM), which contains the metadata
			//

			boolean commFound = false;
			boolean dataFound = false;
			long nChunkSize;

			while(!dataFound)
			{
				// new chunk; read tag and chunk size
				if(tempHeader.length < arrayIdx + 8)
				{
					return(false);
				}
				
				nChunkSize = (tempHeader[arrayIdx+4] << 24) + (tempHeader[arrayIdx+5] << 16) + (tempHeader[arrayIdx+6] << 8) + tempHeader[arrayIdx+7];
				
				// examine tag
				
				// "COMM" ascii values		
				if(tempHeader[arrayIdx]==67 && tempHeader[arrayIdx+1]==79 && tempHeader[arrayIdx+2]==77 && tempHeader[arrayIdx+3]==77)
				{
					arrayIdx += 8;
					
					internal_nNumChannels = (tempHeader[arrayIdx] << 8) + tempHeader[arrayIdx+1];
					internal_nBitsPerSample = (tempHeader[arrayIdx+6] << 8) + tempHeader[arrayIdx+7];
					int[] floatHolder = new int[10];
					for(int k=0; k <10; k++)
					{
						floatHolder[k] = tempHeader[arrayIdx + 8 + k];
					}
					
					internal_nSampleRate = (int)readIEEEStandard754(floatHolder);
					
					sc.sample_rate = internal_nSampleRate;
					sc.sample_size = internal_nBitsPerSample;

					arrayIdx += nChunkSize;
		
				}
				else if(tempHeader[arrayIdx]==83 && tempHeader[arrayIdx+1]==83 && tempHeader[arrayIdx+2]==78 && tempHeader[arrayIdx+3]==68)
				{
					// "SSND" ascii values
					// If we find the data header before we find "COMM" - thats bad

					arrayIdx += 4;
					
					// The next 8 bytes after the chunk size are not actually sound data, however I have code that handles this situation
					
					sc.datasize = (tempHeader[arrayIdx] << 24) + (tempHeader[arrayIdx+1] << 16) + (tempHeader[arrayIdx+2] << 8) + tempHeader[arrayIdx + 3] - 8;
				
					dataFound = true;
				}
				else
				{
					// some chunk other than COMM or SSND, skip past it
					arrayIdx += (8 + nChunkSize);
				}
			
			}
			return(true);
			
		}
		return(false);
	}
	

    private static double readIEEEStandard754(int[] tempData) 
	{
		double result = 0;
        int expon = 0;
        long hiMantissa = 0;
		long loMantissa = 0;
        long t1, t2;
		double huge = 3.40282346638528860e+38;

		int s;

		expon = tempData[1] + (tempData[0] << 8);
		hiMantissa = tempData[5] + (tempData[4] << 8) + (tempData[3] << 16) + (tempData[2] << 24);
		
		if (hiMantissa < 0)  // 2's complement
			hiMantissa += 4294967296L;

		loMantissa = tempData[6] + (tempData[7] << 8) + (tempData[8] << 16) + (tempData[9] << 24);

        if (loMantissa < 0)  // 2's complement
           loMantissa += 4294967296L;

		if (expon == 0 && hiMantissa == 0 && loMantissa == 0) 
		{
			result = 0;
		} 
		else 
		{
			if (expon == 0x7FFF)
				result = huge;
			else 
			{	
				expon -= 16383;
				expon -= 31;
				result = (hiMantissa * Math.pow(2, expon));		
				expon -= 32;
				result += (loMantissa * Math.pow(2, expon));			
			}
		}
        return result;
    }


	public static boolean DoInitialDecode(ShortenContext sc)
	{
		int[] magic = Defines.MAGIC;
		int i = 0;
//		qlpc = NULL;
		int nscan = 0;
		nmean = Defines.UNDEFINED_UINT;
		int bitshift = 0;
		int nchan = Defines.DEFAULT_NCHAN;	// default num channels is 1
		int ftype = Defines.TYPE_S16LH;	// what we want samples to be exported as
		int version = Defines.MAX_VERSION + 1;

		
		while(version > Defines.MAX_VERSION) 
		{
			int singlec;
			int[] tempRB = new int[4];
			
			StreamUtils.stream_read(sc.shn_stream, 1, tempRB, 0);

			singlec = tempRB[0];
			if(0 != magic[nscan] && singlec == magic[nscan])
			{
				nscan++;
			}
			else if(0 == magic[nscan] && singlec <= Defines.MAX_VERSION) 
			{
				version = singlec;
			}
			else 
			{
				if(singlec == magic[0]) 
				{
					nscan = 1;
				}
				else 
				{
					nscan = 0;
				}
				version = Defines.MAX_VERSION + 1;
			}
		}
		


		// check version number 
		if(version > Defines.MAX_SUPPORTED_VERSION)
		{
			sc.error = true;
			sc.error_message = "Version read from Shorten file is greater than max supported version";
			return false;
		}
		
		sc.version = version;

		// set up the default nmean
		nmean = (version < 2) ? Defines.DEFAULT_V0NMEAN : Defines.DEFAULT_V2NMEAN;

		// initialise the variable length file read for the compressed stream
		var_get_init();

		// get the internal file type 
		internal_ftype = (int)uint_get(Defines.TYPESIZE, sc);
		
		nchan = (int)uint_get(Defines.CHANSIZE, sc);
		
		sc.num_channels = nchan;
		sc.ftype = internal_ftype;
	
		
		// get blocksize if version > 0 
		if(version > 0) 
		{
			blocksize = (int)uint_get((int) (Math.log((double) Defines.DEFAULT_BLOCK_SIZE) / Defines.M_LN2),sc);
				   
			maxnlpc = (int)uint_get(Defines.LPCQSIZE, sc);
			nmean = (int)uint_get(0, sc);
			nskip = (int)uint_get(Defines.NSKIPSIZE, sc);
			long singleVal = 0;
			for(i = 0; i < nskip; i++) 
			{
				singleVal = uvar_get(Defines.XBYTESIZE, sc);
			}
		}
		else
		{
			blocksize = Defines.DEFAULT_BLOCK_SIZE;
		}
				

		nwrap = Math.max(Defines.NWRAP, maxnlpc);
		
		buffer = new long[nchan][blocksize + nwrap];
		offset = new long[nchan][Math.max(1,nmean)];
		
		
		for(chan = 0; chan < nchan; chan++) 
		{
			for(i = 0; i < nwrap; i++) 
			{
				buffer[chan][i] = 0;
			}
			bufferIdx[chan] += nwrap;
		}

		if(maxnlpc > 0)
		{
			qlpc = new int[maxnlpc * 4];
		}
		
		if(version > 1)
			lpcqoffset = Defines.V2LPCQOFFSET;  

		init_offset(offset, nchan, Math.max(1, nmean), internal_ftype);

		currentBuffered = 0;	// initialising main buffer counter
		chan = 0;		// initialise
		sc.quitActivated = false;	// initialise this

		//
		// At this stage we have initialised everything. Hopefully
		// Now we start reading from the file. The first section should
		// be a VERBATIM section. This contains a copy of the WAV header
		// We don't need this for playback (so we're not going to add
		// if to our decoded buffer) - but it does contain useful information
		// like bits per sample and sample rate.
		//

		cmd = (int)uvar_get(Defines.FNSIZE, sc);

		if(cmd!= Defines.FN_VERBATIM)
		{
			sc.error = true;
			sc.error_message = "Verbatim section not encountered as expected";
			return(false);
		}
		
		else
		{
			int cklen = (int)uvar_get(Defines.VERBATIM_CKSIZE_SIZE, sc);
			int[] tempHeader = new int[cklen];
			int counter=0;
			while (cklen > 0)
			{
				tempHeader[counter]= (int)uvar_get(Defines.VERBATIM_BYTE_SIZE, sc);
				counter++;
				cklen--;
			}
			
			DecodeHeader(tempHeader, sc);
		
		}

		return true;
	
	}
	
	public static long DecodeBuffer(ShortenContext sc)
	{
		int bytesDecoded = 0;
		int i;
		long cbufferminus1 = 0;
		long cbufferminus2 = 0;
		long cbufferminus3 = 0;
		
		sc.bufferPos = 0;	// reset our decoded buffer index position
				
		/* get commands from file and execute them */
		while(bytesDecoded < Defines.BYTES_TO_DECODE)
		{
			cmd = (int)uvar_get(Defines.FNSIZE, sc);
		
			if(cmd==Defines.FN_QUIT)
			{
				sc.quitActivated = true;
				return bytesDecoded;
			}
			else
			{
	
				switch(cmd) 
				{
					case Defines.FN_ZERO:
					case Defines.FN_DIFF0:
					case Defines.FN_DIFF1:
					case Defines.FN_DIFF2:
					case Defines.FN_DIFF3:
					case Defines.FN_QLPC: 
					{
						long coffset;
						long[] cbuffer = buffer[chan];
						int resn = 0, nlpc, j;
    
						if(cmd != Defines.FN_ZERO) 
						{
							resn = (int)uvar_get(Defines.ENERGYSIZE, sc);
							/* this is a hack as version 0 differed in definition of var_get */
							if(sc.version == 0) resn--;
						}
    
						/* find mean offset : N.B. this code duplicated */
						if(nmean == 0) 
						{
							coffset = offset[chan][0];					
						}
						else 
						{
							long sum = (sc.version < 2) ? 0 : nmean / 2;
							for(i = 0; i < nmean; i++) sum += offset[chan][i];						
							if(sc.version < 2)
								coffset = sum / nmean;
							else
								coffset = rounded_shift_down(sum / nmean, bitshift);
						}

						switch(cmd) 
						{
							case Defines.FN_ZERO:
								for(i = 0; i < blocksize; i++)
									cbuffer[i] = 0;
								break;
							case Defines.FN_DIFF0:
								for(i = 0; i < blocksize; i++)
									cbuffer[i] = (int)(var_get(resn, sc) + coffset);
								break;
							case Defines.FN_DIFF1:
								for(i = 0; i < blocksize; i++)
								{
									if(i>0)
									{
										cbufferminus1 = cbuffer[i - 1];
									}
									else
									{
										cbufferminus1 = oldvalues[chan][0];
									}
									cbuffer[i] = (int)var_get(resn, sc) + cbufferminus1;
								}
								break;
							case Defines.FN_DIFF2:
								for(i = 0; i < blocksize; i++)
								{
									if(i==0)
									{
										cbufferminus1 = oldvalues[chan][0];
										cbufferminus2 = oldvalues[chan][1];
									}
									else if(i==1)
									{
										cbufferminus1 = cbuffer[0];
										cbufferminus2 = oldvalues[chan][0];
									}
									else
									{
										cbufferminus1 = cbuffer[i - 1];
										cbufferminus2 = cbuffer[i - 2];	
									}
									cbuffer[i] = (int)(var_get(resn, sc) + (2 * cbufferminus1 - cbufferminus2));
								}
								break;
							case Defines.FN_DIFF3:
								long pulledVal;
								for(i = 0; i < blocksize; i++)
								{
									if(i==0)
									{
										cbufferminus1 = oldvalues[chan][0];
										cbufferminus2 = oldvalues[chan][1];
										cbufferminus3 = oldvalues[chan][2];
									}
									else if(i==1)
									{
										cbufferminus1 = cbuffer[0];
										cbufferminus2 = oldvalues[chan][0];
										cbufferminus3 = oldvalues[chan][1];
									}
									else if(i==2)
									{
										cbufferminus1 = cbuffer[1];
										cbufferminus2 = cbuffer[0];
										cbufferminus3 = oldvalues[chan][0];
									}
									else
									{
										cbufferminus1 = cbuffer[i - 1];
										cbufferminus2 = cbuffer[i - 2];
										cbufferminus3 = cbuffer[i - 3];	
									}			
									pulledVal = var_get(resn, sc);
									cbuffer[i] = (int)(pulledVal + 3 * (cbufferminus1 - cbufferminus2) + cbufferminus3);							
								}
								break;
							case Defines.FN_QLPC:
								nlpc = (int)uvar_get(Defines.LPCQSIZE, sc);

								for(i = 0; i < nlpc; i++)
									qlpc[i] = (int)var_get(Defines.LPCQUANT, sc);
								
								// We create a new array with a copy of the values from cbuffer, this is to handle case where negative array indices are used
								long[] tempBuffer = new long[blocksize + nlpc + 1];
								for(i = 0; i <= nlpc; i++)
								{
									tempBuffer[i] = 0;
								}
								// Now we need to insert the 'wrap' values from the previous run
								
								int internal_counter = 0;
								for(i=nlpc; i > 0; i--)
								{
									tempBuffer[i] = oldvalues[chan][internal_counter];
									internal_counter++;
								}
								
								for(i = 0; i < blocksize; i++)
								{
									tempBuffer[i + nlpc + 1] = cbuffer[i];
								}
						
								// Now we continue with new temp array
								for(i = 0; i <= nlpc; i++)	// we use <= to compensate for extra array position added to cope with minus indices
								{
									tempBuffer[i] -= coffset;
								}							
									
								for(i = 0; i < blocksize; i++) 
								{
									long sum = lpcqoffset;
	    
									for(j = 0; j < nlpc; j++)
									{
										sum += qlpc[j] * tempBuffer[i - j + nlpc];	// used to have -1								
									}
								
									tempBuffer[i + nlpc + 1] = (int)(var_get(resn, sc) + (sum >> Defines.LPCQUANT));
								}
								
								if(coffset != 0)
								{
									for(i = 0; i < blocksize; i++)
										tempBuffer[i + nlpc + 1] += coffset;
								}
								
								// Now put back the data
								for(i = 0; i < blocksize; i++)
								{
									cbuffer[i] = tempBuffer[i + nlpc + 1];
								}
									
								break;
						}
	
						// store mean value if appropriate : N.B. Duplicated code
						if(nmean > 0) 
						{
						
							long sum = (sc.version < 2) ? 0 : blocksize / 2;
	  
							for(i = 0; i < blocksize; i++)
								sum += cbuffer[i];
	  
							for(i = 1; i < nmean; i++)
								offset[chan][i - 1] = offset[chan][i];
							if(sc.version < 2)
								offset[chan][nmean - 1] = (int)(sum / blocksize);
							else
								offset[chan][nmean - 1] = (int)((sum / blocksize) << bitshift);
						}
    
						// do the wrap - this is where we keep track of the last values so we can use them
						// at the start of the next run
						
						// first ensure array values are emptied
						for(i = 0; i < 64; i++)
						{
							oldvalues[chan][i] = 0;
						}
						
						int arrayterminator = 64;
						
						if(arrayterminator > blocksize)
						{
							arrayterminator = blocksize;
						}
						
						for(i=0; i < arrayterminator; i ++)
						{
							oldvalues[chan][i] = cbuffer[blocksize - (i + 1)];
						}						
    
						fix_bitshift(cbuffer, blocksize, bitshift, internal_ftype);
						if(chan == sc.num_channels - 1)
						{
							long retVal = 0;
							retVal = fwrite_type(buffer, blocksize, sc);
							bytesDecoded += retVal;
						}
						chan = (chan + 1) % sc.num_channels;
	
					}
					break;
				case Defines.FN_BLOCKSIZE:
					blocksize = (int)uint_get((int) (Math.log((double) blocksize) / Defines.M_LN2), sc);
					break;
				case Defines.FN_BITSHIFT:
					bitshift = (int)uvar_get(Defines.BITSHIFTSIZE, sc);
					break;
				case Defines.FN_VERBATIM: 
				{
					// This indicates the end of our music data - more verbatim data found
	
					sc.quitActivated = true;
				}
					break;
				default:
				{
					sc.quitActivated = true;
					System.err.println("sanity check fails trying to decode function: " + cmd);
				}
			}
		}	// end of else i.e. cmd was not FN_QUIT
	}  // end of main while loop
	return bytesDecoded;
}
	
	private static long rounded_shift_down(long x, int n)
	{
		if(n==0)
			return(x);
		else
		{
			return( (x >> (n-1)) >> 1);
		}
	}
		
	
	private static void init_offset(long[][] offset, int nchan, int nblock, int ftype) 
	{
		long mean = 0;
		int  chan, i;

		/* initialise offset */
		switch(ftype) 
		{
			case Defines.TYPE_AU1:
			case Defines.TYPE_S8:
			case Defines.TYPE_S16HL:
			case Defines.TYPE_S16LH:
			case Defines.TYPE_ULAW:
			case Defines.TYPE_AU2:
			case Defines.TYPE_AU3:
			case Defines.TYPE_ALAW:
				mean = 0;
				break;
			case Defines.TYPE_U8:
				mean = 0x80;
				break;
			case Defines.TYPE_U16HL:
			case Defines.TYPE_U16LH:
				mean = 0x8000;
				break;
			default:
				System.err.println("init_offset default activated");
		}

		for(chan = 0; chan < nchan; chan++)
			for(i = 0; i < nblock; i++)
				offset[chan][i] = (int)mean;
	}
	
	private static void mkmasktab() 
	{
		int i;
		long val = 0;

		masktab[0] = val;
		for(i = 1; i < Defines.MASKTABSIZE; i++) 
		{
			val <<= 1;
			val |= 1;
			masktab[i] = val;
		}
	}
	
	static void fix_bitshift(long[] buffer, int nitem, int bitshift, int ftype)
	{  
		int i;

		if(ftype == Defines.TYPE_AU1)
			for(i = 0; i < nitem; i++)
				buffer[i] = ulaw_outward[bitshift][(int)(buffer[i] + 128)];
		else if(ftype == Defines.TYPE_AU2)
			for(i = 0; i < nitem; i++) 
			{
				if(buffer[i] >= 0)
					buffer[i] = ulaw_outward[bitshift][(int)(buffer[i] + 128)];
				else if(buffer[i] == -1)
					buffer[i] =  Defines.NEGATIVE_ULAW_ZERO;
				else
					buffer[i] = ulaw_outward[bitshift][(int)(buffer[i] + 129)];
			}
		else
			if(bitshift != 0)
				for(i = 0; i < nitem; i++)
					buffer[i] <<= bitshift;
	}

	private static void var_get_init() 
	{
		mkmasktab();
		getbufpIdx = 0;
		nbyteget = 0;
		gbuffer  = 0;
		nbitget  = 0;
	}
	
	private static long uint_get(int nbit, ShortenContext sc)
	{
		if(sc.version == 0)
		{
			return(uvar_get(nbit, sc));
		}
		else
		{
			return(ulong_get(sc));
		}
	}
	
	private static long ulong_get( ShortenContext sc) 
	{
		long nbit = uvar_get(Defines.ULONGSIZE, sc);
		return(uvar_get((int)nbit, sc));
	}
	
	
	private static long uvar_get(int nbin, ShortenContext sc)
	{
		long result;
		
		if(nbitget == 0) 
		{
			gbuffer = word_get(sc);
			nbitget = 32;
		}

		for(result = 0; (gbuffer & (1L << --nbitget)) == 0; result++) 
		{
			if(nbitget == 0) 
			{
				gbuffer = word_get(sc);
				nbitget = 32;
			}
		}

		while(nbin != 0) 
		{
			if(nbitget >= nbin) 
			{		
				result = (result << nbin) | ((gbuffer >> (nbitget-nbin)) & masktab[nbin]);
				nbitget -= nbin;
				nbin = 0;
			} 
			else 
			{
				result = (result << nbitget) | (gbuffer & masktab[nbitget]);
				gbuffer = word_get(sc);
				nbin -= nbitget;
				nbitget = 32;				
			}
		}

		return(result);
	}
	
	private static long var_get(int nbin, ShortenContext sc)
	{
		long uvar = uvar_get(nbin + 1, sc);		
		
		if( (uvar & 1) > 0) 
			return((long) ~(uvar >> 1));
		else 
			return((long) (uvar >> 1));
	}
	
	private static long word_get(ShortenContext sc) 
	{
		long buffer;
		int b0;
		int b1;
		int b2;
		int b3;

		if(nbyteget < 4) 
		{	
			nbyteget += StreamUtils.stream_read(sc.shn_stream, Defines.BUFSIZ , sc.getbuf, 0);

			if(nbyteget < 4)
			{
				// a very bad thing has happened
			}
			getbufp = sc.getbuf;
			getbufpIdx = 0;
		}
		b0 = getbufp[getbufpIdx + 0];
		if(b0 < 0)
			b0 += 256;
			
		b1 = getbufp[getbufpIdx + 1];
		if(b1 < 0)
			b1 += 256;

		b2 = getbufp[getbufpIdx + 2];
		if(b2 < 0)
			b2 += 256;

		b3 = getbufp[getbufpIdx + 3];
		if(b3 < 0)
			b3 += 256;			
			
			
		
		buffer = (((long) b0) << 24) | (((long) b1) << 16) | (((long) b2) <<  8) | ((long) b3);
		
		getbufpIdx += 4;
		nbyteget -= 4;

	  return(buffer);
	}
	
	/* convert from signed ints to a given type and write */
	private static long fwrite_type(long[][] data, int nitem, ShortenContext sc)
	{
		int i, nwrite = 0, datasize = sizeof_sample[sc.ftype], chan;
		int temp = 0;
		int counter = 0;
		
		int startPos = sc.bufferPos;

		if(nwritebuf < sc.num_channels * nitem * datasize) 
		{
			nwritebuf = sc.num_channels * nitem * datasize;		
		}
	
		if(sc.num_channels == 1)		// mono
		{
			if(sc.sample_size == 8)
			{
				if(sc.ftype == Defines.TYPE_AU3)		// ALAW
				{				
					for(i = 0; i < nitem; i++)
					{
						if(data[0][i] < 0)
							sc.buffer[startPos + i]  = (byte)((127 - data[0][i]) ^ 0xd5);
						else
							sc.buffer[startPos + i]  = (byte)((data[0][i] + 128) ^ 0x55);
					}
				}
				else if(sc.ftype == Defines.TYPE_AU1 || sc.ftype == Defines.TYPE_AU2)		// ULAW
				{
					/* we leave the conversion to fix_bitshift() */
					for(i = 0; i < nitem; i++)
					{
						sc.buffer[startPos + i] = (byte)(data[0][i]);
					}
				}
				else if(sc.ftype == Defines.TYPE_U8)
				{
					// U8 data
					for(i = 0; i < nitem; i++)
					{
						temp = cap_max_uchar(data[0][i]);
						sc.buffer[startPos + i] = (byte)temp;					
					}
				}
				else if(sc.ftype == Defines.TYPE_S8)
				{
					// S8 data
					for(i = 0; i < nitem; i++)
					{
						temp = (int)(0x00FF & (data[0][i] + 128));
						sc.buffer[startPos + i] = (byte)temp;					
					}
				}					
			}
			else if(sc.sample_size == 16)
			{

				{
					for(i = 0; i < nitem; i++)
					{
						temp = cap_max_short(data[0][i]);
						sc.buffer[startPos + counter] =  (byte)temp;
						counter++;
						sc.buffer[startPos + counter] =  (byte)(temp >>> 8);
						counter++;
					}
				}
			}
		}
		else			// stereo
		{
			if(sc.sample_size == 8)
			{
				if(sc.ftype == Defines.TYPE_AU3)		// ALAW
				{	
					for(i = 0; i < nitem; i++)
					{
						if(data[0][i] < 0)
							sc.buffer[startPos + counter]  = (byte)((127 - data[0][i]) ^ 0xd5);
						else
							sc.buffer[startPos + counter]  = (byte)((data[0][i] + 128) ^ 0x55);
						counter++;
						if(data[1][i] < 0)
							sc.buffer[startPos + counter]  = (byte)((127 - data[1][i]) ^ 0xd5);
						else
							sc.buffer[startPos + counter]  = (byte)((data[1][i] + 128) ^ 0x55);
						counter++;	
					}
				}
				else if(sc.ftype == Defines.TYPE_AU1 || sc.ftype == Defines.TYPE_AU2)		// ULAW
				{
					/* we leave the conversion to fix_bitshift() */
					for(i = 0; i < nitem; i++)
					{
						sc.buffer[startPos + counter] = (byte)(data[0][i]);
						counter++;
						sc.buffer[startPos + counter] = (byte)(data[1][i]);
						counter++;						
					}
				}
				else if(sc.ftype == Defines.TYPE_U8)
				{
					// U8 data
					for(i = 0; i < nitem; i++)
					{
						temp = cap_max_uchar(data[0][i]);
						sc.buffer[startPos + counter] = (byte)temp;
						counter++;
						temp = cap_max_uchar(data[1][i]);
						sc.buffer[startPos + counter] = (byte)temp;
						counter++;						
					}
				}
				else if(sc.ftype == Defines.TYPE_S8)
				{
					// S8 data
					for(i = 0; i < nitem; i++)
					{
						temp = (int)(0x00FF & (data[0][i] + 128));
						sc.buffer[startPos + counter] = (byte)temp;
						counter++;
						temp = (int)(0x00FF & (data[1][i] + 128));
						sc.buffer[startPos + counter] = (byte)temp;
						counter++;						
					}
				}				
			}
			else if(sc.sample_size == 16)
			{
				// TYPE_S16LH
				for(i = 0; i < nitem; i++)
				{
					temp = cap_max_short(data[0][i]);
					sc.buffer[startPos + counter] =  (byte)temp;
					counter++;
					sc.buffer[startPos + counter] =  (byte)(temp >>> 8);
					counter++;
					temp = cap_max_short(data[1][i]);
					sc.buffer[startPos + counter] =  (byte)temp;
					counter++;
					sc.buffer[startPos + counter] =  (byte)(temp >>> 8);
					counter++;
				}
			}
		}
				
		long numBytesToWrite = 0;
		numBytesToWrite = datasize * sc.num_channels * nitem;
		
		sc.bufferPos += numBytesToWrite;

		nwrite = nitem;

		if(!sc.firstBufferDecoded)
		{
			sc.firstBufferDecoded = true;
			if(sc.original_ftype == 1)
			{
				// original encoded file was AIFF, need to ignore the very first 8 bytes.
				
				for(i=0; i < (numBytesToWrite - 8); i++)
				{
					sc.buffer[i] = sc.buffer[i+8];
				}
				sc.bufferPos = sc.bufferPos - 8;
				numBytesToWrite = numBytesToWrite - 8;
				
			}
		}		
		
		return numBytesToWrite;
	}
	
	private static int cap_max_short(long x)
	{
		if(x > 32767)
		{
			return(32767);
		}
		else
		{
			return((int)x);
		}
	}
	
	private static int cap_max_uchar(long x)
	{
		if(x > 255)
		{
			return(255);
		}
		else
		{
			return((int)x);
		}
	}
	
	// Heres where we extract the actual music data
	
	public static int ShortenUnpackSamples(ShortenContext sc, int[] pDestBuffer)
	{
		
		int outputBytes = 0;
	
		return (outputBytes);
	
	}
	

	// Returns the sample rate of the specified Shorten file

    public static int ShortenGetSampleRate(ShortenContext sc)
    {
        if ( null != sc && sc.sample_rate != 0)
        {
            return sc.sample_rate;
        }
        else
        {
            return (44100);
        }
    }
	
	public static int ShortenGetNumChannels(ShortenContext sc)
    {
        if ( null != sc && sc.num_channels != 0)
        {
            return sc.num_channels;
        }
        else
        {
            return 2;
        }
    }
	
	public static int ShortenGetBitsPerSample(ShortenContext sc)
    {
        if (null != sc && sc.sample_size != 0)
        {
            return sc.sample_size;
        }
        else
        {
            return 16;
        }
    }
	

	public static int ShortenGetBytesPerSample(ShortenContext sc)
    {
        if ( null != sc && sc.sample_size != 0)
        {
            return (int)Math.ceil(sc.sample_size/8);
        }
        else
        {
            return 2;
        }
    }
	
	
	// Get total number of samples contained in the Shorten file, or -1 if unknown

    public static int ShortenGetNumSamples(ShortenContext sc)
    {
		/* calculate output size */		
		int num_samples = 0;
		
		if ( null == sc)
		{
			return (-1);
		}		
		
		try
		{
			num_samples = sc.datasize / sc.num_channels;
			if(sc.sample_size == 16)
			{
				num_samples = num_samples / 2;
			}
			
		}
		catch(Exception e)
		{
			num_samples = 0;
		}
		
		return (num_samples);
	}
	
	// Checks that this file uses standard PCM data
	
	public static boolean ShortenCheckPCM(ShortenContext sc)
    {
		if ( null == sc)
		{
			return (false);
		}
		
		if(sc.ftype == Defines.TYPE_AU1 || sc.ftype == Defines.TYPE_AU2 || sc.ftype == Defines.TYPE_AU3 || sc.ftype == Defines.TYPE_ALAW || sc.ftype == Defines.TYPE_ULAW || sc.ftype == Defines.TYPE_GENERIC_ULAW || sc.ftype == Defines.TYPE_GENERIC_ALAW )
		{
			return (false);
		}
		else
		{
			return (true);
		}
	}
}	