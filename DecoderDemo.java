/*
** DecoderDemo.java
**
** Copyright (c) 2011 Peter McQuillan
**
** All Rights Reserved.
**                       
** Distributed under the BSD Software License (see license.txt)  
**
*/
import com.beatofthedrum.shortendecoder.*;

class DecoderDemo
{

	static java.io.FileOutputStream output_stream;
	static java.io.DataInputStream input_stream;
	static int output_opened;

	static int write_wav_format = 1;

	static String input_file_n = "";
	static String output_file_n = "";


	
	static void setup_environment(int argc, String[] argv)
	{
		int i = argc;

		int escaped = 0;

		if (argc < 2)
			usage();

		int arg_idx = 0;
		// loop through command-line arguments
		while (arg_idx < argc)
		{
			if (input_file_n.length() == 0)
			{
				input_file_n = argv[arg_idx];
			}
			else if (output_file_n.length() == 0)
			{
				output_file_n = argv[arg_idx];
			}
			else
			{
				System.out.println("extra unknown argument: " + argv[arg_idx]);
				usage();
			}
			arg_idx++;
		}

		if (input_file_n.length() == 0 || output_file_n.length() == 0 )
			usage();

	}

	static void GetBuffer(ShortenContext sc)
	{
	
		int total_unpacked_bytes = 0;
		int bytes_unpacked;
		
			
		while (true)
		{
			bytes_unpacked = 0;

			bytes_unpacked = (int)ShortenUtils.DecodeBuffer(sc);

			total_unpacked_bytes += bytes_unpacked;

			if (bytes_unpacked > 0)
			{
				try
				{
					output_stream.write(sc.buffer, 0, bytes_unpacked);
				}
				catch(java.io.IOException ioe)
				{
					System.err.println("Error writing data to output file. Error: " + ioe);
				}
			}

			if (bytes_unpacked == 0 || sc.quitActivated==true)
				break;
		} // end of while
		
	}


	static void usage() 
	{
		System.out.println("Usage: java DecoderDemo inputfile outputfile");
		System.out.println("Decompresses the Shorten file specified");
		System.out.println("");
		System.out.println("(c) Peter McQuillan 2011");
		System.exit(1);
	}

	public static void main(String [] args)
	{
		ShortenContext sc = new ShortenContext();
		int output_size;
		int total_samples; 
		int sample_rate;
        int num_channels;
		int byteps;
		int bitps;

		output_opened = 0;

		setup_environment(args.length, args);	// checks all the parameters passed on command line
		
		try
		{
			java.io.FileInputStream fistream;
			fistream = new java.io.FileInputStream(input_file_n);
			input_stream = new java.io.DataInputStream(fistream);
		}
		catch (java.io.FileNotFoundException fe)
		{
			System.err.println("Cannot open input file: " + input_file_n + " : Error : " + fe);
			System.exit(1);
		}
		
		try
		{
			output_stream = new java.io.FileOutputStream(output_file_n);
			output_opened = 1;
		}
		catch(java.io.IOException ioe)
		{
			System.err.println("Cannot open output file: " + output_file_n + " : Error : " + ioe);
			output_opened = 0;
			System.exit(1);
		}
		
		sc = ShortenUtils.ShortenOpenFileInput(input_stream);
		
		if (sc.error)
        {
            System.err.println("Sorry an error has occured");
            System.err.println(sc.error_message);
            System.exit(1);
        }
		
		// Check if data in Shorten file is standard PCM - strictly speaking for this demo I don't need to do this as this code
		// generates WAV files that support non-PCM, but I'm putting the code in here for clarity/demostration purposes
		
		if(!ShortenUtils.ShortenCheckPCM(sc))
		{
            System.err.println("Sorry, but the data in this Shorten file is not standard PCM data.");
            System.exit(1);		
		}
		
		num_channels = ShortenUtils.ShortenGetNumChannels(sc);

		System.out.println("The Shorten file has " + num_channels + " channels");

        total_samples = ShortenUtils.ShortenGetNumSamples(sc);

		System.out.println("The Shorten file has " + total_samples + " samples");

        byteps = ShortenUtils.ShortenGetBytesPerSample(sc);

		System.out.println("The Shorten file has " + byteps + " bytes per sample");
		
		sample_rate = ShortenUtils.ShortenGetSampleRate(sc);
		
		bitps = ShortenUtils.ShortenGetBitsPerSample(sc);


		/* write wav output headers */
		if (write_wav_format != 0)
		{
			int encodingtype = 1;	// PCM
			
			if(sc.ftype == Defines.TYPE_ALAW || sc.ftype==Defines.TYPE_GENERIC_ALAW || sc.ftype==Defines.TYPE_AU3)
			{
				// ALAW
				encodingtype = 6;
			}
			if(sc.ftype == Defines.TYPE_AU1 || sc.ftype == Defines.TYPE_AU2 || sc.ftype == Defines.TYPE_ULAW || sc.ftype==Defines.TYPE_GENERIC_ULAW)
			{
				// ULAW
				encodingtype = 7;
			}
			
			WavWriter.wavwriter_writeheaders(output_stream, sc.datasize, num_channels, sample_rate, byteps, bitps, encodingtype);
		}

		/* will convert the entire buffer */
		GetBuffer(sc);
		
		ShortenUtils.ShortenCloseFile(sc);

		if (output_opened != 0)
		{
			try
			{
				output_stream.close();
			}
			catch(java.io.IOException ioe)
			{
			}
		}
	}
}

