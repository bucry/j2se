package com.bfchuan.runnable;

public class WuZiQi {
	
	public static void test(){
		int w = 20,h =20;         //宽和高
		int x = 0, y = 0;        //坐标
		int k,I = x+y*w;         //坐标与位置转换
		//bool P =false;
		int luozi = 1;
		int heibai = 2;
		while(heibai<3)
		{ 
			heibai++;
			for(k = 0;k<w*h;k++)
			{
				I = x+y*w;
				if(k == I)
				{
					switch(luozi)            //落子
					{
					case 1:{
						System.out.print("◎");
						break;
						   }
					case 2: {
						if(heibai%2 == 0)
							System.out.print("●");
						else 
							System.out.print("○");
						break; 	}
					}
				}
				//棋盘
				else if(k%20 == 0 && k/20 == 0 )
					System.out.print("┏");
				else if(k%20 != 0 && k/20 == 0 && k%20!= 19)
					System.out.print("┳");
				else if(k%20 == 19 && k/20==0)
					System.out.print("┓");
				else if(k%20 ==0 && k/20>0 && k/20!=19)
					System.out.print("┣");
				else if(k%20>0 && k/20>0 && k%20!=19 && k/20!=19)
					System.out.print("╋");
				else if(k%20 == 19 && k/20!=19)
					System.out.print("┫");
				else if(k%20 ==0 && k/20 ==19)
					System.out.print("┗");
				else if(k/20 == 19 && k%20 != 19)
					System.out.print("┻");
				else  
					System.out.print("┛");

				if(k%20 == 19)
					System.out.print("\n");
			}

			int qizi = 1; 
			if(qizi=='a'||qizi=='A')
				if(x>0)
					x-=1;
			if(qizi=='d'||qizi=='D')
				if(x<19)
					x+=1;
			if(qizi=='w'||qizi=='W')
				if(y>0)
					y-=1;
			if(qizi=='s'||qizi=='S')
				if(y<19)
					y+=1;
			if(qizi == 'p')
			{ 
				//if()         
				{   luozi = 2;  heibai+=1;}
			}
		}

	}
	
	public static void main(String args[]){
		test();
	}

}
