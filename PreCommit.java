import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PreCommit {

    private static final String ROOT_PATH = new File("").getAbsolutePath();
    private static final File ROOT_DIR = new File(ROOT_PATH);
    private static final List<String> EXCLUDE_DIR_LIST = new ArrayList<>(List.of(".git", ".idea", "out", "src", "images"));
    private static final PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
	private static final Map<Character, Character> convertCharacterMapForLink = new HashMap<>();
	static {
		convertCharacterMapForLink.put('$', '/');
		convertCharacterMapForLink.put('_', ' ');	
	}
	
    public static void main(String[] args) {

        LinkedHashMap<String, List<String>> filenamesByDirname = new LinkedHashMap<>();
        
        ps.println("\n\nROOT PATH: " + ROOT_PATH);
        ps.println(">> Search Directories");

        storeFilenamesByDirname(filenamesByDirname);

        ps.println("\n>> Start to update README.md");
        ps.println("...");

        updateReadme(filenamesByDirname);

        ps.println(">> Finished to update README.md");

        System.exit(0);
    }

    private static void storeFilenamesByDirname(LinkedHashMap<String, List<String>> filenamesByDirname) {

        Arrays.stream(ROOT_DIR.listFiles())
                .filter(File::isDirectory)
                .filter(f -> !EXCLUDE_DIR_LIST.contains(f.getName()))
                .forEach(dir -> {

                    ps.println("---- " + dir.getName() + "/");
                    filenamesByDirname.put(dir.getName(), getFileNameList(dir));
                });
    }

    private static void updateReadme(LinkedHashMap<String, List<String>> filenamesByDirname) {

        File tmp = null;

        try {
            tmp = new File(ROOT_PATH + "/README.md.tmp_" + new Random().nextInt(100));
            tmp.createNewFile();

            File readme = new File(ROOT_PATH + "/README.md");

            writeToTmp(filenamesByDirname, tmp, readme);
            copyTmpToReadme(tmp, readme);

        } catch (IOException e) {
            ps.println(">> Exception: Exception occurred while create README.md.tmp_xx");
            System.exit(1);
        } finally {
            tmp.delete();
        }
    }

    private static void copyTmpToReadme(File tmp, File readme) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(tmp, StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(new FileWriter(readme, StandardCharsets.UTF_8))) {

            String line = null;

            while((line = br.readLine()) != null) {
                bw.write(line + "\n");
            }

            bw.flush();
        }
    }

    private static void writeToTmp(LinkedHashMap<String, List<String>> filenamesByDirname, File tmp, File readme) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(readme, StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tmp, StandardCharsets.UTF_8))) {

            writeBeforeIndex(br, bw);
            writeIndex(filenamesByDirname, bw);
        }
    }

    private static void writeBeforeIndex(BufferedReader br, BufferedWriter bw) throws IOException{

        String line = null;

        while((line = br.readLine()) != null) {

            if (line.equals("{Index}") || line.equals("# Index")) {
                break;
            }
			
			bw.write(line + "\n");
        }

        bw.flush();
    }

    private static void writeIndex(LinkedHashMap<String, List<String>> filenamesByDirname, BufferedWriter bw) throws IOException {
		
		if	(filenamesByDirname.isEmpty()) {
			return;
		}
		
		bw.write("# Index\n\n");
		
        for (String dirname : filenamesByDirname.keySet()) {

            String category = String.format("### %s", convertToCategoryName(dirname));

            bw.write(category + "\n");

            for(String filename : filenamesByDirname.get(dirname)) {

                String link = dirname + "/" + filename;
                String indexName = convertToLinkName(filename);
                String indexLine = String.format("- [%s](%s)", indexName, link);

                bw.write(indexLine + "\n");
            }
        }

        bw.flush();
    }

    private static List<String> getFileNameList(File dir) {

        File[] files = dir.listFiles();

        List<String> fileNameList = new ArrayList<>();

        for(File f : files) {
            if (f.isFile()) {

                ps.println("     |---- " + f.getName());
                fileNameList.add(f.getName());
            }
        }

        return fileNameList;
    }

    // ex) TCP_%_UDP -> TCP / UDP
    private static String convertToLinkName(String filename) {

        char[] cArr = filename.toCharArray();

		
        for (int i = 0; i < cArr.length; i++) {
			if(convertCharacterMapForLink.containsKey(cArr[i])) {
				cArr[i] = convertCharacterMapForLink.get(cArr[i]);
			}
        }
		
		String ret = String.valueOf(cArr);
		
		if(ret.toLowerCase().endsWith(".md")) {
			ret = ret.substring(0, ret.length() - 3);
		}

        return ret;
    }
    
    // ex) directory_name -> Directory Name
    private static String convertToCategoryName(String dirName) {

        char[] cArr = dirName.replaceAll("_", " ").trim().toCharArray();
        cArr[0] = Character.toUpperCase(cArr[0]);

        for(int i=1;i<cArr.length - 1;i++) {
            if(cArr[i] == ' ') {
                cArr[i+1] = Character.toUpperCase(cArr[i+1]);
            }
        }

        return String.valueOf(cArr);
    }
}
