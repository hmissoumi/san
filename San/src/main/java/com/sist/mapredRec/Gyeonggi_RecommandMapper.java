package com.sist.mapredRec;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.springframework.stereotype.Repository;

public class Gyeonggi_RecommandMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	
	private final IntWritable one=new IntWritable(1);
	private Text result=new Text();
	String[] san={"가덕산","가리산","가학산","각흘산","갈기산","감악산","강씨봉","건지산","검단산",
			"견치봉","계관산","고대산","고동산","고래산","고령산","고성산","곡달산","관산","관악산","관음산",
			"관인봉","광교산","광덕산","구나무산","구름산","구봉산","국망봉","군자봉","귀목봉","금물산","금박산",
			"금주산","길매봉","깃대봉","깃대봉","남한산","노고봉","녹수봉","단월산","당산","대금산",
			"대덕산","대부산","덕성산","도덕산","도드람산","도마치봉","도봉산","도일봉","독조봉",
			"마감산","마옥산","마차산","말아가리산","망이산","매곡산","매봉","명성산","명지산","모락산",
			"몽덕산","무갑산","무학봉","문수봉","문수산","문안산","문형산","민둥산","바라산","바른골봉",
			"박달봉","백둔봉","백마산","백봉","백운봉","백운산","백운산","보개산","보금산","봉미산","부아산",
			"북배산","북한산","불곡산","불곡산","불기산","불암산","비룡산","뾰루봉","사패산","사향봉","사향산",
			"삼각봉","삼봉산","삼성산","서독산","서리산","서운산","석룡산","선장산","설봉산","설성산","성산",
			"성지봉","소구니산","소래산","소리산","소요산","수덕산","수락산","수리산","시궁산","아기봉","애기봉",
			"앵자봉","양자산","어비산","여우봉","연인산","열미봉","예봉산","오갑산","오봉","옥녀봉","옥산",
			"왕방산","왕터산","용마산","용문산","용조봉","우두산","우면산","운길산","운악산","원적산","유명산",
			"은두봉","장락산","적갑산","정광산","정암산","종자산","종현산","주금산","주읍산","죽엽산","중미산",
			"중원산","지장산","천덕봉","천마산","천보산","철마산","청계산","청계산","청계산","청우산","촛대봉",
			"축령산","칠보산","칠봉산","칠봉산","칠장산","칠현산","칼봉산","태을봉","태화산","통방산","팔달산",
			"편전산","한나무봉","함박산","함왕봉","해협산","향수산","형제봉","형제봉","호명산","화악산","화야산","화인봉"};
	
	Pattern[] pattern=new Pattern[san.length];
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		for(int i=0; i<san.length;i++){
			pattern[i]=Pattern.compile(san[i]);
		}
		
		Matcher[] matcher=new Matcher[san.length];
		
		for(int i=0;i<san.length;i++){
			matcher[i]=pattern[i].matcher(value.toString());
			
			while(matcher[i].find()){
				result.set(san[i]);				//String을 text로 바꿀때 set 사용
				context.write(result, one);
				
			}
		}
	
	}

}
