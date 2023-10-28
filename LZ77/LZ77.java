package LZ77;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Integer.max;
import java.util.ArrayList;

public class LZ77
{

    public static String data;

    public static String FRead(File file) throws Exception
    {
        FileInputStream read = new FileInputStream(file);
        int i;
        String s="";
        while ((i=read.read())!=-1)
        {
            s=s+(char)i;
        }
        read.close();
        data=s;
        return s;
    }
    public static void FWrite(File file,String r) throws Exception
    {
        FileOutputStream write = new FileOutputStream(file);
        String name = r;
        byte[] names = name.getBytes();
        write.write(names);
        write.close();
    }

    public static void LZ77Compress(String str) throws IOException, Exception
    {
        File compressed = new File("fileCompressed.txt");
        compressed.createNewFile();

        ArrayList<ArrayList<String>> tags=  new ArrayList<>();
        ArrayList<String> parts= new ArrayList<>();

        String check="";
        String Buffer="";
        int COUNT=0;
        for(int i=0;i<str.length();i++)
        {
            check=check+str.charAt(i);
            int index=Buffer.indexOf(check);

            if(i==str.length()-1)
            {
                ArrayList<String> tag= new ArrayList<>();
                String existPart="",notExistPart="";
                int ptr2=i,ptr1=i-COUNT;
                for(int j=ptr1; j<ptr2; j++) existPart=existPart+str.charAt(j);
                for(int j=ptr1; j<=ptr2; j++) notExistPart=notExistPart+str.charAt(j);
                int lastIndex=Buffer.lastIndexOf(existPart);

                String firstPart=Integer.toString(ptr1-lastIndex);
                String secondPart=Integer.toString(COUNT);
                tag.add(firstPart);
                tag.add(secondPart);
                String nextChar="";
                nextChar=nextChar+str.charAt(i-1);
                tag.add(nextChar);
                tags.add(tag);
                Buffer+=check;
                parts.add(check);
                break;
            }
            if(index==-1 && COUNT==0)
            {
                ArrayList<String> tag= new ArrayList<>();
                tag.add("0");
                tag.add("0");
                String nextChar="";
                nextChar=nextChar+str.charAt(i);
                tag.add(nextChar);
                tags.add(tag);
                Buffer=Buffer+check;
                parts.add(check);
                check="";
                COUNT=0;
            }
            else if(index==-1 && COUNT>0)
            {
                ArrayList<String> tag= new ArrayList<>();
                String existPart="",notExistPart="";
                int ptr2=i,ptr1=i-COUNT;
                for(int j=ptr1; j<ptr2; j++) existPart=existPart+str.charAt(j);//ABAA
                for(int j=ptr1; j<=ptr2; j++) notExistPart=notExistPart+str.charAt(j);//AA
                int poss=Buffer.indexOf(existPart);
                String firstPart=Integer.toString(ptr1-poss);
                String secondPart=Integer.toString(COUNT);
                tag.add(firstPart);
                tag.add(secondPart);
                String nextChar="";
                nextChar=nextChar+str.charAt(i);
                tag.add(nextChar);
                tags.add(tag);
                Buffer=Buffer+check;
                parts.add(check);
                check="";
                COUNT=0;
            }
            else
            {
                COUNT++;
            }
        }

        String COMPRESSED_DATA="";

        System.out.println("--Decompressed Data Tags--");
        for (int i = 0; i < tags.size(); i++)
        {
            for (int j = 0; j < tags.get(i).size(); j++)
            {
                if(j==0)
                {
                    System.out.print(" <");
                    COMPRESSED_DATA+="<";
                }
                if(j!=2)
                {
                    System.out.print(tags.get(i).get(j) + ",");
                    COMPRESSED_DATA+=tags.get(i).get(j);
                    COMPRESSED_DATA+=',';
                }
                else
                {
                    System.out.print( tags.get(i).get(j) );
                    COMPRESSED_DATA+=tags.get(i).get(j);
                }
                if(j==2)
                {
                    System.out.print("> ");
                    COMPRESSED_DATA+="> ";
                }
            }
            System.out.println();
        }
        FWrite(compressed,COMPRESSED_DATA);

    }

    public static void LZ77Decompress(ArrayList<String> tags) throws IOException, Exception
    {
            File file = new File("Decompressed.txt");
            file.createNewFile();

        int current=-1,temp=0;//made the temp to preserve the current position
        String result="";

        for(int i=0; i<tags.size(); i++) {
            current++;
            temp=current;

            String strPosition = "";
            String strLength = "";

            int k = 1;// to avoid "<"
            String subStr = tags.get(i);//<1,2,"a"> selecting tag i

            while(k < subStr.length() && subStr.charAt(k) != ',') {
                //taking the position of tag i
                strPosition=strPosition+subStr.charAt(k);
                k++; //strPosition=1 //k=2 //stop
            }

            int l=k+1; //k+1 to avoid ","
            while(l < subStr.length() && subStr.charAt(l) != ',') {//taking length of tag i
                strLength=strLength+subStr.charAt(l);
                l++;
            }

            int e=l+1;
            if(subStr.charAt(1)=='0' && subStr.charAt(3)=='0' ){//taking char of tag i
                if (e < subStr.length()) { // Check bounds of the string
                    result = result + subStr.charAt(e);
                }
            }
            //take the char if available
            else { // for character data
                String part = "";
                int position = Integer.parseInt(strPosition);
                int length = Integer.parseInt(strLength);

                temp-=position; //elmkan ely ana feh hrg3 3nnd 5tawat ysawy position 34an awsal l2awl char
                for (int j = temp; j < temp + length; j++)
                {
                    //hm4y length
                    // Check if 'j' is within the bounds of 'result' before accessing its character
                    if (j >= 0 && j < result.length())
                    {
                        part = part + result.charAt(j);//ha5d elgoz2 ely bytkrr
                    }
                }
                if (e < subStr.length()) { // Check the boundaries
                    if (subStr.charAt(e) != 'N') {
                        part = part + subStr.charAt(e);//h7ot el part + elchar ely wa2fa 3ndo
                    }
                }
                current = current + part.length() - 1;//h5ally el current y4awr 3la a5r char f elgoz2
                result = result + part;//hdef elgoz2 3ala el result w akml
            }

        }

        System.out.println("Decompressed Text : "+ result);
          FWrite(file,result);
    }
}







