import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SwitchServer {
    private static final int LINE_OFFSET = 4;
    private static final int LINE_NUMBER = 5;

    public static void main(String[] args) {
        if (args.length <= 0) {
            System.out.println("please input your parameters");
            System.exit(-1);
        }

        int project = -1;
        int serverType = -1;

        for (int i = 0; i < args.length; i++) {
            System.out.println("ok,your input is " + args[i]);
            project = Integer.parseInt(args[0]);
            serverType = Integer.parseInt(args[1]);
        }
//         int serverType = 2;
//         int project = 1;

        String fileName = "";
        switch (project) {
            case 1:
                fileName = "mba.gradle";
                break;

            case 2:
                fileName = "art.gradle";
                break;
				
			case 3:
                fileName = "finance.gradle";
                break;

            default:
                fileName = "mba.gradle";
                break;
        }

        try {
            File file = new File(fileName);
            System.out.println(file.getAbsolutePath());
            if (file.exists()) {
                List<String> stringList = new ArrayList<>();
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while (bufferedReader.ready()) {
                    String string = bufferedReader.readLine();
                    stringList.add(string);
                }

                FileWriter fileWriter = new FileWriter(fileName);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                boolean flag = true;
                int start = -1;
                int realStart = -1;
                int realEnd = -1;
                for (int i = 0; i < stringList.size(); i++) {
                    String string = stringList.get(i);

                    if (string.contains("//<=>")) {
                        flag = false;
                    }

                    if (string.contains("project = [")) {
                        start = i;
                        realStart = start + LINE_NUMBER * serverType - LINE_OFFSET;
                        realEnd = start + LINE_NUMBER * serverType;
                        bufferedWriter.write(string);
                        bufferedWriter.newLine();
                        continue;
                    }

                    if (start > 0 && flag) {
                        if (!string.contains("//=>")) {
                            if (i > realStart && i <= realEnd) {
                                if (string.startsWith("//")) {
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

}
