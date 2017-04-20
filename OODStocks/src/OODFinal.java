//Russell Smith, 90698657, Object Oriented Design Final Project

import java.util.Random;

public class OODFinal {
	
	static int x,y,z;
	static int[] top5prices = new int[5];
	static String[] top5list = new String[5];
	static int[] bot10prices = new int[10];
	static String[] bot10list = new String[10];
	
	public static int TestThings(){
		Random rand = new Random();
		
		int StockTest = rand.nextInt(20)*3+1;
		
		return StockTest;
	}
	
	public static String TestThings2(){
		Random rand = new Random();
		//for stock pricing, x=current, y=low, z=high
		x = rand.nextInt(27)*2+1;
		y = rand.nextInt(12)+1;
		z = rand.nextInt(18)*4+1;
		String temp = "";
		for(int i=0; i<3; i++){
			char q = (char)(rand.nextInt(26)+'a');
			temp += q;
		}
		final String StockTest2 = temp +" "+ x +"  "+ y +"  "+ z;
		
		//pass and hold 5 of x checking against each a new integer comes in.
		if(setTop5(x) == true){
			top5list[4] = top5list[3];
			top5list[3] = top5list[2];
			top5list[2] = top5list[1];
			top5list[1] = top5list[0];
			top5list[0] = StockTest2;
		}
		
		if(setbot10(x) == true){
			bot10list[9] = bot10list[8];
			bot10list[8] = bot10list[7];
			bot10list[7] = bot10list[6];
			bot10list[6] = bot10list[5];
			bot10list[5] = bot10list[4];
			bot10list[4] = bot10list[3];
			bot10list[3] = bot10list[2];
			bot10list[2] = bot10list[1];
			bot10list[1] = bot10list[0];
			bot10list[0] = StockTest2;
		}
			
		return StockTest2;
	}
	
	public static boolean setTop5(int x){
			//this logic in incorrect and allows for nulls to be found if |most| is found
		if(x >= top5prices[0]){
			top5prices[4] = top5prices[3];
			top5prices[3] = top5prices[2];
			top5prices[2] = top5prices[1];
			top5prices[1] = top5prices[0];
			top5prices[0] = x;
			
			return true;
		}
		else
			return false;
	}
	public static String getTop5(int x){
		return top5list[x];
	}
	
	public static boolean setbot10(int x){
		if(bot10prices[0] == 0){
			bot10prices[0] = 1000;
		}
			//this logic in incorrect and allows for nulls to be found if |least| is found
		if(x <= bot10prices[0]){
			bot10prices[9] = bot10prices[8];
			bot10prices[8] = bot10prices[7];
			bot10prices[7] = bot10prices[6];
			bot10prices[6] = bot10prices[5];
			bot10prices[5] = bot10prices[4];
			bot10prices[4] = bot10prices[3];
			bot10prices[3] = bot10prices[2];
			bot10prices[2] = bot10prices[1];
			bot10prices[1] = bot10prices[0];
			bot10prices[0] = x;
			
			return true;
		}
		else
			return false;
	}
	public static String getbot10(int x){
		return bot10list[x];
	}
}
