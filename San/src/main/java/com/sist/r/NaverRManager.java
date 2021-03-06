package com.sist.r;
import java.util.*;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.stereotype.Component;

import com.sist.mongo.FeelVO;
import com.sist.mongo.LocalVO;
import com.sist.mongo.ThingsVO;
import com.sist.mongo.WeekdayVO;
@Component
public class NaverRManager {
	
	public void rGraph(){
		try{
			RConnection rc=new RConnection();			
			rc.voidEval("naver<-read.table(\"/home/sist/git/san/San/src/main/webapp/data/naver/output/part-r-00000\")");			
			rc.voidEval("png(\"/home/sist/git/san/San/src/main/webapp/R/naver.png\",width=900,height=500)");			
			rc.voidEval("par(mfrow=c(1,2))");//그림그리기			
			rc.voidEval("pie(naver$V2,labels=naver$V1,col=rainbow(10))");			
			rc.voidEval("barplot(naver$V2,names.arg=naver$V1d,col=rainbow(10))");			
			rc.voidEval("dev.off()");
			rc.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
		
		public List<LocalVO> rLocalData(){
			
			List<LocalVO> list=new ArrayList<LocalVO>();
			try{
				RConnection rc=new RConnection();
				rc.voidEval("data<-read.table(\"/home/sist/git/san/San/src/main/webapp/data/naver/output/local/part-r-00000\")");
				REXP p=rc.eval("data$V1");	//1.지역
				String[] local=p.asStrings();	
				p=rc.eval("data$V2");			//2.카운트
				int[] count=p.asIntegers();
				rc.close();
				
				for(int i=0; i<count.length; i++){
					if(count[i]>=7){				
						LocalVO vo=new LocalVO();
						vo.setLocal(local[i]);
						vo.setCount(count[i]);
						list.add(vo);
					}
				}
			
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return list;
		}
		
		public List<SeasonVO> rSeasonData(int c){
			
			List<SeasonVO> list=new ArrayList<SeasonVO>();
			
			try{
				RConnection rc=new RConnection();
				if(c==0){
					rc.voidEval("data<-read.table(\"/home/sist/git/san/San/src/main/webapp/data/naver/output/season/part-r-00000\")");
				}else if(c==1){
					rc.voidEval("data<-read.table(\"/home/sist/git/san/San/src/main/webapp/data/naver/output/recommand/season/part-r-00000\")");
				}
				REXP p=rc.eval("data$V1");	//1.계절
				String[] season=p.asStrings();	
				p=rc.eval("data$V2");			//2.카운트
				int[] count=p.asIntegers();
				rc.close();
				
				for(int i=0; i<count.length; i++){			
						SeasonVO vo=new SeasonVO();
						vo.setSeason(season[i]);
						vo.setCount(count[i]);
						list.add(vo);
				}
			
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return list;
		}
		
		public List<LocalVO> rRecommandData(){
			
			List<LocalVO> list=new ArrayList<LocalVO>();
			try{
				RConnection rc=new RConnection();
				rc.voidEval("data<-read.table(\"/home/sist/git/san/San/src/main/webapp/data/naver/output/recommand/part-r-00000\")");
				REXP p=rc.eval("data$V1");	//1.산
				String[] local=p.asStrings();	
				p=rc.eval("data$V2");			//2.카운트
				int[] count=p.asIntegers();
				rc.close();
				
				for(int i=0; i<count.length; i++){	//오름차순정렬
					for(int j=i+1; j<count.length; j++){
						if(count[j]>count[i]){
							int temp=count[i];
							count[i]=count[j];
							count[j]=temp;
							String temp1=local[i];
							local[i]=local[j];
							local[j]=temp1;
						}
					}
				}
				
				for(int i=0; i<5; i++){	
					LocalVO vo=new LocalVO();
					vo.setLocal(local[i]);
					vo.setCount(count[i]);
					//System.out.println(vo.getCount()+","+vo.getLocal());
					list.add(vo);
				}
			
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return list;
		}
	
		
		public List<ThingsVO> rThingsData(){	
			List<ThingsVO> list=new ArrayList<ThingsVO>();
			try{
				
				RConnection rc=new RConnection();
				rc.voidEval("things<-read.table(\"/home/sist/git/san/San/src/main/webapp/data/naver/output/things/part-r-00000\")");
				 rc.voidEval("things<-things[order(things$V2,decreasing=T),c(\"V1\",\"V2\")]");
				REXP p=rc.eval("things$V1");		//1.준비물
				String[] things=p.asStrings();	
				p=rc.eval("things$V2");			//2.카운트
				int[] count=p.asIntegers();
				rc.close();
				String[] color={"#FF0F00","#FF9E01","#04D215","#0D52D1","#2A0CD0","#8A0CCF"};
				
				for(int i=0; i<6; i++){
					System.out.println(things[i]);
					ThingsVO vo=new ThingsVO();
						vo.setThings(things[i]);
						vo.setCount(count[i]);
						vo.setColor(color[i]);
						list.add(vo);
					
				}
			
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return list;
		}
		
		
		public List<WeekdayVO> rWeekData(){
			
			List<WeekdayVO> list = new ArrayList<WeekdayVO>();
			
			try{
				RConnection rc = new RConnection();
				rc.voidEval("week<-read.table(\"/home/sist/git/san/San/src/main/webapp/data/naver/output/weekday/part-r-00000\")");
				REXP p = rc.eval("week$V1");
				String[] days = p.asStrings();
				p = rc.eval("week$V2");
				int[] count = p.asIntegers();
				rc.close();
				
				for(int i=0; i<days.length; i++){
					WeekdayVO vo = new WeekdayVO();
					vo.setDay(days[i]);
					vo.setCount(count[i]);
					list.add(vo);
				}
				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			
			
			return list;
		}
		
		public List<FeelVO> rFeelData(){
			
			List<FeelVO> list = new ArrayList<FeelVO>();
			
			try{
				
				RConnection rc = new RConnection();		
				rc.voidEval("feel<-read.table(\"/home/sist/git/san/San/src/main/webapp/data/naver/output/feel/part-r-00000\")");
				//rc.voidEval("feel<-read.table(\"/home/sist/data\")");
				
				REXP p = rc.eval("feel$V1");
				String[] feels = p.asStrings();
				p = rc.eval("feel$V2");
				int[] count = p.asIntegers();
				rc.close();

				for(int i=0; i<feels.length; i++){
					FeelVO vo = new FeelVO();
					vo.setFeel(feels[i]);
					vo.setCount(count[i]);
					list.add(vo);
				}
				
				
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
			
			return list;
		}
		
		public List<FoodVO> rFoodData(){
			
			List<FoodVO> list=new ArrayList<FoodVO>();
			
			try{
				RConnection rc=new RConnection();
				rc.voidEval("data<-read.table(\"/home/sist/git/san/San/src/main/webapp/data/naver/output/food/part-r-00000\")");
				REXP p=rc.eval("data$V1");	//1.음식
				String[] food=p.asStrings();	
				p=rc.eval("data$V2");			//2.카운트
				int[] count=p.asIntegers();
				rc.close();
				
				for(int i=0; i<count.length; i++){			
						FoodVO vo=new FoodVO();
						vo.setFood(food[i]);
						vo.setCount(count[i]);
						list.add(vo);
				}
			
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return list;
		}

		public List<WhoVO> rWhoData(){
		List<WhoVO> list=new ArrayList<WhoVO>();
		
		try{
			RConnection rc=new RConnection();
			rc.voidEval("data<-read.table(\"/home/sist/git/san/San/src/main/webapp/data/naver/output/recommand/who/part-r-00000\")");
			REXP p=rc.eval("data$V1");	//1.누구랑
			String[] who=p.asStrings();	
			p=rc.eval("data$V2");			//2.카운트
			int[] count=p.asIntegers();
			rc.close();
			int total=0;
			for(int i=0;i<count.length;i++){
				total+=count[i];
			}
			for(int i=0; i<count.length; i++){			
					WhoVO vo=new WhoVO();
					vo.setWho(who[i]);
					vo.setCount(count[i]*100/total);
					list.add(vo);
			}
		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return list;
	}
		
		
		public static List<WordCloudVO> wordcloud(){
			List<WordCloudVO> list=new ArrayList<WordCloudVO>();
			try{
				RConnection rc=new RConnection();
				rc.voidEval("library(KoNLP)");
				rc.voidEval("data<-readLines(\"/home/sist/git/san/San/src/main/webapp/data/naver/recommand_san.txt\")");		
				rc.voidEval("place<-sapply(data,extractNoun,USE.NAMES = F)");				
				rc.voidEval("data<-unlist(place)");					
				rc.voidEval("place<-str_replace_all(data,\"[^[:alpha:]]\",\"\")");					
				rc.voidEval("place<-gsub(\" \",\"\",place)");
				rc.voidEval("place<-gsub(\"b\",\"\",place)");
				rc.voidEval("place<-Filter(function(x){nchar(x)>=2},place)");
				rc.voidEval("rev<-table(unlist(place))");
				rc.voidEval("top20<-head(sort(rev,decreasing = T),15)");
				REXP p=rc.eval("names(top20)");
				String word[]=p.asStrings();
				p=rc.eval("top20");
				int count[]=p.asIntegers();
				for(int i=0; i<word.length;i++){
					WordCloudVO vo=new WordCloudVO();
					vo.setWord(word[i]);
					vo.setCount(count[i]);
					list.add(vo);
				}
					
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			
			}
			return list;
		}

}
