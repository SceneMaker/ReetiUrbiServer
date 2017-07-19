package cereprocTts;

import javax.sound.sampled.SourceDataLine;

import com.cereproc.cerevoice_eng.CPRC_ABUF_TRANS_TYPE;
import com.cereproc.cerevoice_eng.SWIGTYPE_p_CPRC_abuf;
import com.cereproc.cerevoice_eng.SWIGTYPE_p_CPRC_abuf_trans;
import com.cereproc.cerevoice_eng.TtsEngineCallback;
import com.cereproc.cerevoice_eng.cerevoice_eng;

//In CereVoice, a callback can be specified to receive data every
//time a phrase has been synthesised. The user application can
//process that data safely, as the engine does not continue until the
//user returns from the callback.  Ideally, the processing done in
//the callback itself should be kept simple, such as sending audio
//data to the audio player, or sending timed phonetic information to
//a seperate thread for lip animation on a talking head.
public class MyTtsCallback extends TtsEngineCallback {

    private SourceDataLine line;

    // Initialise with an audio  line for playback
    public MyTtsCallback(SourceDataLine line) {
        this.line = line;
    }

    // Process the data in the callback.  Here we convert audio from
    // shorts to bytes to play in Java.  The callback function
    // receives a CereVoice audio buffer object, extracts the audio
    // and plays it.
    public void Callback(SWIGTYPE_p_CPRC_abuf abuf) {
//		System.out.println("INFO: firing engine callback");
        int i, sz;
        // sz is the number of 16-bits samples
//		System.out.println("INFO: checking audio size");
        sz = cerevoice_eng.CPRC_abuf_wav_sz(abuf);
        byte[] b = new byte[sz * 2];
        byte b1, b2;
        short s;
        SWIGTYPE_p_CPRC_abuf_trans trans;
        CPRC_ABUF_TRANS_TYPE transtype;
        float start, end;
        String name;

        // First process the transcription information and print it
        /* Process the transcription buffer items and print information. */
//		for(i = 0; i < cerevoice_eng.CPRC_abuf_trans_sz(abuf); i++) {
//		    trans = cerevoice_eng.CPRC_abuf_get_trans(abuf, i);
//		    transtype = cerevoice_eng.CPRC_abuf_trans_type(trans);
//		    start = cerevoice_eng. CPRC_abuf_trans_start(trans);
//		    end = cerevoice_eng.CPRC_abuf_trans_end(trans);
//		    name = cerevoice_eng.CPRC_abuf_trans_name(trans);
//		    if (transtype == CPRC_ABUF_TRANS_TYPE.CPRC_ABUF_TRANS_PHONE) {
//			System.err.printf("INFO: phoneme: %.3f %.3f %s\n", start, end, name);
//		    }
//		    else if (transtype == CPRC_ABUF_TRANS_TYPE.CPRC_ABUF_TRANS_WORD) {
//			System.err.printf("INFO: word: %.3f %.3f %s\n", start, end, name);
//		    }
//		    else if (transtype == CPRC_ABUF_TRANS_TYPE.CPRC_ABUF_TRANS_MARK) {
//			System.err.printf("INFO: marker: %.3f %.3f %s\n", start, end, name);
//		    }
//		    else if (transtype == CPRC_ABUF_TRANS_TYPE.CPRC_ABUF_TRANS_ERROR) {
//			System.err.printf("ERROR: could not retrieve transcription at '%d'", i);
//		    }
//		}
        // Now extract the audio, convert it, send it to java audio playback
        // This is not the most elegant way to do this conversion, but shows
        // how e.g. audio effects could be applied.
        for (i = 0; i < sz; i++) {
            // Sample at position i, a short
            s = cerevoice_eng.CPRC_abuf_wav(abuf, i);
            // The sample is written in Big Endian to the buffer
            b[i * 2] = (byte) ((s & 0xff00) >> 8);
            b[i * 2 + 1] = (byte) (s & 0x00ff);
        }

        CereprocHandle.SpeakFlag = true;
        // Send the audio data to the Java audio player
        line.write(b, 0, sz * 2);
    }

}
