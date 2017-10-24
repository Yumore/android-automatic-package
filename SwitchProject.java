import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SwitchProject {
    private static final int OFFSET = 6;
    private static final int LINE_NUMBER = 7;

    public static void main(String args[]) {
		 if (args.length <= 0) {
            System.out.println("please input your parameters");
            System.exit(-1);
        }

        int project = Integer.valueOf(args[0]);
		
        // int project = 3;
        modifyModuleGradle(project);
        modifyProjectGradle(project);
    }

    private static void modifyModuleGradle(int project) {
        String mainModuleGradle = "mBA_Master/build.gradle";
        try {
            File file = new File(mainModuleGradle);
            System.out.println("module gradle file path: "+file.getAbsolutePath());
            if (file.exists()) {
                List<String> stringList = new ArrayList<>();
                FileReader fileReader = new FileReader(mainModuleGradle);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while (bufferedReader.ready()) {
                    String string = bufferedReader.readLine();
                    stringList.add(string);
                }

                FileWriter fileWriter = new FileWriter(mainModuleGradle);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                boolean flag = true;
                int start = -1;
                int realStart = -1;
                int realEnd = -1;
                for (int i = 0; i < stringList.size(); i++) {
                    String string = stringList.get(i);

                    if (string.contains("signingConfigs {")) {
                        start = i;
                        realStart = start + LINE_NUMBER * project - OFFSET;
                        realEnd = start + LINE_NUMBER * project;
                        bufferedWriter.write(string);
                        bufferedWriter.newLine();
                        continue;
                    }

                    if (string.contains("//<=>")) {
                        flag = false;
                    }

                    if (start > 0 && flag) {
                       if (!string.contains("//=>")) {
                            if (i > realStart && i <= realEnd) {
                                if (string.contains("//")) {
                                    string = string.replace("//", "");
                                }
                            } else {
                                if (!string.startsWith("//")) {
                                    string = "//" + string;
                                }
                            }
                        }
                    }

                    bufferedWriter.write(string);
                    bufferedWriter.newLine();
                }

                bufferedWriter.flush();
                bufferedWriter.close();
                bufferedReader.close();
                fileWriter.close();
                fileReader.close();
            } else {
                System.out.println("file is not exists");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void modifyProjectGradle(int project) {
        String projectGradle = "build.gradle";

        try {
            File file = new File(projectGradle);
            System.out.println("project gradle file path: "+file.getAbsolutePath());
            if (file.exists()) {
                List<String> stringList = new ArrayList<>();
                FileReader fileReader = new FileReader(projectGradle);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while (bufferedReader.ready()) {
                    String string = bufferedReader.readLine();
                    stringList.add(string);
                }

                FileWriter fileWriter = new FileWriter(projectGradle);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                boolean flag = true;
                for (int i = 0; i < stringList.size(); i++) {
                    String string = stringList.get(i);

                    if (string.contains("buildscript")) {
                        flag = false;
                    }

                    if (i > 1 && flag) {
                        if (project + 1 == i) {
                            if (string.startsWith("//")) {
                                string = string.replace("//", "");
                            }
                        } else {
                            if (!string.startsWith("//")) {
                                string = "//" + string;
                            }
                        }
                    }

                    bufferedWriter.write(string);
                    bufferedWriter.newLine();
                }

                bufferedWriter.flush();
                bufferedWriter.close();
                bufferedReader.close();
                fileWriter.close();
                fileReader.close();
            } else {
                System.out.println("file is not exists");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}