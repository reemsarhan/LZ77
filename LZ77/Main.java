package LZ77;
import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Integer.max;
import java.util.ArrayList;

public class Main
{

    public static void main(String[] args) throws Exception
    {
        Scanner input=new Scanner(System.in);
        LZ77 ob=new LZ77();
        System.out.println("Welcome to LZ77 Compression and Decompression system ");
        boolean stay=true;
        while (stay)
        {
            System.out.println("Select the system that you want: ");
            System.out.println("1-Compression System\n2-Decompression System\n3-Exit");
            int choice=input.nextInt();
            if(choice==1)
            {
                System.out.println("   Welcome to Compression System ^_^                ");

                System.out.println("Choose the way you want to compress with:");
                System.out.println("1)Entering file path");
                System.out.println("2)Entering data");
                int compress_choice=input.nextInt();

                switch (compress_choice)
                {
                    case (1):
                        System.out.println("Enter file path: ");
                        Scanner input2=new Scanner(System.in);
                        String path=input2.next();
                        File file = new File(path);
//                    File file = new File("tryy.txt");
                        String str =ob.FRead(file);
                        ob.LZ77Compress(str);
                        break;
                    case (2):
                        System.out.print("Enter Content you want to Compress : ");
                        String str2;
                        //EX:ABAABABAABBBBBBBBBBBBABBBBBBBBABABABABABABAABABABABBABBABABABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
                        //EX:<0,0,"A"> <0,0,"B"> <2,1,"A"> <3,2,"B"> <5,3,"B"> <2,2,"B"> <5,5,"B"> <16,3,"B"> <9,8,"A"> <29,4,"B"> <35,7,"A"> <17,5,"B"> <44,3,"A"> <10,6,"B"> <54,12,"B"> <16,16,"B"> <13,13,"B">
                        str2=input.next();
                        ob.LZ77Compress(str2);
                        break;
                    default:
                        System.out.println("Choose From the previous choices");
                }


            }

            else if(choice==2)
            {
                System.out.println("   Welcome to DeCompression System ^_^                ");
                ArrayList<String> tags = new ArrayList<>();
                System.out.println("Choose the way you want to decompress with");
                System.out.println("1)Entering file path");
                System.out.println("2)Entering tags");
                int decompresschoice;
                decompresschoice=input.nextInt();
                if (decompresschoice == 1) {
                    Scanner input3 = new Scanner(System.in);
                    System.out.println("Enter file path:");
                    String Dpath = input3.next();
                    File file = new File(Dpath);
                    String str2 = ob.FRead(file);
                    String temp = "";
                    for (int i = 0; i < str2.length(); i++) {
                        if (str2.charAt(i) == '<') {
                            int endIndex = str2.indexOf('>', i);
                            if (endIndex != -1) {
                                temp = str2.substring(i, endIndex + 1);
                                tags.add(temp);
                                i = endIndex + 1;
                            } else {
                                // Handle invalid tag format or end of string not found
                                break;
                            }
                        }
                    }
                    ob.LZ77Decompress(tags);
                } else if (decompresschoice == 2) {
                    int n;
                    String tag;
                    System.out.print("Enter Number of tags: ");
                    n = input.nextInt();
                    System.out.println("Enter the tags in this form: <1,1,A>..");
                    // Example tags: <0,0,A> <0,0,B> <2,1,A> <3,2,B> <5,3,B> <2,2,B> <5,5,B> <1,1,A>
                    for (int i = 0; i < n; i++) {
                        tag = input.next();
                        tags.add(tag);
                    }
                    ob.LZ77Decompress(tags);
                }

                else
                {
                    System.out.println("Choose From the previous choices");
                }


//                System.out.println("   Welcome to DeCompression System ^_^                ");
//                ArrayList<String> tags = new ArrayList<>();
//                int n;
//                String tag;
//                System.out.print("Enter Number of tags : ");
//                n=input.nextInt();
//                System.out.println("Enter the tags in this form : <1,1,A>..");
//                //EX:<0,0,A> <0,0,B> <2,1,A> <3,2,B> <5,3,B> <2,2,B> <5,5,B> <1,1,A>
//                for(int i=0;i<n;i++)
//                {
//                    tag=input.next();
//                    tags.add(tag);
//                }
//
//                ob.LZ77Decompress(tags);

            }
            else
            {
                stay=false;
                System.out.println("BYE ^_^");
                System.exit(0);
            }
        }

    }
}
