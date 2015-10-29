/**
 * Created by zwb on 2015/10/29 0029.
 */
import java.util.*;


public class BoyerMoore {

    public static void main(String[] args) {
        String text="ZWBLOVECSS";
        String pattern="CSS";
        System.out.println("Find "+ pattern+" in "+ text);
        BoyerMoore bm=new BoyerMoore();
        bm.boyerMoore(pattern, text);
    }
    private void preBmBc(String pattern,int patLength,Map<String,Integer> bmBc)
    {
        System.out.println("bmbc start process...");
        for(int i=patLength-2;i>=0;i--)
        {
            if(!bmBc.containsKey(String.valueOf(pattern.charAt(i))))
            {
                bmBc.put(String.valueOf(pattern.charAt(i)),(Integer)(patLength-i-1));
            }
        }
    }

    private void suffix(String pattern,int patLength,int [] suffix)
    {
        suffix[patLength-1]=patLength;
        int q=0;
        for(int i=patLength-2;i>=0;i--)
        {
            q=i;
            while(q>=0&&pattern.charAt(q)==pattern.charAt(patLength-1-i+q))
            {
                q--;
            }
            suffix[i]=i-q;
        }
    }

    private void preBmGs(String pattern,int patLength,int []bmGs)
    {
        int i,j;
        int []suffix=new int[patLength];
        suffix(pattern,patLength,suffix);
        //ģʽ����û���Ӵ�ƥ���Ϻú�׺��Ҳ�Ҳ���һ�����ǰ׺
        for(i=0;i<patLength;i++)
        {
            bmGs[i]=patLength;
        }
        //ģʽ����û���Ӵ�ƥ���Ϻú�׺�����ҵ�һ�����ǰ׺
        j=0;
        for(i=patLength-1;i>=0;i--)
        {
            if(suffix[i]==i+1)
            {
                for(;j<patLength-1-i;j++)
                {
                    if(bmGs[j]==patLength)
                    {
                        bmGs[j]=patLength-1-i;
                    }
                }
            }
        }
        //ģʽ�������Ӵ�ƥ���Ϻú�׺
        for(i=0;i<patLength-1;i++)
        {
            bmGs[patLength-1-suffix[i]]=patLength-1-i;
        }
        System.out.print("bmGs:");
        for(i=0;i<patLength;i++)
        {
            System.out.print(bmGs[i]+",");
        }
        System.out.println();
    }
    private int getBmBc(String c,Map<String,Integer> bmBc,int m)
    {
        //����ڹ������򷵻���Ӧ��ֵ�����򷵻�pattern�ĳ���
        if(bmBc.containsKey(c))
        {
            return  bmBc.get(c);
        }else
        {
            return m;
        }
    }
    public void  boyerMoore(String pattern,String text )
    {
        int m=pattern.length();
        int n=text.length();
        Map<String,Integer> bmBc=new HashMap<String,Integer>();
        int[] bmGs=new int[m];
        preBmBc(pattern,m,bmBc);
        preBmGs(pattern,m,bmGs);
        //searching
        int j=0;
        int i=0;
        int count=0;
        while(j<=n-m)
        {
            for(i=m-1;i>=0&&pattern.charAt(i)==text.charAt(i+j);i--)
            {	//���ڼ���
                count++;
            }
            if(i<0){
                System.out.println("one position is:"+j);
                j+=bmGs[0];
            }else{
                j+=Math.max(bmGs[i],getBmBc(String.valueOf(text.charAt(i+j)),bmBc,m)-m+1+i);
            }
        }
        System.out.println("count:"+count);
    }

}
