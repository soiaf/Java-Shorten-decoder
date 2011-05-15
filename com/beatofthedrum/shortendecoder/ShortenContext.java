/*
** ShortenContext.java
**
** Copyright (c) 2011 Peter McQuillan
**
** All Rights Reserved.
**                       
** Distributed under the BSD Software License (see license.txt)  
**
*/

package com.beatofthedrum.shortendecoder;

public class ShortenContext
{
	MyStream shn_stream = new MyStream();
	public boolean error;
	public String error_message = "";
	int num_channels = 0;
	int sample_rate = 0;	// e.g. 44100 samples a second
	int sample_size = 0;	// e.g. 16 bit samples
	int version = 0;	// version of Shorten file
	int[] getbuf = new int[Defines.BUFSIZ];
	public int ftype = 0;
	public int original_ftype = 0;		// 0 = WAV, 1 = AIFF
	public boolean firstBufferDecoded = true;	// if original file was AIFF, then first 8 bytes need to be ignored, we use this value to help
	public boolean quitActivated = false;	// decoder sets this to true when all data processed 
	public byte[] buffer = new byte[65536];
	public int bufferPos = 0;	// current position in buffer to add new data to
	public int datasize = 0;	// size in bytes of the actual music data
}