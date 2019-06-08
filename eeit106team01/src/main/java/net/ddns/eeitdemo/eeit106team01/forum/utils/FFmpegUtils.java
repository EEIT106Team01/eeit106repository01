package net.ddns.eeitdemo.eeit106team01.forum.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegStream;

/**
 * Support video type : All the ffmpeg supported encode type, see
 * https://www.ffmpeg.org/general.html#File-Formats
 * 
 * @author Chiayen
 *
 */
public class FFmpegUtils {
	private static final String WINDOWS_FFMPEG_PATH = "C:/JAVA/ffmpeg/bin/ffmpeg.exe";
	private static final String WINDOWS_FFPROBE_PATH = "C:/JAVA/ffmpeg/bin/ffprobe.exe";
	private static final String LINUX_FFMPEG_PATH = "/usr/bin/ffmpeg";
	private static final String LINUX_FFPROBE_PATH = "/usr/bin/ffprobe";
	private FFmpeg ffmpeg;
	private FFprobe ffprobe;

	/**
	 * Use default fields of FFmpegPath and FFprobePath written in this class for
	 * FFmpeg and FFprobe.
	 * 
	 * @throws IOException
	 */
	public FFmpegUtils() throws IOException {
		try {
			this.ffmpeg = new FFmpeg(LINUX_FFMPEG_PATH);
			this.ffprobe = new FFprobe(LINUX_FFPROBE_PATH);
		} catch (IOException e) {
			try {
				this.ffmpeg = new FFmpeg(WINDOWS_FFMPEG_PATH);
				this.ffprobe = new FFprobe(WINDOWS_FFPROBE_PATH);
			} catch (Exception e1) {
				throw new IOException(e1);
			}
		}
	}

	/**
	 * Use specific path for FFmpeg and FFprobe.
	 * 
	 * @param FFmpegPath  Path of FFmpeg.
	 * @param FFprobePath Path of FFprobe.
	 * @throws IOException
	 */
	public FFmpegUtils(String ffmpegPath, String ffprobePath) throws IOException {
		this.ffmpeg = new FFmpeg(ffmpegPath);
		this.ffprobe = new FFprobe(ffprobePath);
	}

	/**
	 * 
	 * @param inFile Input video file.
	 * @return Video duration in seconds.
	 * @throws IOException
	 */
	public double getVideoDuration(File inFile) throws IOException {
		return ffprobe.probe(inFile.getAbsolutePath()).format.duration;
	}

	/**
	 * 
	 * @param inFile Input video file.
	 * @return Video duration in seconds.
	 * @throws IOException
	 */
	public long getVideoBitrate(File inFile) throws IOException {
		return ffprobe.probe(inFile.getAbsolutePath()).format.bit_rate;
	}
	
	/**
	 * 
	 * @param inFile Input video file.
	 * @return Resolution: widthxheight
	 * @throws IOException
	 */
	public String getVideoResolution(File inFile) throws IOException {
		List<FFmpegStream> streams = ffprobe.probe(inFile.getAbsolutePath()).getStreams();
		return streams.get(0).width + "x" + streams.get(0).height;
	}

	/**
	 * This method generate thumbnail from input video file. <br>
	 * The output file name must end with ".jpg".
	 * 
	 * @param inFile  Input video file.
	 * @param outFile Output jpg file.
	 * @param start   Generate thumbnail of specific second, -1 for random generate.
	 * @throws IOException
	 */
	public void generateThumbnail(File inFile, File outFile, int start) throws IOException {
		if (!outFile.getName().endsWith(".jpg")) {
			System.out.println("Name of outFile must end with \".jpg\".");
			return;
		}
		double duration = this.getVideoDuration(inFile);
		if (start == -1) {
			start = (int) (Math.random() * duration);
		} else if (start < 0) {
			System.out.println("Incorrect value of start, stop execute.");
			return;
		} else if (start > duration) {
			start = (int) Math.floor(duration);
		}
		FFmpegBuilder builder = new FFmpegBuilder()
				.setStartOffset(start, TimeUnit.SECONDS)
				.setInput(inFile.getAbsolutePath())
				.overrideOutputFiles(true)
				.addOutput(outFile.getAbsolutePath())
				.addExtraArgs("-qscale:v", "2", "-frames:v", "1")
				.setStrict(FFmpegBuilder.Strict.NORMAL)
				.done();
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		long time = System.currentTimeMillis();
		// Run a one-pass encode
		executor.createJob(builder).run();
		System.out.println("Generate cost : " + (System.currentTimeMillis() - time) + " ms");
	}

