package com.nitin.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Main
{
    private static final String        VOICENAME = "kevin16";
    private static Map<String, String> questions = new HashMap<String, String>();
    private static Map<String, String> answers   = new HashMap<String, String>();
    public static void main(String asdf[])
    {
        try
        {
            String currentKey = "";
            Scanner scan = new Scanner(System.in);
            loadQuestions();
            loadAnswers();
            VoiceManager vm = VoiceManager.getInstance();
            Voice voice = vm.getVoice(VOICENAME);
            voice.allocate();
            List<String> keys = new ArrayList(questions.keySet());
            Collections.shuffle(keys);
            for (String key : keys)
            {
                currentKey = key;
                System.out.println(currentKey + "->" + questions.get(key));
                voice.speak(questions.get(key));
                String input = scan.nextLine();
                if ("q".equals(input))
                {
                    voice.speak("Thank you for this interview, good bye !");
                    System.out.println("Exit!");
                    scan.close();
                    break;
                }
                else if ("a".equals(input))
                {
                    if (answers.containsKey(currentKey))
                        voice.speak(answers.get(currentKey));
                    else
                        voice.speak("No answer found for this question.");
                    input = scan.nextLine();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void loadQuestions()
    {
        try
        {
            File f = new File(Main.class.getResource("/com/nitin/questions").getFile());
            if (f.isDirectory() && f.list().length != 0)
            {
                File[] files = f.listFiles();
                for (File file : files)
                {

                    System.out.println("Loading data file [ " + file.getName() + " ] completed !");
                    BufferedReader br = null;
                    try
                    {
                        br = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = br.readLine()) != null)
                        {
                            String arr[] = line.trim().split("~");
                            if (arr.length == 2)
                                questions.put(file.getName() + arr[0], arr[1]);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if (br != null)
                            try
                            {
                                br.close();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void loadAnswers()
    {
        try
        {
            File f = new File(Main.class.getResource("/com/nitin/answers").getFile());
            if (f.isDirectory() && f.list().length != 0)
            {
                File[] files = f.listFiles();
                for (File file : files)
                {

                    System.out.println("Loading data file [ " + file.getName() + " ] completed !");
                    BufferedReader br = null;
                    try
                    {
                        br = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = br.readLine()) != null)
                        {
                            String arr[] = line.trim().split("~");
                            if (arr.length == 2)
                            {
                                arr[0] = file.getName() + arr[0];
                                if (questions.containsKey(arr[0]))
                                    answers.put(arr[0], arr[1]);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if (br != null)
                            try
                            {
                                br.close();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