	/**
	 * This method generates short cut from input video file.<br>
	 * Will override output file if exist.
	 * 
	 * @throws IOException
	 * @param inFile  Input video file.
	 * @param outFile Output gif file.
	 * @param start   Generated gif start point in seconds.
	 * @param length  Generated gif total length in seconds.
	 * @throws IOException
	 */
	public void generateGifCut(File inFile, File outFile, int start, int length) throws IOException {
		double duration = this.getVideoDuration(inFile);
		if (duration == 0) {
			System.out.println("Incorrect value of video duration, stop execute.");
			return;
		} else if (duration <= length || length == 0) {
			length = (int) Math.floor(duration);
		}
		if (start > duration || start < 0) {
			System.out.println("Incorrect value of start, stop execute.");
			return;
		}
		FFmpegBuilder builder = new FFmpegBuilder()
				.setStartOffset(start, TimeUnit.SECONDS)
				.setInput(inFile.getAbsolutePath())
				.overrideOutputFiles(true)
				.addOutput(outFile.getAbsolutePath())
				.setFormat("gif")
				.disableSubtitle()
				.setVideoFrameRate(15, 1)
				.setVideoResolution(854, 480)
				.setDuration(length, TimeUnit.SECONDS)
				.setStrict(FFmpegBuilder.Strict.NORMAL)
				.done();
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		long time = System.currentTimeMillis();
		// Run a one-pass encode
		executor.createJob(builder).run();
		System.out.println("Generate cost : " + (System.currentTimeMillis() - time) + " ms");
	}

	/**
	 * This method generates short cut from input video file.<br>
	 * If input video longer then thirty seconds, this method will cut four parts
	 * from input video, then merge these parts into output gif file. <br>
	 * Otherwise generate gif file of the first twelve seconds of input video.
	 * 
	 * @param inFile  Input video file.
	 * @param outFile Output gif file.
	 * @throws IOException
	 */
	public void generateDefaultGifCut(File inFile, File outFile) throws IOException {
		double duration = this.getVideoDuration(inFile);
		if (duration == 0) {
			System.out.println("Incorrect value of video duration, stop execute.");
			return;
		} else if (duration < 30) {
			this.generateGifCut(inFile, outFile, 0, 12);
		} else if (duration >= 30) {
			int start = (int) Math.floor(duration / 9);
			File[] tempGifs = { 
					new File(inFile.getAbsolutePath() + ".temp1"),
					new File(inFile.getAbsolutePath() + ".temp2"),
					new File(inFile.getAbsolutePath() + ".temp3"),
					new File(inFile.getAbsolutePath() + ".temp4") 
					};
			long time = System.currentTimeMillis();
			System.out.println("---------Start---------");
			for (int i = 0; i < tempGifs.length; i++) {
				this.generateGifCut(inFile, tempGifs[i], start * (i + 1) * 2, 3);
			}
			System.out.println("Merge file......");
			File[] tempVideos = new File[4];
			for (int i = 0; i < tempVideos.length; i++) {
				tempVideos[i] = new File(tempGifs[i].getAbsolutePath() + ".tempMp4");
				this.gifToMp4(tempGifs[i], tempVideos[i]);
			}
			File outFileTemp = new File(outFile.getAbsolutePath() + ".tmep");
			this.mergeMp4(outFileTemp, tempVideos);
			this.generateGifCut(outFileTemp, outFile, 0, 0);
			System.out.println("----------End----------");
			System.out.println("Total cost time : " + (System.currentTimeMillis() - time) + " ms");
			for (File tempGif : tempGifs) {
				tempGif.delete();
			}
			for (File tempVideo : tempVideos) {
				tempVideo.delete();
			}
			outFileTemp.delete();
		}
	}

	/**
	 * This method convert video to mp4 video file with specific resolution.
	 * 
	 * @param inFile  Input video file.
	 * @param outFile Input mp4 file.
	 * @param height  1080 for 1920x1080, 720 for 1280x720, 480 for 854x480, other
	 *                value for nothing.
	 */
	public void convertVideoToMp4(File inFile, File outFile, int height) {
		int width = 0;
		long bitrate = 0;
		if (height == 1080) {
			width = 1920;
			bitrate = 2000000;
		} else if (height == 720) {
			width = 1280;
			bitrate = 1200000;
		} else if (height == 480) {
			width = 854;
			bitrate = 500000;
		} else {
			return;
		}
		this.convertVideo(inFile, outFile, "mp4", width, height, bitrate, 30);
	}

	/**
	 * This method convert video to video file with specific format, resolution,
	 * bitrate and frames per second.
	 * 
	 * @param inFile       Input video file.
	 * @param outFile      Output video file.
	 * @param outputFormat Specific format for output video file.
	 * @param width        Specific width for resolution.
	 * @param height       Specific height for resolution.
	 * @param bitrate      Specific bitrate for output video file.
	 * @param fps          Specific fps for output video file.
	 */
	private void convertVideo(File inFile, File outFile, String outputFormat, int width, int height, long bitrate,
			int fps) {
		FFmpegBuilder builder = new FFmpegBuilder()
				.setInput(inFile.getAbsolutePath())
				.overrideOutputFiles(true)
				.addOutput(outFile.getAbsolutePath())
				.setFormat(outputFormat)
				.setVideoResolution(width, height)
				.setVideoBitRate(bitrate)
				.setVideoFrameRate(fps, 1)
				.setStrict(FFmpegBuilder.Strict.NORMAL)
				.done();
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		long time = System.currentTimeMillis();
		// Run a one-pass encode
		executor.createJob(builder).run();
		System.out.println("Generate cost : " + (System.currentTimeMillis() - time) + " ms");
	}

	/**
	 * <b>Only for this class</b><br>
	 * This method convert gif to mp4.
	 * 
	 * @param inFile  Input gif file.
	 * @param outFile Output video file.
	 * @throws IOException
	 */
	private void gifToMp4(File inFile, File outFile) throws IOException {
		FFmpegBuilder builder = new FFmpegBuilder()
				.setInput(inFile.getAbsolutePath())
				.setFormat("gif")
				.overrideOutputFiles(true)
				.addOutput(outFile.getAbsolutePath())
				.setFormat("mp4")
				.setStrict(FFmpegBuilder.Strict.NORMAL)
				.done();
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		long time = System.currentTimeMillis();
		// Run a one-pass encode
		executor.createJob(builder).run();
		System.out.println("Generate cost : " + (System.currentTimeMillis() - time) + " ms");
	}

	/**
	 * <b>Only for this class</b><br>
	 * This method merges mp4 files into one mp4 file. <br>
	 * If input mp4 files have different encoder type, may cause unexpected error.
	 * 
	 * @param outFile Output video file.
	 * @param inFiles Input video files.
	 * @throws IOException
	 */
	private void mergeMp4(File outFile, File... inFiles) throws IOException {
		File tempList = new File(outFile.getParent() + "/" + outFile.getName() + ".txt");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempList))) {
			for (File inFile : inFiles) {
				bw.write("file '" + inFile.getAbsolutePath() + "'");
				bw.newLine();
			}
			bw.flush();
		}
		FFmpegBuilder builder = new FFmpegBuilder()
				.addExtraArgs("-f", "concat", "-safe", "0")
				.setInput(tempList.getAbsolutePath())
				.overrideOutputFiles(true)
				.addOutput(outFile.getAbsolutePath())
				.setFormat("mp4")
				.setStrict(FFmpegBuilder.Strict.NORMAL)
				.done();
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		long time = System.currentTimeMillis();
		// Run a one-pass encode
		executor.createJob(builder).run();
		System.out.println("Generate cost : " + (System.currentTimeMillis() - time) + " ms");
		tempList.delete();
	}
	
	/**
	 * This method generates sprites and vtt files into outFolder. <br>
	 * Notice that outFolder will be clear when execute this method.
	 * 
	 * @param inFile Input video file.
	 * @param outFolder Output folder for generated sprites and vtt files.
	 * @return Generated vtt file or null if failed.
	 * @throws IOException
	 */
	public File generateSpritesAndVTT(File inFile, File outFolder) throws IOException {
		if (!outFolder.isDirectory()) {
			System.out.println("outFolder must be a folder");
			return null;
		}
		File[] oldFiles = outFolder.listFiles();
		for(File oldFile : oldFiles) {
			oldFile.delete();
		}
		FFmpegBuilder builder = new FFmpegBuilder()
				.setInput(inFile.getAbsolutePath())
				.overrideOutputFiles(true)
				.addOutput(outFolder.getAbsolutePath() + "\\sprites%00d.jpg")
				.addExtraArgs("-filter_complex", "fps=1,scale=427:240,tile=layout=10x6")
				.addExtraArgs("-q:v", "2")
				.setStrict(FFmpegBuilder.Strict.NORMAL)
				.done();
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		long time = System.currentTimeMillis();
		// Run a one-pass encode
		executor.createJob(builder).run();
		long videoDuration = (long) this.getVideoDuration(inFile);
		File vtt = new File(outFolder.getAbsolutePath() + "/sprite.vtt");
		try (
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(vtt)));
				) {
			bw.write("WEBVTT");
			for (long secs = 1, x = 0, y = 0; secs <= videoDuration; secs++, x = x + 427) {
				if ((x > 0) && (x > 427 * 9)) {
					x = 0;
					y = y + 240;
				}
				if (y > 240 * 5) {
					y = 0;
				}
				long start = secs - 1;
				long end = secs;
				int sprite = (int) Math.ceil(secs/60d);
				bw.newLine();
				bw.newLine();
				bw.write(String.valueOf(secs));
				bw.newLine();
				bw.write(String.format("%02d:%02d:%02d.000 --> %02d:%02d:%02d.000", start / 3600, (start % 3600) / 60, (start % 60), end / 3600, (end % 3600) / 60, (end % 60)));
				bw.newLine();
				bw.write("sprites" + sprite + ".jpg#xywh=" + x + "," + y + ",427,240");
			}
			bw.flush();
		}
		System.out.println("Generate cost : " + (System.currentTimeMillis() - time) + " ms");
		return vtt;
	}

	public static void main(String[] args) throws IOException {
		File inFile = new File("C:/JAVA/baseball.mp4");
//		File outFile = new File("C:/JAVA/output");
//		File outFileJpg = new File("C:/JAVA/basketball.jpg");
//		File outFileGif = new File("C:/JAVA/basketball.gif");
		
		FFmpegUtils ffu = new FFmpegUtils();
		System.out.println(ffu.getVideoResolution(inFile));
	}
	
}
